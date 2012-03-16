(ns metrics.gauges
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Gauge)))


; Create ----------------------------------------------------------------------
(defmacro gauge
  "Create a new Gauge metric with the given title.

  The body exprs will be used to retrieve the value of the Gauge when requested."
  [title & body]
  `(Metrics/newGauge (metric-name ~title)
                     (proxy [Gauge] []
                       (value [] (do ~@body)))))

(defn gauge-fn
  "Create a new Gauge metric with the given title.

  The given function will be called (with no arguments) to retrieve the value of
  the Gauge when requested."
  [title f]
  (Metrics/newGauge (metric-name title)
                    (proxy [Gauge] []
                      (value [] (f)))))

(defmacro defgauge [title & body]
  "Define a new Gauge metric with the given title.

   The rest of the arguments may be a body form or function to call to
   retrieve the value of the Gauge."
  (cond (and (= 1 (count body)) (and (symbol? (first body))
                                     (fn? (eval (first body)))))
        `(gauge-fn ~(str title) ~(first body))
        true
        `(gauge ~(str title) ~@body)))


; Read ------------------------------------------------------------------------
(defn value
  "Return the value of the given Gauge."
  [^Gauge g]
  (.value g))

