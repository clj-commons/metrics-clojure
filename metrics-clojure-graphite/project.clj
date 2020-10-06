(defproject metrics-clojure-graphite "3.0.0-SNAPSHOT"
  :description "Graphite reporter integration for metrics-clojure"
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-graphite "4.0.5"]])
