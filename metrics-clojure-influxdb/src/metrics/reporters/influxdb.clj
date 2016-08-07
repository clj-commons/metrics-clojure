(ns metrics.reporters.influxdb
  "InfluxDB reporter interface"
  (:require [metrics.core :refer [default-registry]]
            [metrics.reporters :as mrep])
  (:import java.util.concurrent.TimeUnit
           [com.codahale.metrics Metric MetricRegistry]
           [com.google.common.collect ImmutableMap ImmutableSet]
           [com.izettle.metrics.influxdb InfluxDbReporter]
           [com.izettle.metrics.dw InfluxDbReporterFactory]))

(defn ^InfluxDbReporter reporter
  ([opts]
   (reporter default-registry opts))
  ([^MetricRegistry reg {:keys [host hostname port prefix tags fields auth connect-timeout read-timeout
                                group-guages duration-unit measurement-mappings db-name excludes] :as opts
                         :or {port 8086
                              db-name "metrics"}}]
   (let [b (InfluxDbReporterFactory.)]
     (.setHost b (or host hostname "localhost"))
     (.setPort b port)
     (.setDatabase b db-name)
     (when-let [^String p prefix]
       (.setPrefix b p))
     (when-let [^java.util.Map t tags]
       (.setTags b t))
     (when-let [^java.util.Map f fields]
       (.setFields b (ImmutableMap/copyOf f)))
     (when-let [^String a auth]
       (.setAuth b a))
     (when connect-timeout
       (.setConnectTimeout b connect-timeout))
     (when read-timeout
       (.setConnectTimeout b read-timeout))
     (when group-guages
       (.setGroupGuages b group-guages))
     (when-let [^TimeUnit du duration-unit]
       (.setPrecision b du))
     (when-let [^java.util.Map mm measurement-mappings]
       (.setMeasurementMappings b (ImmutableMap/copyOf mm)))
     (when-let [^java.util.Set e excludes]
       (.setExcludes b (ImmutableSet/copyOf e)))
     (.build b reg))))

(defn start
  "Report all metrics to influxdb periodically."
  [^InfluxDbReporter r ^long seconds]
  (mrep/start r seconds))

(defn stop
  "Stops reporting."
  [^InfluxDbReporter r]
  (mrep/stop r))
