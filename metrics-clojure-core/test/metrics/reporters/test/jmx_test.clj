(ns metrics.reporters.test.jmx-test
  (:require [metrics.reporters.jmx :refer [reporter]]
            [clojure.test :refer :all]))

(deftest test-jmx-reporter
  "Checks that the jmx reporter can be instantiated."
  (is (reporter {})))
