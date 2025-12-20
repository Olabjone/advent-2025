(ns day-four
  (:require input))

(def lines (input/read-lines "day-4-input"))

(defn parse-grid [lines]
  (mapv vec lines))

;
(def directions-8
  (vec
     (for [dx [-1 0 1]
           dy [-1 0 1]
           :when (not (and (zero? dx) (zero? dy)))]
       [dx dy])))

(defn in-bounds? [grid r c]
  (and (>= r 0)
       (< r (count grid))
       (>= c 0)
       (< c (count (get grid r)))))

(defn count-adjacent-ats [grid r c]
  (count
    (for [[dr dc] directions-8
      :let [nr (+ r dr)
            nc (+ c dc)]
        :when (and (in-bounds? grid nr nc)
                   (= \@ (get-in grid [nr nc])))]
      1)))

(defn annotate-grid [grid]
  (mapv
    (fn [r]
      (mapv
        (fn [c]
          (if (= \@ (get-in grid [r c]))
            (count-adjacent-ats grid r c)
            (get-in grid [r c])))
        (range (count (grid r)))))
    (range (count grid ))))

(defn <-4-count [annotated-grid]
  (reduce (fn [acc cell] (if (and (number? cell)
                                  (< cell 4))
                           (inc acc)
                           acc))
            0 (flatten annotated-grid)))

(def total-count
  (<-4-count
    (annotate-grid
      (parse-grid lines))))

;;for part 2

(defn print-grid [grid]
  (doseq [row grid]
    (println (apply str row)))
  (println))

(defn prune-grid [grid annotated]
  (mapv
    (fn [r]
      (mapv
        (fn [c]
          (let [orig (get-in grid [r c])
                ann  (get-in annotated [r c])]
            (if (and (= orig \@)
                     (number? ann)
                     (>= ann 4))
              \@
              \.)))
        (range (count (grid r)))))
    (range (count grid))))


(defn step [grid]
  (let [annotated (annotate-grid grid)]
    (prune-grid grid annotated)))


(defn run-until-stable [grid]
  (loop [g grid]
    (let [next (step g)]
      (if (= g next)
        g
        (recur next)))))

(def initial-grid (parse-grid lines))

(def initial-count (count (filter #{\@} (flatten initial-grid))))

(def final-grid (run-until-stable initial-grid))

(def final-count (count (filter #{\@} (flatten final-grid))))

(def answer (- initial-count final-count))

(println "Initial:")
(print-grid initial-grid)

(println "Final:")
(print-grid final-grid)

(println answer)