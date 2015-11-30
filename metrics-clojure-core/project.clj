(defproject metrics-clojure "2.6.0"
  :description "A Clojure façade for Coda Hale's metrics library."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [io.dropwizard.metrics/metrics-core "3.1.2"]]
  :repositories {"repo.codahale.com" "http://repo.codahale.com"
                 ;; to get Clojure snapshots
                 "sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail :update :always}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
    :profiles {:1.6    {:dependencies [[org.clojure/clojure "1.6.0"]]}
               :1.8    {:dependencies [[org.clojure/clojure "1.8.0-RC2"]]}
               :master {:dependencies [[org.clojure/clojure "1.8.0-master-SNAPSHOT"]]}}
  :aliases  {"all" ["with-profile" "+dev:+1.6:+1.8:+master"]}
  :global-vars {*warn-on-reflection* true})
