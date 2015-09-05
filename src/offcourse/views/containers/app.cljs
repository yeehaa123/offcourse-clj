(ns offcourse.views.containers.app
  (:require [offcourse.store :refer [app-state]]
            [offcourse.views.components.checkpoints-table :refer [checkpoints-table]]
            [offcourse.views.components.checkpoints-input :refer [checkpoints-input]]
            [offcourse.actions.view-model :as actions]))

(defn app []
  (let [checkpoints (@app-state :checkpoints)]
    [:section
     [:h1 "Offcourse_"]
     [checkpoints-table checkpoints actions/handle-done]
     [checkpoints-input actions/add-checkpoint]]))
