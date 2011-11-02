(ns metrics.histograms
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core HistogramMetric)))


; Create ----------------------------------------------------------------------
(defn histogram
  ([title] (histogram title false))
  ([title biased]
   (Metrics/newHistogram (metric-name title) biased)))


; Read ------------------------------------------------------------------------
(defn mean [^HistogramMetric h]
  (.mean h))

(defn std-dev [^HistogramMetric h]
  (.stdDev h))

(defn percentiles
  ([^HistogramMetric h]
   (percentiles h [0.75 0.95 0.99 0.999 1.0]))
  ([^HistogramMetric h ps]
   (zipmap ps
           (.percentiles h (double-array ps)))))


(defn number-recorded [^HistogramMetric h]
  (.count h))

(defn largest [^HistogramMetric h]
  (.max h))

(defn smallest [^HistogramMetric h]
  (.min h))

(defn sample [^HistogramMetric h]
  (.values h))


; Write -----------------------------------------------------------------------
(defn update!
  ([^HistogramMetric h] (update! h 1))
  ([^HistogramMetric h n]
   (.update h (long n))
   h))

(defn clear! [^HistogramMetric h]
  (.clear h)
  h)

