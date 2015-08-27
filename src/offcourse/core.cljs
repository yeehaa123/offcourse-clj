(ns ^:figwheel-always offcourse.core
    (:require [reagent.core :refer [atom render-component]]
              [offcourse.store :refer [app-state]]))

(enable-console-print!)

(defn resource-item [uuid val]
  (let [{:keys [url title instructions review]} val
        color (if title "black" "red")]
    ^{:key uuid} [:tr {:style {:color color}}
                  [:td url]
                  [:td title]
                  [:td instructions]
                  [:td
                   (for [[criterium rating] review]
                     ^{:key criterium} [:p (str (name criterium) " " rating)])]]))

(defn resources-list []
  [:table
   [:tr
    (for [header-title ["url" "title" "instructions" "review"]]
      ^{:key header-title} [:th header-title])]
   (for [[uuid val] (@app-state :resources)]
     (resource-item uuid val))])

(defn app []
  [:h1 "Offcourse_"
   [resources-list]])

(render-component [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc)
  (.log js/console (:resources @app-state)))
