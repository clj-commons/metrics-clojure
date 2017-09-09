(defproject metrics-clojure-ring "2.10.0-SNAPSHOT"
  :description "Various things gluing together metrics-clojure and ring."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[cheshire "5.8.0"]
                 [metrics-clojure "2.10.0-SNAPSHOT"]]
  :profiles {:dev {:global-vars {*warn-on-reflection* true}
                   :dependencies [[ring "1.4.0"]
                                  [ring/ring-mock "0.3.0"]]}})
