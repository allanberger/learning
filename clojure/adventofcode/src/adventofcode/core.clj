(ns adventofcode.core
  (:gen-class))

(require '[adventofcode.days.day1 :as day4])
(require '[adventofcode.days.day4 :as day6])

(defn -main
  "Run all days"
  [& args]
  (println (day4/part1))
  (println (day6/part1)))
