(ns clojure-test.part1
  (:require [clojure.string :as str]))

;; No 1
(defn calculate
  "docstring"
  [x y]
    (try
      (* x y)
      (catch Exception e
        0))
  )

;; No 2


;; No 3
(defn count-char
  "docstring"
  [arg]
  (let [vec-str (vec (seq arg))
        vec-res (vec (frequencies vec-str))
        result (str/join (flatten vec-res))]
    result
    )
  )
