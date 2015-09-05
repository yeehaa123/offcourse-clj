(ns offcourse.views.containers.app
  (:require [offcourse.store :refer [app-state]]
            [offcourse.views.components.table :refer [table]]
            [offcourse.views.components.checkpoints-input :refer [checkpoints-input]]
            [offcourse.actions.view-model :as actions]))

(def table-handlers {:handle-done actions/handle-done
                     :handle-delete actions/delete-checkpoint})

(defn app []
  (let [checkpoints (@app-state :checkpoints)]
    [:section
     [:h1 "Offcourse_"]
     [table checkpoints table-handlers]
     [checkpoints-input actions/add-checkpoint]]))
