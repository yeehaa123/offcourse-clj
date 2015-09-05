(ns offcourse.models.checkpoint
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-uuid-utils.core :refer [make-random-squuid uuid-string]]
            [offcourse.models.resource-helpers :as helpers]
            [cljs.core.async :refer [chan timeout >! <!]]))

(def channel (chan 10))

(defn create [url]
  (let [uuid (helpers/generate-uuid)]
    {:id uuid
     :done false
     :task (helpers/generate-string 2 5)
     :instructions (helpers/generate-instructions)
     :url url}))

(defn -generate-data [checkpoint]
  (assoc checkpoint
         :title (helpers/generate-string 2 8)
         :review (helpers/generate-review)))

(defn add [url]
  (go
    (let [checkpoint (create  url)]
      (>! channel checkpoint)
      (<! (timeout (rand-int 3000)))
      (>! channel (-generate-data checkpoint)))))

(defn mark-as-done [resource]
  (go
    (>! channel (update-in resource [:done] #(not %1)))))
