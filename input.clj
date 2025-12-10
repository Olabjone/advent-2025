(ns input
    (:require [clojure.java.io :as io]))

(defn read-lines
      "Reads all lines from a resource or file path and returns a seq of strings."
      [path]
      (with-open [rdr (io/reader path)]
                 (doall (line-seq rdr))))