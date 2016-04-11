(ns metrics.reporters.ganglia
  "Ganglia reporter interface"
  (:require [metrics.core  :refer [default-registry]]
            [metrics.reporters :as mrep])
  (:import java.util.concurrent.TimeUnit
           [com.codahale.metrics MetricRegistry MetricFilter]
           [com.codahale.metrics.ganglia GangliaReporter GangliaReporter$Builder]))

(defn ^com.codahale.metrics.ganglia.GangliaReporter reporter
  ([ganglia opts]
     (reporter default-registry ganglia opts))
  ([^MetricRegistry reg ganglia {:keys [rate-unit duration-unit filter] :as opts}]
     (let [b (GangliaReporter/forRegistry reg)]
       (when-let [^TimeUnit ru rate-unit]
         (.convertRatesTo b ru))
       (when-let [^TimeUnit du duration-unit]
         (.convertDurationsTo b du))
       (when-let [^MetricFilter f filter]
         (.filter b f))
       (.build b ganglia))))

(defn start
  "Report all metrics to ganglia periodically."
  [^GangliaReporter r ^long minutes]
  (mrep/start r minutes))

(defn stop
  "Stops reporting."
  [^GangliaReporter r]
  (mrep/stop r))
