(ns clojure-test.part2
  (:require [taoensso.timbre :as timbre]
            [clojure.java.io :as io]
            [cheshire.core :as chess]))
(timbre/refer-timbre)

(def transaction (atom []))

(defn parse-generate-string
  [data]
  (chess/parse-string
    (chess/generate-string data) true))

(defn decode-stream
  [data]
  (chess/decode-stream
    (io/reader
      data)))

(defn add-transaction [arg]
  (swap! transaction conj arg))

(defn get-data [data field arg]
  (loop [[x & xs] data]
    (if x
      (let [res (field x)]
        (if-not (= res arg)
          (recur xs)
          x))
      "not found")
    ))

(defn get-all-field [data field arg]
  (loop [[x & xs] data vres []]
    (if x
      (let [res (field x)]
        (if (= res arg)
          (recur xs (conj vres x))
          (recur xs vres))
        )
      vres
      )
    ))

(defn get-body [ctx]
  (let [body (try
               (parse-generate-string
                 (decode-stream
                   (get-in ctx [:body])))
               (catch Exception e
                 "nil"
                 ))]
    body))


(defn transaction-service [ctx]
  (let [body (get-body ctx)
        id (:transaction_id body)
        parent_id (:parent_id body)
        parent (if-not (nil? parent_id)
                 (get-data @transaction :transaction_id parent_id)
                 nil)]

    (if (nil? id)
      "transaction_id can't nil"
      (if (= "not found" parent)
        (str "parent_id " parent_id " not found")
        (if (= "not found" (get-data @transaction :transaction_id id))
          (do
            (add-transaction body)
            (chess/generate-string {:status "ok"}))
          (str "transaction_id " id " already exist"))))))

(defn get-type [type]
  (let [the-type (get-all-field @transaction :type type)
        res (map #(:transaction_id %) the-type)]
    (chess/generate-string res)))

(defn get-from-id [id]
  (let [data (get-data @transaction :transaction_id (read-string id))]
    (chess/generate-string data)
    ))

(defn get-sum [id]
  (try
    (let [parent-id (read-string id)
          parents (get-data @transaction :transaction_id parent-id)
          parent-trans (get-all-field @transaction :parent_id parent-id)
          all-trans (conj parent-trans parents)
          amount (map #(:amount %) all-trans)
          result (reduce + amount)]
      (chess/generate-string {:sum result})
      )
    (catch Exception e
      (str "error " id)
      ))
  )