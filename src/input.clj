(ns input
    (:require [clojure.java.io :as io]
              [clojure.string :as str]))

(defn read-lines
      "Reads all lines from a resource or file path and returns a seq of strings."
      [path]
      (with-open [rdr (io/reader path)]
                 (doall (line-seq rdr))))

(defn split-lines-flat [path delimiter]
  (mapcat (fn [line]
         ( str/split line (re-pattern delimiter)))
       (read-lines path)))