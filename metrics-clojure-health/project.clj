(defproject metrics-clojure-health "2.10.0"
  :description "Gluing together metrics-clojure and healthchecks."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "2.10.0"]
                 [io.dropwizard.metrics/metrics-healthchecks "3.2.2"]])
