(ns api.schedule.model
  (:require [schema.core :as s]))

(s/defschema Create
  {:docker-image s/Str
   :cron s/Str
   :command s/Str})

(s/defschema Patch
  {(s/optional-key :docker-image) s/Str
   (s/optional-key :cron) s/Str
   (s/optional-key :command) s/Str})

(s/defschema Delete
  {:result s/Int})

(s/defschema Logs
  {:exit s/Str
   :out s/Str
   :error s/Str})

(s/defschema Schedule
  {:_id s/Str
   :client-id s/Str
   :docker-image s/Str
   :cron s/Str
   :command s/Str
   :status s/Str
   :created-at s/Str
   :updated-at (s/maybe s/Str)
   :deleted-at (s/maybe s/Str)
   :executed-at (s/maybe s/Str)
   :logs (s/maybe [Logs])})

(s/defschema Probe
  {:status s/Str})
