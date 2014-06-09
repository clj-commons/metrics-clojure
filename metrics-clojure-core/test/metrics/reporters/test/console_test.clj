(ns metrics.reporters.test.console-test
  (:require [metrics.reporters.console :refer [reporter]])
  (:use [clojure.test]))

(deftest test-console-reporter
  "Checks that the console reporter can be instantiated."
  (is (reporter {})))
