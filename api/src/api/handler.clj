(ns api.handler
  "User IO"
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [api.model.all :as model]
            [api.controller.crud :as schedule]
            [api.health.probe :as health]))

(def app
  "Definition of endpoints."
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Api Job Scheduler"
                   :description "Simple CRUD API for schedule jobs with docker"}
            :tags [{:name "api", :description "schedule jobs"}
                   {:name "health", :description "liveness and readiness probes"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}

   (context "/api/v1" []
     :tags ["api"]

     (POST "/schedule" []
       :return model/Schedule
       :body [data model/Create]
       :summary "create job"
       :description
       "Just schedule a docker job\n
       [json body]
       docker-image: The any docker image added in a public repository like hub.docker.com
       cron: an array with date and time [
         year,
         month,
         day,
         hour,
         minute
       ]
       command: string for command line (ex. ls -la)"
       (ok (schedule/create data)))

     (GET "/schedule" []
       :return [model/Schedule]
       :summary "return jobs"
       :description
       "Return all jobs registered"
       (ok (schedule/restore)))

     (GET "/schedule/:id" []
       :path-params [id :- s/Str]
       :return model/Schedule
       :summary "return a job by id"
       :description
       "Return all information from a document\n
       [path parameters]
       id: mongo object id"
       (ok (schedule/restore-by-id id)))

     (PUT "/schedule/:id" []
       :path-params [id :- s/Str]
       :return model/Schedule
       :body [data model/Create]
       :summary "full job update by id"
       :description
       "Alter all data from a job\n
       [path parameters]
       id: mongo object id\n
       [json body]
       docker-image: The any docker image added in a public repository like hub.docker.com
       cron: an array with date and time [
        year,
        month,
        day,
        hour,
        minute
       ]
       command: string for command line (ex. ls -la)"
       (ok (schedule/update id data)))

     (PATCH "/schedule/:id" []
       :path-params [id :- s/Str]
       :return model/Schedule
       :body [data model/Patch]
       :summary "update an item or many from a job"
       :description
       "Update just one item or many from a job\n
       [path parameters]
       id: mongo object id\n
       [json body]
       * docker-image: The any docker image added in a public repository like hub.docker.com
       * cron: Scheduled method based on Linux cron job (Ex. * * * * *) Every minute
       * command: string for command line (ex. ls -la)\n
       * optional\n
       Notes: if any value passed, :updated-at will be updated with now() values"
       (ok (schedule/patch id data)))

     (DELETE "/schedule/:id" []
       :path-params [id :- s/Str]
       :return model/Schedule
       :summary "delete a job by id"
       :description
       "Apply a soft delete for a job
       [path parameters]
       id: mongo object id\n
       Notes: delete job is asynchronous and take a time to stop a running job"
       (ok (schedule/delete id))))

   (context "/health" []
     :tags ["health"]

     (GET "/liveness" []
       :return model/Probe
       :summary "liveness state"
       (ok (health/liveness)))

     (GET "/readiness" []
       :return model/Probe
       :summary "readiness state"
       (ok (health/readiness))))))
