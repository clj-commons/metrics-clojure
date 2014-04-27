(ns metrics.core
  (:require [metrics.utils :refer [metric-name]])
  (:import com.yammer.metrics.Metrics
           java.util.concurrent.TimeUnit
           com.yammer.metrics.reporting.ConsoleReporter))


(defn remove-metric
  "Remove the metric with the given title."
  [title]
  (.removeMetric (Metrics/defaultRegistry) (metric-name title)))


(defn report-to-console
  "Report all metrics to standard out every few seconds."
  [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))


