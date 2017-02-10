(ns metrics.reporters.graphite
  "Graphite reporter interface"
  (:require [metrics.core  :refer [default-registry]]
            [metrics.reporters :as mrep])
  (:import java.util.concurrent.TimeUnit
           java.net.InetSocketAddress
           [com.codahale.metrics Metric MetricRegistry Clock MetricFilter]
           [com.codahale.metrics.graphite Graphite GraphiteReporter GraphiteReporter$Builder]))

(defn- ^InetSocketAddress inet-socket-address
  [^String hostname ^Long port]
  (InetSocketAddress. hostname (int port)))

(defn- ^GraphiteReporter$Builder builder-for-registry
  [^MetricRegistry reg]
  (GraphiteReporter/forRegistry reg))

(defn- ^Graphite graphite-sender
  [hostname port]
  (Graphite. (inet-socket-address hostname (int port))))

(defn ^com.codahale.metrics.graphite.GraphiteReporter reporter
  ([opts]
     (reporter default-registry opts))
  ([^MetricRegistry reg {:keys [host hostname port prefix clock rate-unit duration-unit filter] :as opts
                         :or {port 2003}}]
     (let [g (graphite-sender (or host hostname "localhost") port)
           b (builder-for-registry reg)]
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
