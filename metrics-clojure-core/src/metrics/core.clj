(ns metrics.core
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.reporting ConsoleReporter GangliaReporter))
  (:import (java.util.concurrent TimeUnit)))


(defn remove-metric
  "Remove the metric with the given title."
  [title]
  (.removeMetric (Metrics/defaultRegistry) (metric-name title)))


(defn report-to-console
  "Report all metrics to standard out every few seconds."
  [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))


(defn report-to-ganglia
  "Report all metrics to ganglia every few minutes."
  [minutes host port]
  (GangliaReporter/enable minutes TimeUnit/MINUTES host port))