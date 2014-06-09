(ns metrics.ring.expose
  (:import (com.codahale.metrics Gauge Timer Counter Histogram Meter))
  (:require [metrics.gauges :as gauges]
            [metrics.meters :as meters]
            [metrics.histograms :as histograms]
            [metrics.counters :as counters]
            [metrics.timers :as timers]
            [ring.util.response :refer [header response]]
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


; Utils -----------------------------------------------------------------------
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


; Exposing --------------------------------------------------------------------
(defn- metrics-json [request]
  (let [metrics-map (into {} (map render-metric (all-metrics)))
        json (generate-string metrics-map)]
    (-> (response json)
      (header "Content-Type" "application/json"))))

(defn expose-metrics-as-json
  ([handler]
     (expose-metrics-as-json handler "/metrics"))
  ([handler uri]
   (fn [request]
     (let [request-uri (:uri request)]
       (if (or (.startsWith request-uri (sanitize-uri uri))
               (= request-uri uri))
         (metrics-json request)
         (handler request))))))
