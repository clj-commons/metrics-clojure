(ns metrics.reporters.test.console-test
  (:require [metrics.reporters.console :refer [reporter]]
            [clojure.test :refer :all]))

(deftest test-console-reporter
  "Checks that the console reporter can be instantiated."
  (is (reporter {})))
