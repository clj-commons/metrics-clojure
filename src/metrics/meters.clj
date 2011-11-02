(ns metrics.meters
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core MeterMetric))
  (import (java.util.concurrent TimeUnit)))


; Create ----------------------------------------------------------------------
(defn meter [title event-type]
  (Metrics/newMeter (metric-name title)
                    event-type
                    TimeUnit/SECONDS))


; Read ------------------------------------------------------------------------
(defn rates [^MeterMetric m]
  {1 (.oneMinuteRate m)
   5 (.fiveMinuteRate m)
   15 (.fifteenMinuteRate m)})

(defn rate-one [^MeterMetric m]
  (.oneMinuteRate m))

(defn rate-five [^MeterMetric m]
  (.fiveMinuteRate m))

(defn rate-fifteen [^MeterMetric m]
  (.fifteenMinuteRate m))

(defn rate-mean [^MeterMetric m]
  (.meanRate m))


; Write -----------------------------------------------------------------------
(defn mark!
  ([^MeterMetric m]
   (mark! m 1))
  ([^MeterMetric m n]
   (.mark m (long n))
   m))
