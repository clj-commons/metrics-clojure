(ns metrics.reporters.jmx
  "JMX reporting"
  (:require [metrics.core  :refer [default-registry]])
  (:import [com.codahale.metrics MetricRegistry MetricFilter]
           com.codahale.metrics.jmx.JmxReporter
           java.util.concurrent.TimeUnit))

(defn ^JmxReporter reporter
  ([opts]
   (reporter default-registry opts))
  ([^MetricRegistry reg opts]
   (let [b (JmxReporter/forRegistry reg)]
     (when-let [^String d (:domain opts)]
       (.inDomain b d))
     (when-let [^TimeUnit ru (:rate-unit opts)]
       (.convertRatesTo b ru))
     (when-let [^TimeUnit du (:duration-unit opts)]
       (.convertDurationsTo b du))
     (when-let [^MetricFilter f (:filter opts)]
       (.filter b f))
     (.build b))))

(defn start
  "Report all metrics via JMX"
  [^JmxReporter r]
  (.start ^JmxReporter r))

(defn stop
  "Stop reporting metrics via JMX"
  [^JmxReporter r]
  (.stop ^JmxReporter r))
