(ns metrics.timers
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (com.yammer.metrics.core TimerMetric))
  (import (java.util.concurrent TimeUnit)))


; Create ----------------------------------------------------------------------
(defn timer [title]
  (Metrics/newTimer (metric-name title)
                    TimeUnit/MILLISECONDS
                    TimeUnit/SECONDS))


; Read ------------------------------------------------------------------------
(defn rates [^TimerMetric m]
  {1 (.oneMinuteRate m)
   5 (.fiveMinuteRate m)
   15 (.fifteenMinuteRate m)})

(defn rate-one [^TimerMetric m]
  (.oneMinuteRate m))

(defn rate-five [^TimerMetric m]
  (.fiveMinuteRate m))

(defn rate-fifteen [^TimerMetric m]
  (.fifteenMinuteRate m))

(defn rate-mean [^TimerMetric m]
  (.meanRate m))


(defn mean [^TimerMetric t]
  (.mean t))

(defn std-dev [^TimerMetric t]
  (.stdDev t))

(defn percentiles
  ([^TimerMetric t]
   (percentiles t [0.75 0.90 0.95 1.0]))
  ([^TimerMetric t ps]
   (zipmap ps
           (.percentiles t (double-array ps)))))


(defn number-recorded [^TimerMetric t]
  (.count t))

(defn largest [^TimerMetric t]
  (.max t))

(defn smallest [^TimerMetric t]
  (.min t))

(defn sample [^TimerMetric t]
  (.values t))


; Write -----------------------------------------------------------------------
(defmacro time! [t & body]
  `(.time ~t
         (proxy [Callable] []
           (call [] (do ~@body)))))

(defn time-fn! [t f]
  (.time t
         (proxy [Callable] []
           (call [] (f)))))

(defn clear! [^TimerMetric t]
  (.clear t))
