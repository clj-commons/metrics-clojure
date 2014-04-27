(ns metrics.reporters.console
  "Console reporting"
  (:import java.util.concurrent.TimeUnit
           [com.codahale.metrics Metrics MetricsRegistry ConsoleReporter ConsoleReporter$Builder Clock MetricsFilter]
           java.io.PrintStream
           java.util.Locale))

(defn ^ConsoleReporter reporter
  ([opts]
     (reporter (Metrics/defaultRegistry)
               opts))
  ([^MetricsRegistry reg opts]
     (let [b (ConsoleReporter$Builder. reg)]
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
       (when-let [^MetricsFilter f (:filter opts)]
         (.filter b f))
       (.build b))))

(defn start
  "Report all metrics to standard out periodically"
  [^ConsoleReporter r ^long seconds]
  (.start r seconds TimeUnit/SECONDS))
