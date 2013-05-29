(ns metrics.graphite-reporter
  (:import java.util.concurrent.TimeUnit
           com.yammer.metrics.reporting.GraphiteReporter))

(defn report-to-graphite
  "Report all metrics to ganglia every few minutes."
  ([minutes host port]
   (report-to-graphite minutes host port nil))
  ([^long minutes ^String host ^long port ^String prefix]
   (GraphiteReporter/enable minutes TimeUnit/MINUTES host port prefix)))
