(ns ^:figwheel-always offcourse.core
    (:require [reagent.core :refer [atom render-component]]
              [markdown.core :refer [md->html]]
              [offcourse.store :refer [app-state]]))

(enable-console-print!)

(defn checkpoint-item [uuid val]
  (let [{:keys [url task instructions review]} val
        color (if review "black" "red")]
    ^{:key uuid} [:tr {:style {:color color}}
                  [:td [:input { :type "checkbox" :default-checked true}]]
                  [:td task]
                  [:td url]
                  [:td
                   {:dangerously-set-innerHTML {:__html (md->html instructions)}}]
                  [:td
                   (for [[criterium rating] review]
                     ^{:key criterium} [:p (str (name criterium) " " rating)])]]))

(defn checkpoints-table-headers []
  [:tr
   (for [header-title ["done?" "task" "url" "instructions" "review"]]
     ^{:key header-title} [:th header-title])])

(defn checkpoints-table [checkpoints]
  [:table
   (checkpoints-table-headers)
   (for [[uuid val] checkpoints]
     (checkpoint-item uuid val))])

(defn app []
  (let [checkpoints (@app-state :checkpoints)]
    [:h1 "Offcourse_"
     [checkpoints-table checkpoints]]))

(render-component [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc))
