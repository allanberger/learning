(ns adventofcode.days.day6
  (:require [clojure.java.io :as io]))

(defn input [path]
  (line-seq (io/reader path)))

(defn part1
  "http://adventofcode.com/2016/day/6"
  []
  (doseq [x (apply map vector (input "././files/day6_input.txt"))]
    (->> (frequencies x)
         (sort-by val)
         (reverse)
         (ffirst)
         (prn))))

; => \w \k \b \v \m \i \k \b

(defn part2
  "http://adventofcode.com/2016/day/6"
  []
  (doseq [x (apply map vector (input "././files/day6_input.txt"))]
    (->> (frequencies x)
         (sort-by val)
         (ffirst)
         (prn))))

; => \e \v \a \k \w \a \g \a
