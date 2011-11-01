(ns metrics.timers
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics))
  (import (java.util.concurrent TimeUnit)))


(defn timer [title]
  (Metrics/newTimer (metric-name title)
                    TimeUnit/MILLISECONDS
                    TimeUnit/SECONDS))

(defmacro time! [t & body]
  `(.time ~t
         (proxy [Callable] []
           (call [] (do ~@body)))))

(defn time-fn! [t f]
  (.time t
         (proxy [Callable] []
           (call [] (f)))))

