(ns metrics.histograms
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics)))


(defn histogram
  ([title] (histogram title false))
  ([title biased]
   (Metrics/newHistogram (metric-name title) biased)))

(defn update!
  ([h] (update! h 1))
  ([h n]
   (.update h n)
   h))
