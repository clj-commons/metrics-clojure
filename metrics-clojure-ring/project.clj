(defproject metrics-clojure-ring "1.1.0"
  :description "Various things gluing together metrics-clojure and ring."
  :dependencies [[cheshire "5.3.1"]
                 [metrics-clojure "1.1.0"]]
  :profiles {:dev {:dependencies [[ring "1.2.2"]]}})
