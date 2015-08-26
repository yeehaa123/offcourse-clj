(ns offcourse.models.resource
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [timeout close! chan >! <!]]))

(def channel (chan 1))

(def seed-urls ["www.google.com"
                "www.yahoo.com"
                "www.bing.com"
                "www.altavista.com"])

(defn create [url]
  [(keyword url) {:url url}])

(defn add-data [url]
  [(keyword url) {:url url
                  :data "bla bla"}])

(def seed-resources
  (let [tuples (map #(create %1) seed-urls)]
    (into {} tuples)))

(defonce resources (atom seed-resources))

(defn -add-url [url resources]
  (let [[key val] (create url)]
    (assoc resources key val)))

(defn -add-data [url resources]
  (let [[key val] (add-data url)]
    (assoc resources key val)))

(defn -delete [url resources]
  (dissoc resources (keyword url)))

(defn fetch-data [url]
  (go
    (<! (timeout 3000))
    (>! channel (swap! resources #(-add-data url %1)))))

(defn add [url]
  (go
    (>! channel (swap! resources #(-add-url url %1)))
    (fetch-data url)))

(defn delete [url]
  (go
   (>! channel (swap! resources #(-delete url %1)))))
