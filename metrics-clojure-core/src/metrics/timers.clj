(ns metrics.timers
  (:use [metrics.utils :only (metric-name get-percentiles)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Timer MetricName))
  (:import (java.util.concurrent TimeUnit)))


; Create ----------------------------------------------------------------------
(defn timer [title]
  (Metrics/newTimer ^MetricName (metric-name title)
                    TimeUnit/MILLISECONDS
                    TimeUnit/SECONDS))

(defmacro deftimer [title]
  `(def ~title (timer ~(str title))))


; Read ------------------------------------------------------------------------
(defn rates [^Timer m]
  {1 (.oneMinuteRate m)
   5 (.fiveMinuteRate m)
   15 (.fifteenMinuteRate m)})

(defn rate-one [^Timer m]
  (.oneMinuteRate m))

(defn rate-five [^Timer m]
  (.fiveMinuteRate m))

(defn rate-fifteen [^Timer m]
  (.fifteenMinuteRate m))

(defn rate-mean [^Timer m]
  (.meanRate m))


(defn mean [^Timer t]
  (.mean t))

(defn std-dev [^Timer t]
  (.stdDev t))

(defn percentiles
  ([^Timer t]
   (percentiles t [0.75 0.95 0.99 0.999 1.0]))
  ([^Timer t ps]
   (get-percentiles t ps)))


(defn number-recorded [^Timer t]
  (.count t))

(defn largest [^Timer t]
  (.max t))

(defn smallest [^Timer t]
  (.min t))

(defn sample [^Timer t]
  (.getValues (.getSnapshot t)))


; Write -----------------------------------------------------------------------
(defmacro time! [^Timer t & body]
  `(.time ~(vary-meta t assoc :tag `Timer)
         (proxy [Callable] []
           (call [] (do ~@body)))))

(defn time-fn! [^Timer t f]
  (.time t
         (proxy [Callable] []
           (call [] (f)))))

(defn clear! [^Timer t]
  (.clear t))

