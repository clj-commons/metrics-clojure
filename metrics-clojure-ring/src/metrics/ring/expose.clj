(ns metrics.ring.expose
  (:import (com.codahale.metrics Gauge Timer Counter Histogram Meter))
  (:require [metrics.gauges :as gauges]
            [metrics.meters :as meters]
            [metrics.histograms :as histograms]
            [metrics.counters :as counters]
            [metrics.timers :as timers]
            [ring.util.response :refer [header response]]
            [metrics.core :refer [default-registry]]
            [metrics.utils :refer [all-metrics]]
            [cheshire.core :refer [generate-string]]))


; Define rendering protocol ---------------------------------------------------
(defprotocol RenderableMetric
  (render-to-basic [metric] "Turn a metric into a basic Clojure datastructure."))

(extend-type Gauge
  RenderableMetric
  (render-to-basic [g]
    {:type :gauge
     :value (gauges/value g)}))

(extend-type Timer
  RenderableMetric
  (render-to-basic [t]
    {:type :timer
     :rates (timers/rates t)
     :percentiles (timers/percentiles t)
     :max (timers/largest t)
     :min (timers/smallest t)
     :mean (timers/mean t)
     :standard-deviation (timers/std-dev t)}))

(extend-type Meter
  RenderableMetric
  (render-to-basic [m]
    {:type :meter
     :rates (meters/rates m)}))

(extend-type Histogram
  RenderableMetric
  (render-to-basic [h]
    {:type :histogram
     :max (histograms/largest h)
     :min (histograms/smallest h)
     :mean (histograms/mean h)
     :standard-deviation (histograms/std-dev h)
     :percentiles (histograms/percentiles h)}))

(extend-type Counter
  RenderableMetric
  (render-to-basic [c]
    {:type :counter
     :value (counters/value c)}))

;;
;; Implementation
;;

(defn- ensure-leading-slash [s]
  (if (not= \/ (first s))
    (str \/ s)
    s))

(defn- strip-trailing-slash [s]
  (if (= \/ (last s))
    (apply str (butlast s))
    s))

(defn- sanitize-uri [uri]
  (str (-> uri
         ensure-leading-slash
         strip-trailing-slash)
       \/))

(defn- render-metric [[metric-name metric]]
  [metric-name (render-to-basic metric)])


;;
;; API
;;

(defn render-metrics
  ([]
   (render-metrics default-registry))
  ([registry]
   (into {} (map render-metric (all-metrics registry)))))

(defn serve-metrics
  ([request]
     (serve-metrics request default-registry))
  ([request registry]
     (serve-metrics request registry false))
  ([request registry {:keys [pretty-print?] :as opts}]
     (let [metrics-map (render-metrics registry)
           json        (generate-string metrics-map {:pretty pretty-print?})]
       (-> (response json)
         (header "Content-Type" "application/json")))))

(defn expose-metrics-as-json
  ([handler]
     (expose-metrics-as-json handler "/metrics"))
  ([handler uri]
    (expose-metrics-as-json handler uri default-registry))
  ([handler uri registry]
    (expose-metrics-as-json handler uri registry {:pretty-print? false}))
  ([handler uri registry opts]
    (fn [request]
      (let [^String request-uri (:uri request)]
        (if (or (.startsWith request-uri (sanitize-uri uri))
                (= request-uri uri))
          (serve-metrics request registry opts)
          (handler request))))))
