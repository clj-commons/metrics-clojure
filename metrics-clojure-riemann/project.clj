(defproject metrics-clojure-riemann "3.0.0-SNAPSHOT"
  :description "Riemann reporter integration for metrics-clojure"
  :url "https://github.com/clj-commons/metrics-clojure"
  :license {:name "MIT"}
  :profiles {:dev {:global-vars {*warn-on-reflection* true}}}
  :dependencies [[metrics-clojure "3.0.0-SNAPSHOT"]
                 [io.riemann/metrics4-riemann-reporter "0.5.1"]])
