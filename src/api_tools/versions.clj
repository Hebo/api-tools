(ns api-tools.versions
  (:require [clj-http.client :as client]
            [clojure.pprint]))

(defn make-url
  [host]
  (format "http://%s/kraken/games/top?limit=1&on_site=1" host))

(defn get-ver
  [resp]
  (get-in resp [:headers "x-api-version"]))

(defn try-version
  [host ver]
  (let [resp (client/get (make-url host)
                         {:headers {"Accept" (str "application/vnd.twitchtv.v" ver "+json")}})]
    (let [])
    (if (not= (get-ver resp) ver)
      (do (println (str "Error! got version: "
                        (get-ver resp)
                        " expected: "
                        ver))
        (clojure.pprint/pprint resp))
      (println "No Incident."))))


(defn find-bug
  [host]
  (doseq [ver (cycle ["2" "3"])]
    (try-version host ver)
    (Thread/sleep 1000)))

(defn -main
  [host]
  (println "Testing against" host)
  (find-bug host))
