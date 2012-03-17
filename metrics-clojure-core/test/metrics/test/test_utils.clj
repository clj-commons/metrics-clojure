(ns metrics.test.test-utils)

(defn abs [n]
  (if (> n 0) n (* -1 n)))

(defn within-one [a b]
  (<= (abs (- a b))
      1))

(defn within-ten [a b]
  (<= (abs (- a b))
      10))

(defn maps-within-one [a b]
  (when (= (set (keys a)) (set (keys b)))
    (every? identity
            (map #(within-one (a %) (b %))
                 (keys a)))))

