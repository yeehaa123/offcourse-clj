(ns offcourse.views.schemas.checkpoint
  (:require [offcourse.views.helpers :as helpers]
            [offcourse.actions.view-model :as actions]))

(def schema {:done [helpers/checkbox actions/handle-done]
             :task [helpers/strong-field]
             :instructions [helpers/render-markdown]
             :url [helpers/field]
             :title [helpers/field]
             :review [helpers/review-list]
             :delete [helpers/delete-button actions/delete-checkpoint]})
