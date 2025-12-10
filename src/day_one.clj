;; Author Ola Björnfot
;; 09-12-2025
;; Day 1
;; my attempt to learn Clojure by doing Advent of Code 2025
(ns day-one)
(require '[clojure.java.io :as io])

(defn parse-line [line]
  {:dir   (subs line 0 1)
   :value (Integer/parseInt (subs line 1))})

(defn rotate [state instr]
  (let [{:keys [safe-val count]} state
        {:keys [dir value]}       instr
        new-val   (mod (case dir
                         "L" (+ safe-val value)
                         "R" (- safe-val value))
                       100)
        new-count (if (zero? new-val) (inc count) count)
        result    {:safe-val new-val :count new-count}]

    result))

(with-open [rdr (io/reader "day_one")]
  (reduce
    (fn [state line]
      (let [instr (parse-line line)]
        (rotate state instr)))
    {:safe-val 50 :count 0}   ;; initial state
    (line-seq rdr)))
