(ns metrics.health.core
  (:require [clojure.set :as set]
            [metrics.core :refer [default-registry metric-name]]
            [metrics.utils :refer [desugared-title]])
  (:import clojure.lang.IFn
           [com.codahale.metrics MetricRegistry Gauge]
           [com.codahale.metrics.health HealthCheck HealthCheck$Result HealthCheckRegistry]))

(def ^{:tag HealthCheckRegistry :doc "Default health check registry used by public API functions when no explicit registry argument is given"}
  default-healthcheck-registry
  (HealthCheckRegistry.))

(defn healthy
  "Returns a healthy result."
  ([] (HealthCheck$Result/healthy))
  ([^String message & args]
     (HealthCheck$Result/healthy (apply format message args))))

(defn unhealthy
  "Returns a unhealthy result."
  ([^String message & args]
     (HealthCheck$Result/unhealthy (apply format message args))))

(defn new-hc
  "Wrap a fn to ensure it returns healthy/unhealthy. Any exception or
  non-healthy result is considered unhealthy and returned as such."
  ([^IFn hc-fn] (proxy [HealthCheck] []
                  (check []
                    (let [result (hc-fn)]
                      (if (instance? HealthCheck$Result result)
                        result
                        (unhealthy (str "Bad HealthCheck result: " result))))))))

(defn healthcheck-fn
  "Create and register a new healthcheck.  hc-fn must return
  healthy/unhealthy; any other result, (including an exception), will
  be considered unhealthy."
  ([title ^IFn hc-fn]
     (healthcheck-fn default-healthcheck-registry title hc-fn))
  ([^HealthCheckRegistry reg title ^IFn hc-fn]
     (let [hc (new-hc hc-fn)
           s (metric-name title)]
       ;; REVIEW: Should this automatically remove a previous check of
       ;; the same title? (jw 14-08-28)
       (.register reg s hc)
       hc)))

(defmacro defhealthcheck
  "Define a new Healthcheck metric with the given title and a function.
   to call to retrieve the value of the Healthcheck.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a healthcheck titled \"default.default.foo\" into var foo
    (defhealthcheck foo ,,,)
    (defhealthcheck \"foo\" ,,,)

    ; Define a healthcheck titled \"a.b.c\" into var c
    (defhealthcheck [a b c] ,,,)
    (defhealthcheck [\"a\" \"b\" \"c\"] ,,,)
    (defhealthcheck [a \"b\" c] ,,,)
  "
  ([title ^clojure.lang.IFn f]
   (let [[s title] (desugared-title title)]
     `(def ~s (healthcheck-fn '~title ~f))))
  ([^HealthCheckRegistry reg title ^clojure.lang.IFn f]
   (let [[s title] (desugared-title title)]
     `(def ~s (healthcheck-fn ~reg '~title ~f)))))

(defn check
  "Run a given HealthCheck."
  [^HealthCheck h]
  (.check ^HealthCheck h))

(defn check-all
  "Returns a map with the keys :healthy & :unhealthy, each containing
  the associated checks. Note that the items of each vector are of
  type
  java.util.Collections$UnmodifiableMap$UnmodifiableEntrySet$UnmodifiableEntry,
  but key & val work on it."
  ([] (check-all default-healthcheck-registry))
  ([^HealthCheckRegistry reg]
     (let [results (.runHealthChecks reg)
           group-results (group-by #(.isHealthy ^HealthCheck$Result (val %)) results)]
       (set/rename-keys group-results {true :healthy false :unhealthy}))))
