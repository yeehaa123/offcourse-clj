(ns ^:figwheel-always offcourse.core
    (:require [reagent.core :refer [atom render-component]]
              [offcourse.store :refer [app-state]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

(defn resource-item [val]
  (println val)
  (let [url (val :url)
        data (val :data)]
    [:li (if data
           data
           url)]))

(defn resources-list []
  [:ul
   (for [[_ val] (:resources @app-state)]
     (let [url (val :url)]
       ^{:key url} [resource-item val]))])

(defn app []
  [:h1 "Offcourse_"
   [resources-list]])

(render-component [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc)
  (.log js/console (:resources @app-state)))
