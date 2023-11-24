(defproject metrics-clojure-jvm "3.0.0-SNAPSHOT"
  :description "Gluing together metrics-clojure and jvm instrumentation."
  :url "https://github.com/clj-commons/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-jvm "4.0.5"]])
