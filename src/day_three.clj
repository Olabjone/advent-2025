(ns day_three
  (:require [input]))


(def source (into [] (input/read-lines "day-3-input" )))

;;PART ONE
(comment( (defn my-max [line]
            (second (let [digits (map #(- (int %) (int\0)) line)]
                      (reduce
                        (fn [[max-left current-best] digit]
                          (let [candidate (+ (* max-left 10) digit)
                                new-best (if (> candidate current-best) candidate current-best)
                                new-max-left (if (> digit max-left) digit max-left)]
                            [new-max-left new-best]))
                        [0 0] digits))))))

;;PART TWO

(defn line->digits [line]
  (map #(- (int %) (int \0)) line))


(defn build-stack [digits k]
  (let [to-remove (- (count digits) k)]
    (first
      (reduce (fn [[stack rem] d]
                (let [[new-stack new-rem]
                      (loop [s stack r rem]
                        (if (and (seq s) (< (peek s ) d) (> r 0 ))
                          (recur (pop s ) (dec r))
                          [s r]))]
                  [(conj new-stack d) new-rem]))
              [[]to-remove]
              digits))))

(defn my-max [len line]
  (let [digits (line->digits line)
        stack (build-stack digits len)]
    (reduce (fn [accum_num curr_digit] (+ (* accum_num 10) curr_digit)) 0 (take len stack))))



(def all-batteries
  (map (fn [line] (my-max 12 line)) source))

(reduce + all-batteries)