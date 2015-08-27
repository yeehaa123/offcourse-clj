(ns offcourse.store
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [offcourse.models.resource :as resource]))


(def app-state (atom {:resources {}}))

(defn update-resources [resources]
  (swap! app-state #(assoc %1 :resources resources)))

(defn listen-for-updates []
  (go
    (loop []
      (let [resources (<! resource/channel)]
        (update-resources resources)
        (recur)))))

(defn add-resource [url]
  (resource/add url))

(defn delete-resource [uuid]
  (resource/delete uuid))

(listen-for-updates)

(resource/seed)
