(ns worker.service.mongo
  "Just a mongo CRUD."
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

  (defn id-to-str
    "Convert mongo ObjectId to string."
    [data]
    (if-let [id (:_id data)]
      (assoc data :_id (str id))))

  (defn restore
    "Get all collection entries."
    []
    (map id-to-str (mc/find-maps db collection)))

  (defn restore-by-id
    "Get a document by Id."
    [id]
    (id-to-str (mc/find-map-by-id db collection (ObjectId. id))))

  (defn update-by-id
    "Update a document by Id."
    [id data]
    (.getN (mc/update-by-id db collection (ObjectId. id) data)))

  (defn find
    "Search things in database."
    [query]
    (map id-to-str (mc/find-maps db collection query))))