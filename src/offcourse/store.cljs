(ns offcourse.store
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [offcourse.models.checkpoint :as checkpoint]))

(def app-state (atom {}))

(defn update-checkpoints [checkpoints]
  (swap! app-state #(assoc %1 :checkpoints checkpoints)))

(defn listen-for-updates []
  (go
    (loop []
      (let [checkpoints (<! checkpoint/channel)]
        (update-checkpoints checkpoints)
        (recur)))))

(listen-for-updates)

