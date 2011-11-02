(ns metrics.gauges
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core GaugeMetric)))


; Create ----------------------------------------------------------------------
(defmacro gauge [title & body]
  `(Metrics/newGauge (metric-name ~title)
                     (proxy [GaugeMetric] []
                       (value [] (do ~@body)))))

; Read ------------------------------------------------------------------------
(defn gauge-fn [title f]
  (Metrics/newGauge (metric-name title)
                    (proxy [GaugeMetric] []
                      (value [] (f)))))


; Read ------------------------------------------------------------------------
(defn value [^GaugeMetric g]
  (.value g))
