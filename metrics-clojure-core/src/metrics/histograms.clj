(ns metrics.histograms
  (:use [metrics.utils :only (metric-name get-percentiles desugared-title)])
  (:import com.yammer.metrics.Metrics
           [com.yammer.metrics.core Histogram MetricName]))


; Create ----------------------------------------------------------------------
(defn histogram
  "Create and return a Histogram metric with the given title.

  By default a biased Histogram is created.  This is probably what you want, but
  if you know what you're doing you can pass false to create a uniform one
  instead."
  ([title] (histogram title true))
  ([title biased]
   (Metrics/newHistogram
     ^MetricName (metric-name title)
     (boolean biased))))


(defmacro defhistogram
  "Define a Histogram metric with the given title.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a histogram titled \"default.default.foo\" into var foo
    (defhistogram foo)
    (defhistogram \"foo\")

    ; Define a histogram titled \"a.b.c\" into var c
    (defhistogram [a b c])
    (defhistogram [\"a\" \"b\" \"c\"])
    (defhistogram [a \"b\" c])
  "
  [title & args]
  (let [[s title] (desugared-title title)]
    `(def ~s (histogram ~title ~@args))))


; Read ------------------------------------------------------------------------
(defn mean
  "Return the mean value of the given Histogram."
  [^Histogram h]
  (.mean h))

(defn std-dev
  "Return the standard deviation of the given Histogram."
  [^Histogram h]
  (.stdDev h))

(defn percentiles
  "Return a mapping of percentiles to their values for the given Histogram.

  For example:

    (percentiles myhistogram [0.5 0.9 1.0])
    ;=> {0.5 200, 0.9 240, 1.0 500}

  This means that:

  * 50% of the values recorded by this Histogram were less than or equal to 200
  * 90% were less than or equal to 240
  * 100% were less than or equal to 500

  If you don't pass a list of desired percentiles, the default will be
  [0.75 0.95 0.99 0.999 1.0]."
  ([^Histogram h]
   (percentiles h [0.75 0.95 0.99 0.999 1.0]))
  ([^Histogram h ps]
   (get-percentiles h ps)))


(defn number-recorded
  "Return the number of values recorded by the given Histogram."
  [^Histogram h]
  (.count h))

(defn largest
  "Return the largest value recorded by the given Histogram."
  [^Histogram h]
  (.max h))

(defn smallest
  "Return the smallest value recorded by the given Histogram."
  [^Histogram h]
  (.min h))

(defn sample
  "Return the values in the given Histogram's current sampling.

  This is almost certainly NOT what you want.  Read up on how these histograms
  work and make sure you understand it before using this function."
  [^Histogram h]
  (.getValues (.getSnapshot h)))


; Write -----------------------------------------------------------------------
(defn update!
  "Record a value in the given Histogram."
  [^Histogram h n]
  (.update h (long n))
  h)

(defn clear!
  "Clear all data from the given Histogram."
  [^Histogram h]
  (.clear h)
  h)

