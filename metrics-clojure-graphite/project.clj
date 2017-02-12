(defproject metrics-clojure-graphite "2.9.0"
  :description "Graphite reporter integration for metrics-clojure"
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "2.9.0"]
                 [io.dropwizard.metrics/metrics-graphite "3.1.2"]])
