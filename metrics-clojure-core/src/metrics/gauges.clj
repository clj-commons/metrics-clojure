(ns metrics.gauges
  (:require [metrics.core :refer [default-registry metric-name]]
            [metrics.utils :refer [desugared-title]])
  (:import [com.codahale.metrics MetricRegistry MetricFilter Gauge]
           clojure.lang.IFn))

(defn gauge-fn
  "Create a new Gauge metric with the given title.

  The given function will be called (with no arguments) to retrieve the value of
  the Gauge when requested."
  ([title ^IFn f]
   (gauge-fn default-registry title f))
  ([^MetricRegistry reg title ^IFn f]
   (let [g (reify Gauge
             (getValue [this]
               (f)))
         s (metric-name title)]
     (.remove reg s)
     (.register reg s g))))

(defn gauge
  "Retrieve an existing gauge from the provided registry (or the
  default registry) from its name. Returns nil when not found."
  ([title]
   (gauge default-registry title))
  ([^MetricRegistry reg title]
   (when-let [matches (seq (.getGauges reg (reify MetricFilter
                                             (matches [this name _]
                                               (= name (metric-name title))))))]
     (val (first matches)))))


(defmacro defgauge
  "Define a new Gauge metric with the given title and a function.
   to call to retrieve the value of the Gauge.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a gauge titled \"default.default.foo\" into var foo
    (defgauge foo ,,,)
    (defgauge \"foo\" ,,,)

    ; Define a gauge titled \"a.b.c\" into var c
    (defgauge [a b c] ,,,)
    (defgauge [\"a\" \"b\" \"c\"] ,,,)
    (defgauge [a \"b\" c] ,,,)
  "
  ([title ^clojure.lang.IFn f]
   (let [[s title] (desugared-title title)]
     `(def ~s (gauge-fn '~title ~f))))
  ([^MetricRegistry reg title ^clojure.lang.IFn f]
   (let [[s title] (desugared-title title)]
     `(def ~s (gauge-fn ~reg '~title ~f)))))


(defn value
  "Return the value of the given Gauge."
  [^Gauge g]
  (.getValue g))

