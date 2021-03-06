(ns offcourse.actions.view-model
  (:require [offcourse.stores.checkpoint :as checkpoint]))

(defn add-checkpoint [url]
  (checkpoint/add url))

(defn delete-checkpoint [uuid]
  (checkpoint/delete uuid))

(defn seed-checkpoints []
  (checkpoint/seed))

(defn handle-done [uuid]
  (checkpoint/mark-as-done uuid))
