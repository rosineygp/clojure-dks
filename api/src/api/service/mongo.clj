(ns api.service.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [clj-time.core :as t]
            [clj-time.local :as l])
  (:import org.bson.types.ObjectId))

(def database "schedule")
(def collection "cron")

(defn mongo [database]
  (let [conn (mg/connect)
        db (mg/get-db conn database)]
    {:conn conn
     :db db}))

(defn create [data]
  (let [{conn :conn
         db :db} (mongo database)]
    (mc/insert-and-return
     db collection (merge
                    data
                    {:status "created"
                     :client-id "anonymous"
                     :logs []
                     :created-at (str (l/local-now))
                     :updated-at nil
                     :deleted-at nil
                     :executed-at nil}))))

(defn restore []
  (let [{conn :conn
         db :db} (mongo database)]
    (mc/find db collection)))