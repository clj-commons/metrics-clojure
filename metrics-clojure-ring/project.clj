(defproject malesch/metrics-clojure-ring "2.9.1"
  :description "Various things gluing together metrics-clojure and ring."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[cheshire "5.7.1"]
                 [malesch/metrics-clojure "2.9.1"]]
  :profiles {:dev {:global-vars {*warn-on-reflection* true}
                   :dependencies [[ring "1.4.0"]
                                  [ring/ring-mock "0.3.0"]]}})
