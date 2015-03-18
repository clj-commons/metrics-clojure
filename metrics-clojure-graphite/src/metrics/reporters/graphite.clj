(ns metrics.reporters.graphite
  "Graphite reporter interface"
  (:require [metrics.core  :refer [default-registry]]
            [metrics.reporters :as mrep])
  (:import java.util.concurrent.TimeUnit
           java.net.InetSocketAddress
           [com.codahale.metrics Metric MetricRegistry Clock MetricFilter]
           [com.codahale.metrics.graphite Graphite GraphiteReporter GraphiteReporter$Builder]))


(defn ^com.codahale.metrics.graphite.GraphiteReporter reporter
  ([opts]
     (reporter default-registry opts))
  ([^MetricRegistry reg opts]
     (let [g (Graphite. (InetSocketAddress. (or (:host opts)
                                                (:hostname opts)
                                                "localhost")
                                            (get opts :port 2003)))
           b (GraphiteReporter/forRegistry reg)]
       (when-let [^String s (:prefix opts)]
         (.prefixedWith b s))
       (when-let [^Clock c (:clock opts)]
         (.withClock b c))
       (when-let [^TimeUnit ru (:rate-unit opts)]
         (.convertRatesTo b ru))
       (when-let [^TimeUnit du (:duration-unit opts)]
         (.convertDurationsTo b du))
       (when-let [^MetricFilter f (:filter opts)]
         (.filter b f))
       (.build b g))))

(defn start
  "Report all metrics to graphite periodically."
  [^GraphiteReporter r ^long seconds]
  (mrep/start r seconds))

(defn stop
  "Stops reporting."
  [^GraphiteReporter r]
  (mrep/stop r))
