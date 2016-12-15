(ns adventofcode.days.day4
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn room-name [room]
  (subs room 0 (- (count room) 11)))

(defn sector-id [room]
  (let [length (count room)]
    (subs room (- length 10) (- length 7))))

(defn checksum [room]
  (let [length (count room)]
    (subs room (- length 6) (- length 1))))

(defn calc-checksum [room-name]
  (->> (s/replace room-name "-" "")
       (frequencies)
       (sort-by val)
       (reverse)
       (partition-by val)
       (map #(map first %))
       (map sort)
       (flatten)
       (take 5)
       (s/join)))

(defn sector-value [room]
  (if (= (checksum room) (calc-checksum (room-name room)))
    (Integer. (sector-id room))
    0))

(defn rooms [path]
  (line-seq (io/reader path)))

(reduce + (map sector-value (rooms "././files/day4_input.txt")))
