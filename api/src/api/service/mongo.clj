(ns api.service.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(def database "schedule")
(def collection "cron")

(defn mongo [database]
  (let [conn (mg/connect)
        db (mg/get-db conn database)]
    {:conn conn
     :db db}))

(defn id-to-str [data]
  (if-let [id (:_id data)]
    (assoc data :_id (str id))))

(defn create [data]
  (let [{db :db} (mongo database)]
    (id-to-str (mc/insert-and-return
                db collection data))))
(defn restore []
  (let [{db :db} (mongo database)]
    (map id-to-str (mc/find-maps db collection))))

(defn restore-by-id [id]
  (let [{db :db} (mongo database)]
    (id-to-str (mc/find-map-by-id db collection (ObjectId. id)))))

(defn update-by-id [id data]
  (let [{db :db} (mongo database)]
    (.getN (mc/update-by-id db collection (ObjectId. id) data))))