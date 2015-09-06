(ns offcourse.views.containers.app
  (:require [offcourse.store :refer [app-state]]
            [offcourse.views.components.table :refer [table]]
            [offcourse.views.components.checkpoints-input :refer [checkpoints-input]]
            [offcourse.actions.view-model :as actions]
            [offcourse.views.schemas.checkpoint :as cp-schema]))


(defn app []
  (let [checkpoints (@app-state :checkpoints)]
    [:section
     [:h1 "Offcourse_"]
     [table checkpoints cp-schema/schema]
     [checkpoints-input actions/add-checkpoint]]))
