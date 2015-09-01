(ns ^:figwheel-always offcourse.core
    (:require [reagent.core :refer [render-component]]
              [offcourse.store :refer [app-state]]
              [offcourse.views.containers.app :refer [app]]))

(enable-console-print!)

(render-component [app]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc))
