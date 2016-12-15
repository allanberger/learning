(ns adventofcode.days.day4
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn compare-freq [[k1 v1] [k2 v2]]
  (or (> v1 v2)
      (and (= v1 v2)
           (< (compare k1 k2) 0))))

(defn calc-checksum [room-name]
  (->> (s/replace room-name "-" "")
       (frequencies)
       (sort compare-freq)
       (flatten)
       (take-nth 2)
       (take 5)
       (s/join)))

(defn sector-value [room]
  (let [length (count room)]
    (letfn [(room-name [room] (subs room 0 (- length 11)))
            (sector-id [room] (subs room (- length 10) (- length 7)))
            (checksum [room] (subs room (- length 6) (- length 1)))]
      (if (= (checksum room) (calc-checksum (room-name room)))
        (Integer. (sector-id room))
        0))))

(defn rooms [path]
  (line-seq (io/reader path)))

(reduce + (map sector-value (rooms "././files/day4_input.txt")))
