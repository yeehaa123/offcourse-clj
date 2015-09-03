(ns offcourse.models.checkpoint
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-uuid-utils.core :refer [make-random-squuid uuid-string]]
            [offcourse.models.resource-helpers :as helpers]
            [cljs.core.async :refer [chan timeout >! <!]]))

(def channel (chan 10))

(defn create [uuid url]
  (let [uuid (helpers/generate-uuid)]
    {:id uuid :url url}))

(defn -generate-data [{:keys [id url]}]
    {:id id
     :done true
     :url url
     :task (helpers/generate-task)
     :instructions (helpers/generate-instructions)
     :review (helpers/generate-review)})

(defn add [url]
  (go
    (let [checkpoint (create uuid url)]
      (>! channel checkpoint)
      (<! (timeout (rand-int 3000)))
      (>! channel (-generate-data checkpoint)))))

(defn mark-as-done [resource]
  (go
    (>! channel (update-in resource [:done] #(not %1)))))

