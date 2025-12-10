(ns day_two
      (:require [input]))


(defn invalid-id? [id]
      (let [length (count id)
            middle (/ length 2)]
            (if (even? length) (= (subs id 0 middle) (subs id middle length)) false)))

;;Returns a lazy sequence of all nums from a to b(inclusive)
(defn parse-range [range-str]
      (let [[a b] (clojure.string/split range-str #"-")
            start (Long/parseLong a)
            end (inc (Long/parseLong b))] (range start end)))

(defn keep-invalids [ID-range]
      (keep (fn [num] (let [id (str num)] (when (invalid-id? id) id)))
            ID-range))


(def ranges (input/split-lines-flat "day-2-input" ","))

(def invalids
     (mapcat (fn [range-str] (keep-invalids (parse-range range-str)))
             ranges))

(def total-invalids (count invalids))
(def totalofInvalids
      (reduce + (map #(Long/parseLong %) invalids)))
(println "Total invalid IDs:" total-invalids)

(println totalofInvalids)