(ns metrics.core
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.reporting ConsoleReporter))
  (:import (java.util.concurrent TimeUnit)))


(defn remove-metric [title]
  (.removeMetric (Metrics/defaultRegistry) (metric-name title)))


(defn report-to-console [seconds]
  (ConsoleReporter/enable seconds TimeUnit/SECONDS))
