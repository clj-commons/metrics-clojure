(defproject metrics-clojure "2.0.4-SNAPSHOT"
  :description "A Clojure façade for Coda Hale's metrics library."
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.codahale.metrics/metrics-core "3.0.2"]]
  :repositories {"repo.codahale.com" "http://repo.codahale.com"
                 ;; to get Clojure snapshots
                 "sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail :update :always}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
    :profiles {:1.4    {:dependencies [[org.clojure/clojure "1.4.0"]]}
               :1.5    {:dependencies [[org.clojure/clojure "1.5.1"]]}
               :master {:dependencies [[org.clojure/clojure "1.7.0-master-SNAPSHOT"]]}}
  :aliases  {"all" ["with-profile" "+dev:+1.4:+1.5:+master"]}
  :global-vars {*warn-on-reflection* true})
