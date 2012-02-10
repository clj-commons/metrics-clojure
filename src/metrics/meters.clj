(ns metrics.meters
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core Meter))
  (import (java.util.concurrent TimeUnit)))


; Create ----------------------------------------------------------------------
(defn meter [title event-type]
  (Metrics/newMeter (metric-name title)
                    event-type
                    TimeUnit/SECONDS))


; Read ------------------------------------------------------------------------
(defn rates [^Meter m]
  {1 (.oneMinuteRate m)
   5 (.fiveMinuteRate m)
   15 (.fifteenMinuteRate m)})

(defn rate-one [^Meter m]
  (.oneMinuteRate m))

(defn rate-five [^Meter m]
  (.fiveMinuteRate m))

(defn rate-fifteen [^Meter m]
  (.fifteenMinuteRate m))

(defn rate-mean [^Meter m]
  (.meanRate m))


; Write -----------------------------------------------------------------------
(defn mark!
  ([^Meter m]
   (mark! m 1))
  ([^Meter m n]
   (.mark m (long n))
   m))
