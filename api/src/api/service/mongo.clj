(ns api.service.mongo
  "Just a mongo CRUD."
  (:require [api.boot.connector :as connector]
            [monger.collection :as mc]
            [monger.credentials :as mcred])
  (:import org.bson.types.ObjectId))

(let [collection "cron"
      db (connector/monger)]

  (defn id-to-str
    "Convert mongo ObjectId to string."
    [data]
    (if-let [id (:_id data)]
      (assoc data :_id (str id))))

  (defn create
    "Create new document."
    [data]
    (id-to-str (mc/insert-and-return
                db collection data)))

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
    (.getN (mc/update-by-id db collection (ObjectId. id) data))))