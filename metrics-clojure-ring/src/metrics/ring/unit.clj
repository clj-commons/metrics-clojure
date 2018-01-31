(ns metrics.ring.unit
  (:require [clojure.string :as string])
  (:import (java.util.concurrent TimeUnit)))


(defn- rate-factor [^TimeUnit rate-unit]
  (.toSeconds rate-unit 1))

(defn- duration-factor [^TimeUnit duration-unit]
  (/ 1.0 (.toNanos duration-unit 1)))

(defn- duration-unit-label [^TimeUnit duration-unit]
  (string/lower-case duration-unit))

(defn- rate-unit-label [^TimeUnit rate-unit]
  (let [ustr (str rate-unit)]
    (str "events/" (subs (string/lower-case ustr) 0 (dec (count ustr))))))

(defn- scale-metrics-map [m factor & ignored-keys]
  (let [ignored (into #{} ignored-keys)]
    (into {} (for [[k v] m]
               [k (if (ignored k) v (* v factor))]))))


(defn build-options [^TimeUnit rate-unit ^TimeUnit duration-unit]
  {:rate-factor (rate-factor rate-unit)
   :rate-unit-label (rate-unit-label rate-unit)
   :duration-factor (duration-factor duration-unit)
   :duration-unit-label (duration-unit-label duration-unit)})

(defn convert-duration [v {:keys [duration-factor]}]
  (* v duration-factor))

(defn convert-rates [rates {:keys [rate-factor]}]
  (scale-metrics-map rates rate-factor :mean :total))

(defn convert-percentiles [percentiles {:keys [duration-factor]}]
  (scale-metrics-map percentiles duration-factor))
