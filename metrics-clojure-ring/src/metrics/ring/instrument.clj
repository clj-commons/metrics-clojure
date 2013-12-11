(ns metrics.ring.instrument
  (:use [metrics.counters :only (counter inc! dec!)]
        [metrics.meters :only (meter mark!)]
        [metrics.timers :only (timer time!)]))


(defn- mark-in! [metric-map k]
  (when-let [metric (metric-map k (metric-map :other))]
    (mark! metric)))

(defn instrument
  "Instrument a ring handler.

  This middleware should be added as late as possible (nearest to the outside of
  the \"chain\") for maximum effect.
  "
  ([handler]
   (let [active-requests (counter ["ring" "requests" "active"])
         requests (meter ["ring" "requests" "rate"] "requests")
         responses (meter ["ring" "responses" "rate"] "responses")
         statuses {2 (meter ["ring" "responses" "rate.2xx"] "responses")
                   3 (meter ["ring" "responses" "rate.3xx"] "responses")
                   4 (meter ["ring" "responses" "rate.4xx"] "responses")
                   5 (meter ["ring" "responses" "rate.5xx"] "responses")}
         times {:get     (timer ["ring" "handling-time" "GET"])
                :put     (timer ["ring" "handling-time" "PUT"])
                :post    (timer ["ring" "handling-time" "POST"])
                :head    (timer ["ring" "handling-time" "HEAD"])
                :delete  (timer ["ring" "handling-time" "DELETE"])
                :options (timer ["ring" "handling-time" "OPTIONS"])
                :trace   (timer ["ring" "handling-time" "TRACE"])
                :connect (timer ["ring" "handling-time" "CONNECT"])
                :other   (timer ["ring" "handling-time" "OTHER"])}
         request-methods {:get     (meter ["ring" "requests" "rate.GET"] "requests")
                          :put     (meter ["ring" "requests" "rate.PUT"] "requests")
                          :post    (meter ["ring" "requests" "rate.POST"] "requests")
                          :head    (meter ["ring" "requests" "rate.HEAD"] "requests")
                          :delete  (meter ["ring" "requests" "rate.DELETE"] "requests")
                          :options (meter ["ring" "requests" "rate.OPTIONS"] "requests")
                          :trace   (meter ["ring" "requests" "rate.TRACE"] "requests")
                          :connect (meter ["ring" "requests" "rate.CONNECT"] "requests")
                          :other   (meter ["ring" "requests" "rate.OTHER"] "requests")}]
     (fn [request]
       (inc! active-requests)
       (try
         (let [request-method (:request-method request)]
           (mark! requests)
           (mark-in! request-methods request-method)
           (let [resp (time! (times request-method (times :other))
                             (handler request))
                 status-code (or (:status resp) 404)]
             (mark! responses)
             (mark-in! statuses (int (/ status-code 100)))
             resp))
         (finally (dec! active-requests)))))))
