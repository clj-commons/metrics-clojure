(ns metrics.ganglia-reporter
  (:import java.util.concurrent.TimeUnit
           com.yammer.metrics.reporting.GangliaReporter))

(defn report-to-ganglia
  "Report all metrics to ganglia every few minutes."
  ([minutes host port]
   (report-to-ganglia minutes host port nil))
  ([^long minutes ^String host ^long port ^String prefix]
   (GangliaReporter/enable minutes TimeUnit/MINUTES host port prefix)))
