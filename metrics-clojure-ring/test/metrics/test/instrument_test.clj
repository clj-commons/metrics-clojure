(ns metrics.test.instrument-test
  (:require [clojure.test :refer :all]
            [metrics.ring.instrument :refer [instrument instrument-by uri-prefix]]
            [ring.mock.request :as mock]
            [ring.util.request :as request])
  (:import [com.codahale.metrics MetricRegistry]))

(def dummy-handler (fn [req]
                     req))

(defn tracked? [^MetricRegistry reg metric]
  (->> (.getMetrics reg)
       (map (fn [[k v]] k))
       (set)
       (some #{metric})
       (boolean)))

(defn metric [^MetricRegistry reg metric]
  (get (.getMetrics reg) metric))

(def expected-metrics
  ["ring.handling-time.TRACE"
   "ring.handling-time.CONNECT"
   "ring.handling-time.DELETE"
   "ring.handling-time.GET"
   "ring.handling-time.HEAD"
   "ring.handling-time.OPTIONS"
   "ring.handling-time.OTHER"
   "ring.handling-time.POST"
   "ring.handling-time.PUT"
   "ring.requests-scheme.rate.http"
   "ring.requests-scheme.rate.https"
   "ring.requests.active"
   "ring.requests.rate"
   "ring.requests.rate.CONNECT"
   "ring.requests.rate.DELETE"
   "ring.requests.rate.GET"
   "ring.requests.rate.HEAD"
   "ring.requests.rate.OPTIONS"
   "ring.requests.rate.OTHER"
   "ring.requests.rate.POST"
   "ring.requests.rate.PUT"
   "ring.requests.rate.TRACE"
   "ring.responses.rate"
   "ring.responses.rate.2xx"
   "ring.responses.rate.3xx"
   "ring.responses.rate.4xx"
   "ring.responses.rate.5xx"])

(deftest test-instrument
  (testing "instrument without custom prefix"
    (let [reg (MetricRegistry. )
          app (instrument dummy-handler reg)]

      (app (mock/request :get "/yolo" {}))

      (doseq [m expected-metrics]
        (is (tracked? reg m))))))

(deftest test-uri-prefix
  (is (= ["foo"]
         (uri-prefix (mock/request :get "/foo"))))  
  (is (= ["foo" "bar" "baz"]
         (uri-prefix (mock/request :get "/foo/bar/baz")))))

(deftest test-instrument-by-uri-prefix
  (let [reg (MetricRegistry.)
        app (instrument-by dummy-handler reg uri-prefix)]

    (app (mock/request :get "/yolo" {}))

    (doseq [m expected-metrics]
      (is (tracked? reg (str "yolo." m))))))
