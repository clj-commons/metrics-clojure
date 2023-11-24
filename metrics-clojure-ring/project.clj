(defproject metrics-clojure-ring "3.0.0-SNAPSHOT"
  :description "Various things gluing together metrics-clojure and ring."
  :url "https://github.com/clj-commons/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[cheshire "5.10.0"]
                 [metrics-clojure "3.0.0-SNAPSHOT"]]
  :profiles {:dev {:global-vars {*warn-on-reflection* true}
                   :dependencies [[ring "1.4.0"]
                                  [ring/ring-mock "0.3.0"]]}})
