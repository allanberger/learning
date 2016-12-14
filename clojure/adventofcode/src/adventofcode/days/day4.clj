(ns adventofcode.days.day4
  (:gen-class)
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(defn rooms []
  (line-seq (io/reader "././files/day4_input.txt")))

(defn checksum [room]
  (let [length (count room)]
    (subs room (- length 6) (- length 1))))

(defn compare-item [[k1 v1][k2 v2]]
  (or (> v1 v2)
      (if (= v1 v2)
        (<= (compare k1 k2) 0)
        false)))

(defn check-real-room [room]
  (->> (sort compare-item
             (-> room
                 (subs 0 (- (count room) 10))
                 (str/replace "-" "")
                 (frequencies)))
       (take 5)
       (keys)))

(defn solution [room]
    (if (= (set (check-real-room room)) (set (checksum room)))
      (Integer. (subs room (- (count room) 10) (- (count room) 7)))
      0))

(transduce (map #(do (println (check-real-room %1) (checksum %1) (solution %1)) (solution %1))) + (rooms))
(reduce + (map solution (rooms)))
