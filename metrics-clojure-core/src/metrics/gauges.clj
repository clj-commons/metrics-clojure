(ns metrics.gauges
  (:use [metrics.utils :only (metric-name desugared-title)])
  (:import com.yammer.metrics.Metrics
           com.yammer.metrics.core.Gauge))


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


(defmacro defgauge
  "Define a new Gauge metric with the given title.

  The rest of the arguments may be a body form or function to call to
  retrieve the value of the Gauge.

  The title uses some basic desugaring to let you concisely define metrics:

    ; Define a gauge titled \"default.default.foo\" into var foo
    (defgauge foo ,,,)
    (defgauge \"foo\" ,,,)

    ; Define a gauge titled \"a.b.c\" into var c
    (defgauge [a b c] ,,,)
    (defgauge [\"a\" \"b\" \"c\"] ,,,)
    (defgauge [a \"b\" c] ,,,)
  "
  [title & [b & bs :as body]]
  (let [[s title] (desugared-title title)]
    (if (and (empty? bs)
             (symbol? b)
             (fn? (eval b)))
      `(def ~s (gauge-fn '~title ~b))
      `(def ~s (gauge '~title ~@body)))))


; Read ------------------------------------------------------------------------
(defn value
  "Return the value of the given Gauge."
  [^Gauge g]
  (.value g))

