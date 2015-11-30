(defproject metrics-clojure-ring "2.6.0"
  :description "Various things gluing together metrics-clojure and ring."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[cheshire "5.5.0"]
                 [metrics-clojure "2.6.0"]]
  :profiles {:dev {:dependencies [[ring "1.4.0"]]}})
