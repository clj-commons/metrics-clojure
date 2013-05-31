(ns metrics.ganglia.reporter
  (:require [metrics.utils :as utils])
  (:import java.util.concurrent.TimeUnit
           com.yammer.metrics.Metrics
           com.yammer.metrics.reporting.GangliaReporter))

(defn report-to-ganglia
  "Report all metrics to ganglia every few minutes."
  ([^long minutes ^String host ^long port]
   (GangliaReporter/enable (Metrics/defaultRegistry)
                           minutes
                           TimeUnit/MINUTES
                           host
                           port))
  ([^long minutes ^String host ^long port ^String prefix]
   (GangliaReporter/enable (Metrics/defaultRegistry)
                           minutes
                           TimeUnit/MINUTES
                           host
                           port
                           prefix)))
