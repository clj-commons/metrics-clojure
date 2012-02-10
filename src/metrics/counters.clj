(ns metrics.counters
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Counter)))


; Create ----------------------------------------------------------------------
(defn counter [title]
  (Metrics/newCounter (metric-name title)))


; Read ------------------------------------------------------------------------
(defn value [^Counter c]
  (.count c))


; Write -----------------------------------------------------------------------
(defn inc!
  ([^Counter c] (inc! c 1))
  ([^Counter c n]
   (.inc c n)
   c))

(defn dec!
  ([^Counter c] (dec! c 1))
  ([^Counter c n]
   (.dec c n)
   c))

(defn clear! [^Counter c]
  (.clear c)
  c)

