(ns api.service.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(def database "schedule")
(def collection "cron")

(let [conn (mg/connect)
      db (mg/get-db conn database)]

  (defn id-to-str [data]
    (if-let [id (:_id data)]
      (assoc data :_id (str id))))

  (defn create [data]
    (id-to-str (mc/insert-and-return
                db collection data)))

  (defn restore []
    (map id-to-str (mc/find-maps db collection)))

  (defn restore-by-id [id]
    (id-to-str (mc/find-map-by-id db collection (ObjectId. id))))

  (defn update-by-id [id data]
    (.getN (mc/update-by-id db collection (ObjectId. id) data))))