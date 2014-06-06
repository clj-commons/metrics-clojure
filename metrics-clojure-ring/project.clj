(defproject metrics-clojure-ring "2.1.0-SNAPSHOT"
  :description "Various things gluing together metrics-clojure and ring."
  :dependencies [[cheshire "5.3.1"]
                 [metrics-clojure "2.0.0"]]
  :profiles {:dev {:dependencies [[ring "1.2.2"]]}})
