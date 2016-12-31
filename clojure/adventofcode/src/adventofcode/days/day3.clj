(ns adventofcode.days.day3
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(defn input [path]
  (line-seq (io/reader path)))

(def input1 "  541  588  421")
(def input2 "  827  272  126")
(def input3 "   39  703  839")

(map vector (input "././files/day3_input.txt"))

; get greatest number of one row
(defn greatest
  [entry]
  (first (reverse (sort (map #(Integer/parseInt %) (str/split (str/trim entry) #"\s+"))))))

; sum of 2 smaller numbers
(defn sum
  [entry]
  (apply + (rest (reverse (sort (map #(Integer/parseInt %) (str/split (str/trim entry) #"\s+")))))))

(defn check-valid-triangle
  [entry]
  (compare (greatest entry) (sum entry)))

(defn part1
  []
  (-> (filter #(= % -1) (map #(check-valid-triangle %) (input "././files/day3_input.txt")))
      (count)))

; => 993
