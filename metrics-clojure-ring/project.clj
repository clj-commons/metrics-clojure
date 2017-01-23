(defproject metrics-clojure-ring "2.9.0-SNAPSHOT"
  :description "Various things gluing together metrics-clojure and ring."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[cheshire "5.7.0"]
                 [metrics-clojure "2.9.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[ring "1.4.0"]]}})
