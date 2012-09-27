(ns metrics.core
  (:use [metrics.utils :only (metric-name)])
  (:import [[com.yammer.metrics.reporting GraphiteReporter]
            [com.yammer.metrics.reporting ConsoleReporter]
            [java.util.concurrent TimeUnit]]))


(defn remove-metric
  "Remove the metric with the given title."
  [title]
  (.removeMetric (Metrics/defaultRegistry) (metric-name title)))

(defn report-to-console
  "Report all metrics to standard out every few seconds."
  [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))

(defn report-to-graphite
  "Report all metrics to graphite at specified interval in seconds."
  [seconds host port prefix]
  (GraphiteReporter/enable seconds TimeUnit/SECONDS
                           host port prefix))