(ns offcourse.views.components.table
  (:require [offcourse.views.helpers :as helpers]))

(defn headers [titles]
  [:tr (for [header-title titles]
         ^{:key header-title} [:th (name header-title)])])

(defn cell [field-name schema val handler]
  ^{:key field-name} [:td (schema (field-name val) handler)])

(defn row [schema val]
  (let [id (val :id)]
    ^{:key id} [:tr (for [[field-name [cell-schema handler]] schema]
                      (let [handler (partial handler id)]
                        (cell field-name cell-schema val handler)))]))

(defn table [data schema]
   (let [column-titles (keys schema)]
     [:table
      (headers column-titles)
      (for [[_ row-data] data]
        (row schema row-data))]))
