(ns metrics.utils
  (import (com.yammer.metrics.core MetricName)))


(defn metric-name [title]
  (if (string? title)
    (new MetricName "default" "default" title)
    (new MetricName (first title) (second title) (last title))))
