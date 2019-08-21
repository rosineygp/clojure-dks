(ns api.health.probe
  "Kuberntes based probes.")

(defn liveness
  "Return the liveness state"
  []
  (hash-map :status "ok"))

(defn readiness
  "Return the readiness state"
  []
  (hash-map :status "ok"))