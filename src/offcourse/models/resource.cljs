(ns offcourse.models.resource
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs-uuid-utils.core :refer [make-random-squuid uuid-string]]
            [cljs.core.async :refer [timeout close! chan >! <!]]))

(def channel (chan 10))
(defonce resources (atom {}))

(def seed-urls ["www.google.com"
                "www.yahoo.com"
                "www.bing.com"
                "www.altavista.com"])

(defn generate-uuid []
  (uuid-string (make-random-squuid)))

(defn create [uuid url]
  [(keyword uuid) {:url url}])

(defn add-data [uuid url]
  [(keyword uuid) {:url url
                   :data "bla bla"}])

(defn -add-url [uuid url resources]
  (let [[key val] (create uuid url)]
    (assoc resources key val)))

(defn -add-data [uuid url resources]
  (let [[key val] (add-data uuid url)]
    (assoc resources key val)))

(defn -delete [uuid resources]
  (dissoc resources uuid))

(defn fetch-data [uuid url]
  (go
    (>! channel (swap! resources #(-add-data uuid url %1)))))

(defn add [url]
  (go
    (let [uuid (generate-uuid)]
      (>! channel (swap! resources #(-add-url uuid url %1)))
      (<! (timeout (+ 1000 (rand-int 2000))))
      (fetch-data uuid url))))

(defn delete [uuid]
  (go
   (>! channel (swap! resources #(-delete uuid %1)))))

(defn seed []
  (for [url seed-urls] (add url)))

(defn -delete-all [resources]
  (if (empty? resources)
    resources
    (let [[uuid _] (first resources)]
      (recur (-delete uuid resources)))))

(defn delete-all []
  (go
    (>! channel (swap! resources #(-delete-all %1)))))
