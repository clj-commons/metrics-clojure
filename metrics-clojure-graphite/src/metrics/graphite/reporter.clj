(ns metrics.graphite.reporter
  (:require [metrics.utils :as utils])
  (:import java.util.concurrent.TimeUnit
           com.yammer.metrics.Metrics
           com.yammer.metrics.reporting.GraphiteReporter))

(defn report-to-graphite
  "Report all metrics to graphite every few minutes."
  ([^long minutes ^String host ^long port]
   (GraphiteReporter/enable (Metrics/defaultRegistry)
                            minutes
                            TimeUnit/MINUTES
                            host
                            port))
  ([^long minutes ^String host ^long port ^String prefix]
   (GraphiteReporter/enable (Metrics/defaultRegistry)
                            minutes
                            TimeUnit/MINUTES
                            host
                            port
                            prefix)))
