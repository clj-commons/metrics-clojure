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
  ([^MetricRegistry reg {:keys [host hostname port prefix clock rate-unit duration-unit filter] :as opts
                         :or {port 2003}}]
     (let [g (Graphite. (InetSocketAddress. (or host
                                                hostname
                                                "localhost")
                                            port))
           b (GraphiteReporter/forRegistry reg)]
       (when-let [^String s prefix]
         (.prefixedWith b s))
       (when-let [^Clock c clock]
         (.withClock b c))
       (when-let [^TimeUnit ru rate-unit]
         (.convertRatesTo b ru))
       (when-let [^TimeUnit du duration-unit]
         (.convertDurationsTo b du))
       (when-let [^MetricFilter f filter]
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
