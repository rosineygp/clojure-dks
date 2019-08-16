(ns api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [api.schedule.model :as model]
            [api.schedule.crud :as schedule]
            [api.health.probe :as health]))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Api Job Scheduling"
                   :description "Simple CRUD API for schedule jobs in docker"}
            :tags [{:name "api", :description "Schedule jobs in docker"}
                   {:name "health", :description "Liveness and Readiness Probes"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}

   (context "/api/v1" []
     :tags ["api"]

     (POST "/schedule" []
       :return model/Schedule
       :body [data model/Create]
       :summary "Schedule new docker job"
       (ok (schedule/create data)))

     (GET "/schedule" []
       :return [model/Schedule]
       :summary "return all jobs scheduled"
       (ok (schedule/restore)))

     (GET "/schedule/:id" []
       :path-params [id :- s/Str]
       :return model/Schedule
       :summary "return a job by Object id"
       (ok (schedule/restore-by-id id)))

     (PUT "/schedule/:id" []
       :path-params [id :- s/Int]
       :return model/Schedule
       :body [data model/update]
       :summary "Update a job by id"
       (ok (schedule/update id)))

     (DELETE "/schedule/:id" []
       :path-params [id :- s/Int]
       :return model/Schedule
       :summary "Delete a job by id"
       (ok (schedule/delete id))))

   (context "/health" []
     :tags ["health"]

     (GET "/liveness" []
       :return {:status s/Str}
       :summary "return api liveness state"
       (ok (health/liveness)))

     (GET "/readiness" []
       :return {:status s/Str}
       :summary "return api readiness state"
       (ok (health/readiness))))))
