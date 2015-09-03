(ns offcourse.models.checkpoint
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-uuid-utils.core :refer [make-random-squuid uuid-string]]
            [offcourse.models.resource-helpers :as helpers]))

(defn create [uuid url]
  [(keyword uuid) {:url url}])

(defn -generate-data [uuid url]
  [(keyword uuid) {:done true
                   :url url
                   :task (helpers/generate-task)
                   :instructions (helpers/generate-instructions)
                   :review (helpers/generate-review)}])

(defn add-url [uuid url resources]
  (let [[key val] (create uuid url)]
    (assoc resources key val)))

(defn add-data [uuid url resources]
  (let [[key val] (-generate-data uuid url)]
    (assoc resources key val)))

(defn delete [uuid resources]
  (println uuid)
  (dissoc resources uuid))

(defn mark-as-done [resources uuid]
  (update-in resources [uuid :done] #(not %1)))
