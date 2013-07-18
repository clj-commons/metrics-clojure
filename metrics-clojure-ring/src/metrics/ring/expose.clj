(ns metrics.ring.expose
  (:use [cheshire.core :as json]
        [ring.util.response :only [header response]]
        [metrics.expose :as expose]))


;; Utils -----------------------------------------------------------------------
(defn- ensure-leading-slash [s]
  (if (not= \/ (first s))
    (str \/ s)
    s))

(defn- strip-trailing-slash [s]
  (if (= \/ (last s))
    (apply str (butlast s))
    s))

(defn- sanitize-uri [uri]
  (str (-> uri
           ensure-leading-slash
           strip-trailing-slash)
       \/))

(defn- render-metric [[metric-name metric]]
  [metric-name (render-to-basic metric)])


;; Exposing --------------------------------------------------------------------
(defn expose-metrics-as-json
  ([handler]
     (expose-metrics-as-json handler "/metrics"))
  ([handler uri]
     (fn [request]
       (let [request-uri (:uri request)]
         (if (or (.startsWith request-uri (sanitize-uri uri))
                 (= request-uri uri))
           (-> (expose/json-metrics)
               json/encode
               response
               (header "Content-Type" "application/json"))
           (handler request))))))
