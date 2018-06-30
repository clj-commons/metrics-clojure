(ns metrics.ring.expose
  (:import (com.codahale.metrics Gauge Timer Counter Histogram Meter)
           (java.util.concurrent TimeUnit))
  (:require [clojure.string :as string]
            [metrics.gauges :as gauges]
            [metrics.meters :as meters]
            [metrics.histograms :as histograms]
            [metrics.counters :as counters]
            [metrics.timers :as timers]
            [ring.util.response :refer [header response]]
            [metrics.core :refer [default-registry]]
            [metrics.utils :refer [all-metrics]]
            [metrics.ring.unit :as unit]
            [cheshire.core :refer [generate-string]]))


; Define rendering protocol ---------------------------------------------------
(defprotocol RenderableMetric
  (render-to-basic [metric unit-opts] "Turn a metric into a basic Clojure datastructure."))

(extend-type Gauge
  RenderableMetric
  (render-to-basic [g _]
    {:type :gauge
     :value (gauges/value g)}))

(extend-type Timer
  RenderableMetric
  (render-to-basic [t unit-opts]
    {:type               :timer
     :duration-unit      (:duration-unit-label unit-opts)
     :rate-unit          (:rate-unit-label unit-opts)
     :rates              (unit/convert-rates (timers/rates t) unit-opts)
     :percentiles        (unit/convert-percentiles (timers/percentiles t) unit-opts)
     :max                (unit/convert-duration (timers/largest t) unit-opts)
     :min                (unit/convert-duration (timers/smallest t) unit-opts)
     :mean               (unit/convert-duration (timers/mean t) unit-opts)
     :standard-deviation (unit/convert-duration (timers/std-dev t) unit-opts)}))

(extend-type Meter
  RenderableMetric
  (render-to-basic [m unit-opts]
    {:type  :meter
     :rate-unit (:rate-unit-label unit-opts)
     :rates (unit/convert-rates (meters/rates m) unit-opts)}))

(extend-type Histogram
  RenderableMetric
  (render-to-basic [h _]
    {:type :histogram
     :max (histograms/largest h)
     :min (histograms/smallest h)
     :mean (histograms/mean h)
     :standard-deviation (histograms/std-dev h)
     :percentiles (histograms/percentiles h)}))

(extend-type Counter
  RenderableMetric
  (render-to-basic [c _]
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

(defn- render-metric [[metric-name metric] unit-opts]
  [metric-name (render-to-basic metric unit-opts)])

(defn- placeholder? [c]
  (contains? #{"" "*"} c))

;;
;; API
;;

(defn filter-matches? [kn filter]
  (every? true?
          (map (fn [k f]
                 (or (placeholder? f)
                     (= k f)))
               (string/split kn #"\.")
               (string/split filter #"\."))))

(defn filter-metrics [^String filter metrics]
  (if (string/blank? filter)
    metrics
    (into {}
          (for [[k v] metrics :when (filter-matches? k filter)]
            [k v]))))

(defn render-metrics
  ([]
   (render-metrics default-registry nil nil))
  ([filter]
    (render-metrics default-registry filter nil))
  ([registry filter unit-context]
   (into {} (map #(render-metric % unit-context) (->> registry
                                                      (all-metrics)
                                                      (filter-metrics filter))))))
(defn- serve-metrics*
  ([request registry {:keys [pretty-print? filter rate-unit duration-unit]}]
     (let [metrics-map (render-metrics registry filter (unit/build-options rate-unit duration-unit))
           json        (generate-string metrics-map {:pretty pretty-print?})]
       (-> (response json)
         (header "Content-Type" "application/json")))))

(defn serve-metrics
  ([request]
    (let [^String filter (get-in request [:params :filter])]
     (serve-metrics* request default-registry {:pretty-print? false
                                               :filter filter
                                               :rate-unit TimeUnit/SECONDS
                                               :duration-unit TimeUnit/NANOSECONDS})))
  ([request respond raise]
    (let [^String filter (get-in request [:params :filter])]
          (try            
            (respond 
              (serve-metrics* request default-registry {:pretty-print? false
                                                      :filter filter
                                                      :rate-unit TimeUnit/SECONDS
                                                      :duration-unit TimeUnit/NANOSECONDS}))
            (catch Exception e (raise e))))))


(defn expose-metrics-as-json
  ([handler]
     (expose-metrics-as-json handler "/metrics"))
  ([handler uri]
    (expose-metrics-as-json handler uri default-registry))
  ([handler uri registry]
    (expose-metrics-as-json handler uri registry {:pretty-print? false}))
  ([handler uri registry opts]
    (fn 
      ([request]
        (let [^String request-uri (:uri request)
              ^String filter (get-in request [:params :filter])]
          (if (or (.startsWith request-uri (sanitize-uri uri))
                  (= request-uri uri))
            (serve-metrics* request registry (merge {:filter filter
                                                    :rate-unit TimeUnit/SECONDS
                                                    :duration-unit TimeUnit/NANOSECONDS} opts))
            (handler request))))
      ([request respond raise]
        (let [^String request-uri (:uri request)
              ^String filter (get-in request [:params :filter])]
          (if (or (.startsWith request-uri (sanitize-uri uri))
                  (= request-uri uri))
            (serve-metrics* request registry (merge {:filter filter
                                                   :rate-unit TimeUnit/SECONDS
                                                   :duration-unit TimeUnit/NANOSECONDS} opts))
            (handler request respond raise)))))))
