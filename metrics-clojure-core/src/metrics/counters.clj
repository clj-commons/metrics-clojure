(ns metrics.counters
  (:use [metrics.utils :only (metric-name)])
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Counter)))


; Create ----------------------------------------------------------------------
(defn counter
  "Create and return a new Counter metric with the given title.

  Title can be a plain string like \"foo\" or a vector of three strings like:

      [\"myapp\" \"webserver\" \"connections\"]

  "
  [title]
  (Metrics/newCounter (metric-name title)))


; Read ------------------------------------------------------------------------
(defn value
  "Return the current value of the counter."
  [^Counter c]
  (.count c))


; Write -----------------------------------------------------------------------
(defn inc!
  "Increment the counter by the given amount (or 1 if not specified)."
  ([^Counter c] (inc! c 1))
  ([^Counter c n]
   (.inc c n)
   c))

(defn dec!
  "Decrement the counter by the given amount (or 1 if not specified)."
  ([^Counter c] (dec! c 1))
  ([^Counter c n]
   (.dec c n)
   c))

(defn clear!
  "Clear the given counter, resetting its value to zero."
  [^Counter c]
  (.clear c)
  c)

