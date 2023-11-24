(defproject metrics-clojure-health "3.0.0-SNAPSHOT"
  :description "Gluing together metrics-clojure and healthchecks."
  :url "https://github.com/clj-commons/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.dropwizard.metrics/metrics-healthchecks "4.0.5"]])
