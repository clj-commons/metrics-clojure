(defproject metrics-clojure "0.9.2"
  :description "A Clojure façade for Coda Hale's metrics library."
  :dependencies [[org.clojure/clojure "[1.2.1,1.4.0]"]
                 [com.yammer.metrics/metrics-core "2.2.0"]]
  :repositories {"repo.codahale.com" "http://repo.codahale.com"}
  :warn-on-reflection true)
