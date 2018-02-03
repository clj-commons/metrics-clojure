(defproject metrics-clojure-ganglia "3.0.0-SNAPSHOT"
  :description "Ganglia reporter integration for metrics-clojure"
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-ganglia "3.2.2"]])
