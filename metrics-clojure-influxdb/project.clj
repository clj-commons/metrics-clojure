(defproject metrics-clojure-influxdb "2.10.0"
  :description "InfluxDB reporter integration for metrics-clojure"
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "2.10.0"]
                 [com.izettle/dropwizard-metrics-influxdb "1.1.8"]])
