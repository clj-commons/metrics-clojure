(defproject metrics-clojure-ring "2.4.0"
  :description "Various things gluing together metrics-clojure and ring."
  :dependencies [[cheshire "5.3.1"]
                 [metrics-clojure "2.4.0"]]
  :profiles {:dev {:dependencies [[ring "1.3.0"]]}})
