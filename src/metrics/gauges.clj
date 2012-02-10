(ns metrics.gauges
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Gauge)))


; Create ----------------------------------------------------------------------
(defmacro gauge [title & body]
  `(Metrics/newGauge (metric-name ~title)
                     (proxy [Gauge] []
                       (value [] (do ~@body)))))


; Read ------------------------------------------------------------------------
(defn gauge-fn [title f]
  (Metrics/newGauge (metric-name title)
                    (proxy [Gauge] []
                      (value [] (f)))))


; Read ------------------------------------------------------------------------
(defn value [^Gauge g]
  (.value g))
