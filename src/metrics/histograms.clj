(ns metrics.histograms
  (use [metrics.utils :only (metric-name get-percentiles)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core Histogram MetricName)))


; Create ----------------------------------------------------------------------
(defn histogram
  ([title] (histogram title true))
  ([title biased]
   (Metrics/newHistogram
     ^MetricName (metric-name title)
     (boolean biased))))


; Read ------------------------------------------------------------------------
(defn mean [^Histogram h]
  (.mean h))

(defn std-dev [^Histogram h]
  (.stdDev h))

(defn percentiles
  ([^Histogram h]
   (percentiles h [0.75 0.95 0.99 0.999 1.0]))
  ([^Histogram h ps]
   (get-percentiles h ps)))


(defn number-recorded [^Histogram h]
  (.count h))

(defn largest [^Histogram h]
  (.max h))

(defn smallest [^Histogram h]
  (.min h))

(defn sample [^Histogram h]
  (.getValues (.getSnapshot h)))


; Write -----------------------------------------------------------------------
(defn update! [^Histogram h n]
  (.update h (long n))
  h)

(defn clear! [^Histogram h]
  (.clear h)
  h)

