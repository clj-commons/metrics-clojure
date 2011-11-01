(ns metrics.gauges
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core GaugeMetric)))


(defn gauge [title & body]
  `(Metrics/newGauge (metric-name title)
                     (proxy [GaugeMetric] []
                       (value [] (do ~@body)))))

(defn gauge-fn [title f]
  (Metrics/newGauge (metric-name title)
                    (proxy [GaugeMetric] []
                      (value [] (f)))))

