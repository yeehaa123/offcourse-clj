(ns offcourse.views.components.checkpoints-table
  (:require [offcourse.views.helpers :refer [renderMarkdown table-headers]]))

(defn review-list [review]
  (for [[criterium rating] review]
    ^{:key criterium} [:p (str (name criterium) " " rating)]))

(defn checkpoint-field [[key val]]
  (case key
    :done ^{:key key} [:td [:input {:type "checkbox" :default-checked val}]]
    :instructions ^{:key key} [:td (renderMarkdown val)]
    :review ^{:key key} [:td (review-list val)]
    ^{:key key} [:td val]))

(defn checkpoint-item [uuid val]
  ^{:key uuid} [:tr
                (for [field (into {} val)]
                  (checkpoint-field field))])

(defn checkpoints-table [checkpoints]
  [:table
   (table-headers checkpoints)
   (for [[uuid val] checkpoints]
     (checkpoint-item uuid val))])
