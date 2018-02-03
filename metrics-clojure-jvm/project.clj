(defproject metrics-clojure-jvm "3.0.0-SNAPSHOT"
  :description "Gluing together metrics-clojure and jvm instrumentation."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-jvm "3.2.2"]])
