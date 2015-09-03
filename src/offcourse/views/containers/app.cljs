(ns offcourse.views.containers.app
  (:require [offcourse.store :refer [app-state]]
            [offcourse.views.components.checkpoints-table :refer [checkpoints-table]]
            [offcourse.actions.view-model :as actions]))

(defn app []
  (let [checkpoints (@app-state :checkpoints)]
    [:h1 "Offcourse_"
     [checkpoints-table checkpoints actions/handle-done]]))
