(ns metrics.counters
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core CounterMetric)))


; Create ----------------------------------------------------------------------
(defn counter [title]
  (Metrics/newCounter (metric-name title)))


; Read ------------------------------------------------------------------------
(defn value [^CounterMetric c]
  (.count c))


; Write -----------------------------------------------------------------------
(defn inc!
  ([^CounterMetric c] (inc! c 1))
  ([^CounterMetric c n]
   (.inc c n)
   c))

(defn dec!
  ([^CounterMetric c] (dec! c 1))
  ([^CounterMetric c n]
   (.dec c n)
   n))

(defn clear! [^CounterMetric c]
  (.clear c))

