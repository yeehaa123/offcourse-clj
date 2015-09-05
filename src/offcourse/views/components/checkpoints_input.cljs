(ns offcourse.views.components.checkpoints-input
  (:require [reagent.core :as reagent :refer [atom]]
            [offcourse.views.helpers :refer [valid?]]))

(def input-value (atom "foo"))

(defn handle-input [input]
  (let [val (-> input .-target .-value)]
    (reset! input-value val)))

(defn handle-submit [add-handler]
  (when (valid? @input-value)
    (add-handler @input-value)
    (reset! input-value "")))

(defn checkpoints-input [add-handler]
  [:div
   [:input {:type "text"
            :value @input-value
            :on-change #(handle-input %)}]
   [:input {:type "submit"
            :on-click #(handle-submit add-handler)}]])
