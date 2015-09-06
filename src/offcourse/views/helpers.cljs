(ns offcourse.views.helpers
  (:require [clojure.string :refer [blank?]]
            [markdown.core :refer [md->html]]))

(defn valid? [val]
  (not (blank? val)))

(defn delete-button [_ handle-delete]
  [:button {:on-click #(handle-delete)} "Delete"])

(defn render-markdown [markdown-string]
  {:dangerously-set-innerHTML {:__html (md->html markdown-string)}})

(defn checkbox [val handle-done]
  [:input {:type "checkbox" :default-checked val :on-click #(handle-done)}])

(defn review-list [review]
  [:ul
   (for [[criterium rating] review]
     ^{:key criterium} [:li (str (name criterium) " " rating)])])

(defn strong-field [val] [:strong val])

(defn field [val] val)
