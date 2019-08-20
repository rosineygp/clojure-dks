(ns api.service.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.credentials :as mcred])
  (:import org.bson.types.ObjectId))

(let [host (or (System/getenv "MONGO_HOST") "localhost")
      port (Integer/parseInt (or (System/getenv "MONGO_PORT") "27017"))
      database (or (System/getenv "MONGO_DATABASE_NAME") "schedule")
      collection "cron"
      credentials (mcred/create
                   (or (System/getenv "MONGO_USER") "root")
                   (or (System/getenv "MONGO_AUTH_DB") "admin")
                   (or (System/getenv "MONGO_PASSWORD") "password"))
      conn (mg/connect-with-credentials host port credentials)
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