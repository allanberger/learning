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
       (keys)
       (take 5)
       (s/join)))

(defn sector-value [room]
  (let [length (count room)]
    (letfn [(room-name [] (subs room 0 (- length 11)))
            (sector-id [] (subs room (- length 10) (- length 7)))
            (checksum [] (subs room (- length 6) (- length 1)))]
      (if (= (checksum) (calc-checksum (room-name)))
        (Integer. (sector-id))
        0))))

(defn rooms [path]
  (line-seq (io/reader path)))

(defn part1
  []
  (reduce + (map sector-value (rooms "././files/day4_input.txt"))))
