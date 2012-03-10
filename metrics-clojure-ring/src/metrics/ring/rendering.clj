(ns metrics.rendering)


(defprotocol RenderableMetric
  (render-to-basic [metric] "Turn a metric into a basic Clojure datastructure."))

(defn render-metric)

