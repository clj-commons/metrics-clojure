(ns metrics.core
  (:use [metrics.utils :only (metric-name)])
  (:import com.yammer.metrics.Metrics
           java.util.concurrent.TimeUnit
           [com.yammer.metrics.reporting ConsoleReporter JmxReporter]))


(defn remove-metric
  "Remove the metric with the given title."
  [title]
  (.removeMetric (Metrics/defaultRegistry) (metric-name title)))


(defn report-to-console
  "Report all metrics to standard out every few seconds."
  [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))


(defn report-to-jmx
  "Enables reporting your metrics as JMX MBeans"
  []
  (JmxReporter/startDefault (Metrics/defaultRegistry)))
