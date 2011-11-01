(ns metrics.counters
  (use [metrics.utils :only (metric-name)])
  (import (com.yammer.metrics Metrics)))

(defn counter [title]
  (Metrics/newCounter (metric-name title)))

(defn inc!
  ([c] (inc! c 1))
  ([c n]
   (.inc c n)
   c))

(defn dec!
  ([c] (dec! c 1))
  ([c n]
   (.dec c n)
   n))

