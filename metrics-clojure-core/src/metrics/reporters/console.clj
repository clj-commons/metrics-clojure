(ns metrics.reporters.console
  "Console reporting"
  (:require [metrics.core  :refer [default-registry]])
  (:import java.util.concurrent.TimeUnit
           [com.codahale.metrics Metric MetricRegistry ConsoleReporter Clock MetricFilter]
           java.io.PrintStream
           java.util.Locale))

(defn ^ConsoleReporter reporter
  ([opts]
   (reporter default-registry opts))
  ([^MetricRegistry reg opts]
   (let [b (ConsoleReporter/forRegistry reg)]
     (when-let [^PrintStream s (:stream opts)]
       (.outputTo b s))
     (when-let [^Locale l (:locale opts)]
       (.formattedFor b l))
     (when-let [^Clock c (:clock opts)]
       (.withClock b c))
     (when-let [^TimeUnit ru (:rate-unit opts)]
       (.convertRatesTo b ru))
     (when-let [^TimeUnit du (:duration-unit opts)]
       (.convertDurationsTo b du))
     (when-let [^MetricFilter f (:filter opts)]
       (.filter b f))
     (.build b))))

(defn start
  "Report all metrics to standard out periodically"
  [^ConsoleReporter r ^long seconds]
  (.start r seconds TimeUnit/SECONDS))
