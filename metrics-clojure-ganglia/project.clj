(defproject metrics-clojure-ganglia "2.10.0"
  :description "Ganglia reporter integration for metrics-clojure"
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "2.10.0"]
                 [io.dropwizard.metrics/metrics-ganglia "3.2.2"]])
