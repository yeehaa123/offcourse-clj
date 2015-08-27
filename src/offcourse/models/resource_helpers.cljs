(ns offcourse.models.resource-helpers
  (:require [cljs-uuid-utils.core :refer [make-random-squuid uuid-string]]
            [faker.lorem :as lorem]
            [clojure.string :as string]))

(defn generate-uuid []
  (uuid-string (make-random-squuid)))

(def seed-urls ["www.google.com"
                "www.yahoo.com"
                "www.bing.com"
                "www.altavista.com"])

(defn generate-title []
  (let [num (+ (rand-int 5) 3)
        words (take num (lorem/words))]
    (string/join " " words)))

(defn generate-review []
  (let [criteria [:clarity :pertinence :timeliness :difficulty :entertainment]
        ratings (for [criterium criteria] {criterium (+ 1 (rand-int 5))})]
    (into {} ratings)))
