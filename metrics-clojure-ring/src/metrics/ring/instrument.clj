(ns metrics.ring.instrument
  (:require [metrics.core :refer [default-registry]]
            [metrics.counters :refer (counter inc! dec!)]
            [metrics.meters :refer (meter mark!)]
            [metrics.timers :refer (timer time!)]
            [clojure.string :as string])
  (:import [com.codahale.metrics MetricRegistry]))

(def default-options
  {:prefix []})

(defn- mark-in! [metric-map k]
  (when-let [metric (metric-map k (metric-map :other))]
    (mark! metric)))

(defn- ring-metrics
  "Returns the core ring metrics, each one prefixed with the `prefix` option"
  [reg {:keys [prefix] :as options}]
  (let [active-requests (counter reg (concat prefix ["ring" "requests" "active"]))
        requests (meter reg (concat prefix ["ring" "requests" "rate"]))
        responses (meter reg (concat prefix ["ring" "responses" "rate"]))
        schemes {:http  (meter reg (concat prefix ["ring" "requests-scheme" "rate.http"]))
                 :https (meter reg (concat prefix ["ring" "requests-scheme" "rate.https"]))}
        statuses {2 (meter reg (concat prefix ["ring" "responses" "rate.2xx"]))
                  3 (meter reg (concat prefix ["ring" "responses" "rate.3xx"]))
                  4 (meter reg (concat prefix ["ring" "responses" "rate.4xx"]))
                  5 (meter reg (concat prefix ["ring" "responses" "rate.5xx"]))}
        times {:get     (timer reg (concat prefix ["ring" "handling-time" "GET"]))
               :put     (timer reg (concat prefix ["ring" "handling-time" "PUT"]))
               :post    (timer reg (concat prefix ["ring" "handling-time" "POST"]))
               :head    (timer reg (concat prefix ["ring" "handling-time" "HEAD"]))
               :delete  (timer reg (concat prefix ["ring" "handling-time" "DELETE"]))
               :options (timer reg (concat prefix ["ring" "handling-time" "OPTIONS"]))
               :trace   (timer reg (concat prefix ["ring" "handling-time" "TRACE"]))
               :connect (timer reg (concat prefix ["ring" "handling-time" "CONNECT"]))
               :other   (timer reg (concat prefix ["ring" "handling-time" "OTHER"]))}
        request-methods {:get     (meter reg (concat prefix ["ring" "requests" "rate.GET"]))
                         :put     (meter reg (concat prefix ["ring" "requests" "rate.PUT"]))
                         :post    (meter reg (concat prefix ["ring" "requests" "rate.POST"]))
                         :head    (meter reg (concat prefix ["ring" "requests" "rate.HEAD"]))
                         :delete  (meter reg (concat prefix ["ring" "requests" "rate.DELETE"]))
                         :options (meter reg (concat prefix ["ring" "requests" "rate.OPTIONS"]))
                         :trace   (meter reg (concat prefix ["ring" "requests" "rate.TRACE"]))
                         :connect (meter reg (concat prefix ["ring" "requests" "rate.CONNECT"]))
                         :other   (meter reg (concat prefix ["ring" "requests" "rate.OTHER"]))}]
    {:active-requests active-requests
     :requests requests
     :responses responses
     :schemes schemes
     :statuses statuses
     :times times
     :request-methods request-methods}))

(defn handle-request [handler metrics request]
  (let [{:keys [active-requests requests responses
                schemes statuses times request-methods]} metrics]
    (inc! active-requests)
    (try
      (let [request-method (:request-method request)
            request-scheme (:scheme request)]
        (mark! requests)
        (mark-in! request-methods request-method)
        (mark-in! schemes request-scheme)
        (let [resp (time! (times request-method (times :other))
                          (handler request))
              ^{:tag "int"} status-code (or (:status resp) 404)]
          (mark! responses)
          (mark-in! statuses (int (/ status-code 100)))
          resp))
      (finally (dec! active-requests)))))

(defn metrics-for
  [metrics-db prefix registry]
  (if-let [prefix-metrics (get @metrics-db prefix)]
    prefix-metrics
    (let [prefix-metrics (ring-metrics registry {:prefix prefix})]
      (swap! metrics-db assoc prefix prefix-metrics)
      prefix-metrics)))  

(defn uri-prefix [request]
  (let [empty-strings? (complement #{""})
        path (-> (:uri request)
                 (string/split #"/"))]
    (filter empty-strings? path)))

(defn instrument
  ([handler]
   (instrument handler default-registry))
  ([handler ^MetricRegistry reg]
   (fn [request]
     (handle-request handler
                     (ring-metrics reg {:prefix []})
                     request))))

(defn instrument-by
  "Instrument a ring handler using the metrics returned by the `metrics-for`
   function. `metrics-by` should be a function which takes an atom, a registry
   and a request and return a collection of metrics objects that pertain to
   some type of request.

   For example, `metrics-by-uri` maintains separate metrics for each endpoint

   This middleware should be added as late as possible (nearest to the outside of
   the \"chain\") for maximum effect.
  "
  ([handler ^MetricRegistry metrics-prefix]
   (instrument-by handler default-registry metrics-prefix))
  ([handler ^MetricRegistry reg metrics-prefix]
   (let [metrics-db (atom {})]
     (fn [request]
       (let [prefix (metrics-prefix request)]
         (handle-request handler
                         (metrics-for metrics-db prefix reg)
                         request))))))
