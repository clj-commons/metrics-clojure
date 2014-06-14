(ns metrics.reporters
  "Core functions for working with reporters"
  (:import java.util.concurrent.TimeUnit
           [com.codahale.metrics ScheduledReporter]))


(defn start
  "Starts reporting"
  [^ScheduledReporter r ^long seconds]
  (.start r seconds TimeUnit/SECONDS))

(defn stop
  "Stops reporting"
  [^ScheduledReporter r]
  (.stop r))
