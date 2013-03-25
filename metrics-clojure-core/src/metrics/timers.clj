(ns metrics.timers
  (:use [metrics.utils :only (metric-name get-percentiles desugared-title)])
  (:import com.yammer.metrics.Metrics
           [com.yammer.metrics.core Timer MetricName]
           java.util.concurrent.TimeUnit))


; Create ----------------------------------------------------------------------
(defn timer [title]
  (Metrics/newTimer ^MetricName (metric-name title)
                    TimeUnit/MILLISECONDS
                    TimeUnit/SECONDS))


(defmacro deftimer
  "Define a new Timer metric with the given title.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a timer titled \"default.default.foo\" into var foo
    (deftimer foo)
    (deftimer \"foo\")

    ; Define a timer titled \"a.b.c\" into var c
    (deftimer [a b c])
    (deftimer [\"a\" \"b\" \"c\"])
    (deftimer [a \"b\" c])
  "
  [title]
  (let [[s title] (desugared-title title)]
    `(def ~s (timer ~title))))


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

