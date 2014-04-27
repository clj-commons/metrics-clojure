(ns metrics.meters
  (:refer-clojure :exclude [count])
  (:require [metrics.core :refer [default-registry metric-name]]
            [metrics.utils :refer [desugared-title]])
  (:import [com.codahale.metrics MetricRegistry Meter]))


(defn meter
  ([title]
     (meter default-registry title)) 
  ([^MetricRegistry reg title]
     (.meter reg (metric-name title))))


(defmacro defmeter
  "Define a new Meter metric with a given title and event type.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a meter titled \"default.default.foo\" into var foo
    (defmeter foo ,,,)
    (defmeter \"foo\" ,,,)

    ; Define a meter titled \"a.b.c\" into var c
    (defmeter [a b c] ,,,)
    (defmeter [\"a\" \"b\" \"c\"] ,,,)
    (defmeter [a \"b\" c] ,,,)
  "
  ([title]
     (let [[s title] (desugared-title title)]
       `(def ~s (meter '~title))))
  ([^MetricRegistry reg title]
     (let [[s title] (desugared-title title)]
       `(def ~s (meter ~reg '~title)))))

(defn rate-one
  [^Meter m]
  (.getOneMinuteRate m))

(defn rate-five
  [^Meter m]
  (.getFiveMinuteRate m))

(defn rate-fifteen
  [^Meter m]
  (.getFifteenMinuteRate m))

(defn rate-mean
  [^Meter m]
  (.getMeanRate m))

(defn count
  [^Meter m]
  (.getCount m))

(defn rates
  [^Meter m]
  {1 (rate-one m)
   5 (rate-five m)
   15 (rate-fifteen m)
   :total (count m)})

(defn mark!
  ([^Meter m]
     (mark! m 1))
  ([^Meter m n]
     (.mark m (long n))
     m))

