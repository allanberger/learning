(ns adventofcode.days.day3
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(defn input [path]
  (line-seq (io/reader path)))

; get greatest number of one row
(defn greatest
  [entry]
  (first (reverse (sort (map #(Integer/parseInt %) (str/split (str/trim entry) #"\s+"))))))

; sum of 2 smaller numbers
(defn sum
  [entry]
  (apply + (rest (reverse (sort (map #(Integer/parseInt %) (str/split (str/trim entry) #"\s+")))))))

; comparator returns -1 if it's a valid triangle
(defn check-valid-triangle
  [entry]
  (compare (greatest entry) (sum entry)))

(defn part1
  []
  (->> (input "././files/day3_input.txt")
       (map #(check-valid-triangle %))
       (filter #(= % -1))
       (count)))

; => 993

(defn part2
  []
  (->> (input "././files/day3_input.txt")
       (map #(str/trim %))
       (map #(str/split % #"\s+"))
       (apply map vector)
       (flatten)
       (partition 3)
       (map #(str/join " " %))
       (map #(check-valid-triangle %))
       (filter #(= % -1))
       (count)))

; => 1849
