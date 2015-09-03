(ns offcourse.stores.checkpoint
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [offcourse.models.resource-helpers :as helpers]
            [offcourse.models.checkpoint :as checkpoint]
            [cljs.core.async :refer [timeout close! chan >! <!]]))

(def channel (chan 10))
(defonce checkpoints (atom {}))

(defn listen-for-updates []
  (go
    (loop []
      (let [{:keys [id] :as instance } (<! checkpoint/channel)]
        (println instance)
        (>! channel (swap! checkpoints #(assoc %1 id instance)))
        (recur)))))

(defn add [url]
    (checkpoint/add url))

(defn seed []
  (for [url helpers/seed-urls] (add url)))

(defn delete [uuid]
  (go
    (>! channel (swap! checkpoints #(dissoc %1 uuid)))))

(defn delete-all []
  (go
    (>! channel (reset! checkpoints {}))))

(defn mark-as-done [uuid]
    (checkpoint/mark-as-done (@checkpoints uuid)))

(listen-for-updates)
