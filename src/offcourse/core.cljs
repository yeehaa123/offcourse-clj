(ns ^:figwheel-always offcourse.core
    (:require [reagent.core :refer [atom render-component]]
              [offcourse.store :refer [app-state]]))

(enable-console-print!)

(defn resource-item [val]
  (let [url (val :url)
        data (val :data)
        color (if data "red" "black")]
    [:li {:style {:color color}} url]))

(defn resources-list []
  [:ul
   (for [[uuid val] (:resources @app-state)]
     ^{:key uuid} [resource-item val])])

(defn app []
  [:h1 "Offcourse_"
   [resources-list]])

(render-component [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc)
  (.log js/console (:resources @app-state)))
