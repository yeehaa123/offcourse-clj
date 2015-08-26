(ns offcourse.store
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [offcourse.models.resource :as resource]))

(defonce app-state (atom {:resources @resource/resources}))

(defn update-resources []
  (go
    (loop []
      (let [resources (<! resource/channel)]
        (println resources)
        (swap! app-state #(assoc %1 :resources resources))
        (recur)))))

(defn add-resource [url]
  (resource/add url))

(defn delete-resource [url]
  (resource/delete url))

(update-resources)
