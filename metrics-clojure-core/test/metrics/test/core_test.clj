(ns metrics.test.core-test
  (:require [metrics.core :as core]
            [clojure.test :refer :all]))

(deftest regression-default-registry-tag
  (testing "Ensure default-registry no longer has invalid reflection :tag"
    (.getMeters core/default-registry)))
