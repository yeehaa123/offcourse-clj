(ns offcourse.views.components.table
  (:require [offcourse.views.helpers :as helpers]))

(defn add-extra-fields [extra-fields keys]
  (if keys
    (concat keys extra-fields)
    keys))

(defn extract-header-titles
  ([object] (extract-header-titles object []))
  ([object extra-fields] (->> object
                              first
                              rest
                              (into {})
                              keys
                              (add-extra-fields extra-fields))))

(defn map-object [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn review-list [review]
  (for [[criterium rating] review]
    ^{:key criterium} [:p (str (name criterium) " " rating)]))

(defn schema [val handlers]
  {:done (helpers/checkbox val (handlers :handle-done))
   :instructions (helpers/render-markdown val)
   :review (review-list val)
   :delete (helpers/delete-button (handlers :handle-delete))})

(defn cell-content [key val handlers]
  (or
   ((schema val handlers) key)
   val))

(defn headers [titles]
  [:tr (for [header-title titles]
         ^{:key header-title} [:th (name header-title)])])

(defn cell [field-name val handlers]
  ^{:key field-name} [:td (cell-content field-name
                                        (val field-name)
                                        handlers)])

(defn row [field-names val handlers]
  (let [id (val :id)
        handlers (map-object #(partial %1 id) handlers)]
    ^{:key id} [:tr (for [field-name field-names]
                      (cell field-name val handlers))]))

(defn table
  ([data handlers] (table data handlers []))
  ([data handlers extra-fields]
   (let [column-titles (extract-header-titles data extra-fields)]
     [:table
      (headers column-titles)
      (for [[_ row-data] data]
        (row column-titles row-data handlers))])))
