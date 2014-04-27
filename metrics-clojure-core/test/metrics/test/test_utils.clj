(ns metrics.test.test-utils
  (:import java.util.concurrent.TimeUnit))

(defn abs
  [n]
  (if (> n 0) n (* -1 n)))

(defn maps-within-one
  [a b]
  (when (= (set (keys a)) (set (keys b)))
    (every? identity
            (map #(within-one (a %) (b %))
                 (keys a)))))
