(defproject metrics-clojure "3.0.0-SNAPSHOT"
  :description "A Clojure façade for Coda Hale's metrics library."
  :url "https://github.com/sjl/metrics-clojure"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.dropwizard.metrics/metrics-core "4.0.5"]
                 [io.dropwizard.metrics/metrics-jmx "4.0.5"]]
  :repositories {
                 ;; to get Clojure snapshots
                 "sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail :update :always}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
    :profiles {:1.8    {:dependencies [[org.clojure/clojure "1.8.0"]]}
               :master {:dependencies [[org.clojure/clojure "1.10.0-master-SNAPSHOT"]]}
               :dev    {:global-vars {*warn-on-reflection* true}}}
  :aliases  {"all" ["with-profile" "+dev:+1.8:+master"]}
  :global-vars {*warn-on-reflection* true})
