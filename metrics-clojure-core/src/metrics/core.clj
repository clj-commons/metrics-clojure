(ns metrics.core
  (:use [metrics.utils :as utils])
  (:import com.yammer.metrics.Metrics
           com.yammer.metrics.core.MetricName
           com.yammer.metrics.core.MetricsRegistry
           java.util.concurrent.TimeUnit
           com.yammer.metrics.reporting.ConsoleReporter))


(defn remove-metric
  "Remove the metric with the given title."
  [title]
  (.removeMetric ^MetricsRegistry (Metrics/defaultRegistry) (utils/metric-name title)))

(defn remove-all-metrics
  "Remove all metrics."
  []
  (let [^MetricsRegistry registry (Metrics/defaultRegistry)]
    (doseq [^MetricName metric-name (keys (.allMetrics registry))]
      (.removeMetric registry metric-name))))

(defn report-to-console
  "Report all metrics to standard out every few seconds."
  [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))

