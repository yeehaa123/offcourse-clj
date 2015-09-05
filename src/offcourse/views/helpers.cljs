(ns offcourse.views.helpers
  (:require [clojure.string :refer [blank?]]
            [markdown.core :refer [md->html]]))

(defn valid? [val]
  (not (blank? val)))

(defn delete-button [handler]
  [:button {:on-click handler} "Delete"])

(defn render-markdown [markdown-string]
  {:dangerously-set-innerHTML {:__html (md->html markdown-string)}})

(defn checkbox [val handle-done]
  [:input {:type "checkbox" :default-checked val :on-click #(handle-done)}])
