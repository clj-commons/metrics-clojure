(defproject metrics-clojure-jvm "2.9.0-SNAPSHOT"
  :description "Gluing together metrics-clojure and jvm instrumentation."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "2.9.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-jvm "3.1.2"]])
