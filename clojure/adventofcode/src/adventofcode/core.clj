(ns adventofcode.core
  (:gen-class))

(require '[adventofcode.days.day3 :as day3])
(require '[adventofcode.days.day4 :as day4])
(require '[adventofcode.days.day6 :as day6])

(defn -main
  "Run all days"
  [& args]
  (println (day3/part1))
  (println (day4/part1))
  (println (day6/part1))
  (println (day6/part2)))
