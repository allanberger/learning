(ns adventofcode.days.day6
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn input [path]
  (line-seq (io/reader path)))

(doseq [x (apply map vector (input "././files/day6_input.txt"))]
  (->> (frequencies x)
       (sort-by val)
       (reverse)
       (ffirst)
       (prn)))

; => \w \k \b \v \m \i \k \b
