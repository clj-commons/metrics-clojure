(ns metrics.utils
  (:require [metrics.core :refer [default-registry]])
  (:import [com.codahale.metrics MetricRegistry Sampling]))


(defn get-percentile
  [^Sampling metric ^double percentile]
  (-> metric
      .getSnapshot
      (.getValue percentile)))

(defn get-percentiles
  [metric percentiles]
  (zipmap percentiles
          (map (partial get-percentile metric)
               percentiles)))

(defn metric-name
  [title]
  (metrics.core/metric-name title))

(defn all-metrics
  ([]
     (all-metrics default-registry))
  ([^MetricRegistry reg]
     (into {} (.getMetrics reg))))

(defn desugared-title
  "Syntactic sugary goodness for defining metrics concisely with macros.

  Returns a vector of: [symbol-to-define metric-name]"
  [mn]
  (cond
   (string? mn) [(symbol mn) mn]
   (symbol? mn) [mn (str mn)]
   :else [(symbol (last mn))
          (map str mn)]))
