(ns metrics.histograms
  (:require [metrics.core :refer [default-registry metric-name]]
            [metrics.utils :refer [get-percentiles desugared-title snapshot]])
  (:import [com.codahale.metrics MetricRegistry Histogram Snapshot]))


(defn histogram
  "Create and return a Histogram metric with the given title.

  By default a biased Histogram is created.  This is probably what you want, but
  if you know what you're doing you can pass false to create a uniform one
  instead."
  ([title]
     (histogram default-registry title))
  ([^MetricRegistry reg title]
     (.histogram reg (metric-name title))))


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
  ([title]
     (let [[s title] (desugared-title title)]
       `(def ~s (histogram '~title))))
  ([^MetricRegistry reg title]
     (let [[s title] (desugared-title title)]
       `(def ~s (histogram ~reg '~title)))))

(defn mean
  "Return the mean value of the given histogram."
  [^Histogram h]
  (let [sn (snapshot h)]
    (.getMean sn)))

(defn std-dev
  "Return the standard deviation of the given Histogram."
  [^Histogram h]
  (let [sn (snapshot h)]
    (.getStdDev sn)))

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
  (.getCount h))

(defn largest
  "Return the largest value recorded by the given Histogram."
  [^Histogram h]
  (let [sn (snapshot h)]
    (.getMax sn)))

(defn smallest
  "Return the smallest value recorded by the given Histogram."
  [^Histogram h]
  (let [sn (snapshot h)]
    (.getMin sn)))

(defn sample
  "Return the values in the given histogram's current sampling.

  This is almost certainly NOT what you want. Read up on how these histograms
  work and make sure you understand it before using this function."
  [^Histogram h]
  (.getValues (snapshot h)))


(defn update!
  "Record a value in the given Histogram."
  [^Histogram h n]
  (.update h (long n))
  h)

