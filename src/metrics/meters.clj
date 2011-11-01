(ns metrics.meters
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (java.util.concurrent TimeUnit)))


(defn meter [title event-type]
  (Metrics/newMeter (metric-name title)
                    event-type
                    TimeUnit/SECONDS))

(defn mark! [m]
  (.mark m)
  m)

