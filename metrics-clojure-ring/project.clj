(defproject metrics-clojure-ring "2.3.0-beta2"
  :description "Various things gluing together metrics-clojure and ring."
  :dependencies [[cheshire "5.3.1"]
                 [metrics-clojure "2.3.0-beta2"]]
  :profiles {:dev {:dependencies [[ring "1.3.0"]]}})
