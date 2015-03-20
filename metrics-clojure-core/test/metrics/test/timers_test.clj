(ns metrics.test.timers-test
  (:require [metrics.core :as mc]
            [metrics.timers :as mt]
            [metrics.test.test-utils :refer :all]
            [clojure.test :refer :all]))

(def ^:const expiration-delay 6000)

(defn- sleep-100
  []
  (Thread/sleep 100)
  100)

(defn- sleep-200
  []
  (Thread/sleep 200)
  200)

(let [reg (mc/new-registry)]
  (mt/deftimer reg ["test" "timers" "deftimered"])

  (deftest test-deftimer
    (is (= (mt/rate-mean deftimered) 0.0))
    (is (= (mt/time! deftimered (sleep-100)) 100))
    (is (> (mt/rate-mean deftimered) 0.0))))


(deftest test-rate-mean
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-mean"])]
    (is (= (mt/rate-mean t) 0.0))
    (is (= (mt/time! t (sleep-100)) 100))
    (is (> (mt/rate-mean t) 0.0))))

(deftest test-rate-mean-fn
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-mean-fn"])]
    (is (= (mt/rate-mean t) 0.0))
    (is (= (mt/time-fn! t sleep-100) 100))
    (is (> (mt/rate-mean t) 0.0))))

(deftest test-rate-one
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-one"])]
    (is (= (mt/rate-one t) 0.0))
    (is (= (mt/time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-one t) 0.0))))

(deftest test-rate-five
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-five"])]
    (is (= (mt/rate-five t) 0.0))
    (is (= (mt/time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-five t) 0.0))))

(deftest test-rate-fifteen
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-fifteen"])]
    (is (= (mt/rate-fifteen t) 0.0))
    (is (= (mt/time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-fifteen t) 0.0))))

(deftest test-rate-mean-start-stop
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-mean"])]
    (is (= (mt/rate-mean t) 0.0))
    (is (= (mt/start-stop-time! t (sleep-100)) 100))
    (is (> (mt/rate-mean t) 0.0))))

(deftest test-rate-one-start-stop
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-one"])]
    (is (= (mt/rate-one t) 0.0))
    (is (= (mt/start-stop-time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-one t) 0.0))))

(deftest test-rate-five-start-stop
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-five"])]
    (is (= (mt/rate-five t) 0.0))
    (is (= (mt/start-stop-time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-five t) 0.0))))

(deftest test-rate-fifteen-start-stop
  (let [r (mc/new-registry)
        t (mt/timer r ["test" "timers" "test-rate-fifteen"])]
    (is (= (mt/rate-fifteen t) 0.0))
    (is (= (mt/start-stop-time! t (sleep-100)) 100))
    (Thread/sleep expiration-delay)
    (is (> (mt/rate-fifteen t) 0.0))))
