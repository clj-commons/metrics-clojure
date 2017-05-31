(ns metrics.ring.instrument
  (:require [metrics.core :refer [default-registry]]
            [metrics.counters :refer (counter inc! dec!)]
            [metrics.meters :refer (meter mark!)]
            [metrics.timers :refer (timer time!)])
  (:import [com.codahale.metrics MetricRegistry]))

(def default-options
  {:prefix []})

(defn- mark-in! [metric-map k]
  (when-let [metric (metric-map k (metric-map :other))]
    (mark! metric)))

(defn instrument
  "Instrument a ring handler.

  This middleware should be added as late as possible (nearest to the outside of
  the \"chain\") for maximum effect.
  "
  ([handler]
   (instrument handler default-registry))
  ([handler ^MetricRegistry reg]
   (instrument handler reg default-options))
  ([handler ^MetricRegistry reg
    {:keys [prefix] :as options}]
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
     (fn [request]
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
         (finally (dec! active-requests)))))))
