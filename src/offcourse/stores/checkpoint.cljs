(ns offcourse.stores.checkpoint
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [offcourse.models.resource-helpers :as helpers]
            [offcourse.models.checkpoint :as model]
            [cljs.core.async :refer [timeout close! chan >! <!]]))

(def channel (chan 10))
(defonce resources (atom {}))

(defn -fetch-data [uuid url]
  (go
    (>! channel (swap! resources #(model/add-data uuid url %1)))))

(defn add [url]
  (go
    (let [uuid (helpers/generate-uuid)]
      (>! channel (swap! resources #(model/add-url uuid url %1)))
      (<! (timeout (+ 1000 (rand-int 2000))))
      (-fetch-data uuid url))))

(defn delete [uuid]
  (go
   (>! channel (swap! resources #(model/delete uuid %1)))))

(defn seed []
  (for [url helpers/seed-urls] (add url)))

(defn delete-all []
  (go
    (>! channel (reset! resources {}))))

(defn mark-as-done [uuid]
  (go
    (>! channel (swap! resources #(model/mark-as-done %1 uuid)))))
