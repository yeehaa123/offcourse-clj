(ns offcourse.views.helpers
  (:require [markdown.core :refer [md->html]]))

(defn extract-titles [object]
  (->> object
       first
       rest
       (into {})
       keys))

(defn table-headers [object]
  [:tr
   (for [header-title (extract-titles object)]
     ^{:key header-title} [:th (name header-title)])])

(defn render-markdown [markdown-string]
  {:dangerously-set-innerHTML {:__html (md->html markdown-string)}})

(defn checkbox [val handle-done]
  [:input {:type "checkbox" :default-checked val :on-click #(handle-done)}])
