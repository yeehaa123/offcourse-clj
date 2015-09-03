(ns offcourse.views.components.checkpoints-table
  (:require [offcourse.views.helpers :as helpers]))

(defn review-list [review]
  (for [[criterium rating] review]
    ^{:key criterium} [:p (str (name criterium) " " rating)]))

(defn checkpoint-field [[key val] handle-done]
  (case key
    :done ^{:key key} [:td (helpers/checkbox  val handle-done)]
    :instructions ^{:key key} [:td (helpers/render-markdown val)]
    :review ^{:key key} [:td (review-list val)]
    ^{:key key} [:td val]))

(defn checkpoint-item [[uuid val] handle-done]
  ^{:key uuid} [:tr
                (for [field (into {} val)]
                  (checkpoint-field field (partial handle-done uuid)))])

(defn checkpoints-table [checkpoints handle-done]
  [:table
   (helpers/table-headers checkpoints)
   (for [checkpoint checkpoints]
     (checkpoint-item checkpoint handle-done))])
