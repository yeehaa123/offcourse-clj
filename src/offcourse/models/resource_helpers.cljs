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

(defn generate-string [min max]
  (let [diff (- max min)
        num (+ (rand-int min) diff)
        words (take num (lorem/words))]
    (string/join " " words)))

(defn generate-instructions []
  (let [num (+ (rand-int 3) 0)
        paragraphs (take num (lorem/paragraphs))]
    (if (> num 0)
      (string/join "\n\n" paragraphs))))

(defn generate-review []
  (let [criteria [:clarity :pertinence :timeliness :difficulty :entertainment]
        ratings (for [criterium criteria] {criterium (+ 1 (rand-int 5))})]
    (into {} ratings)))
