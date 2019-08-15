(ns api.health.probe)

(defn liveness []
  (hash-map :status "ok"))

(defn readiness []
  (hash-map :status "ok"))