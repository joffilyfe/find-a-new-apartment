(ns find-a-new-apartment.core
  (:require [find-a-new-apartment.api :as api]
            [find-a-new-apartment.db :as db]))

;; database
(def database (atom {}))
(reset! database {"893189962" {:custo "2186", :date "2021-01-08T17:39:40.965699"}
                  "893189970" {:custo "2083", :date "2021-01-08T17:39:40.965686"}
                  "892912405" {:custo "1985", :date "2021-01-08T17:39:40.965710"}
                  "892827883" {:custo "2181", :date "2021-01-08T17:39:40.965569"}
                  "893208303" {:custo "1932", :date "2021-01-08T17:39:40.965943"}
                  "892895113" {:custo "2517", :date "2021-01-08T17:39:40.965780"}
                  "893202138" {:custo "2229", :date "2021-01-08T17:39:40.965908"}
                  "893211856" {:custo "2142", :date "2021-01-08T17:39:40.965649"}
                  "893115083" {:custo "2170", :date "2021-01-08T17:39:40.965831"}
                  "893151060" {:custo "2452", :date "2021-01-08T17:39:40.965814"}
                  "893221189" {:custo "2336", :date "2021-01-08T17:39:40.965489"}
                  "893193721" {:custo "2405", :date "2021-01-08T17:39:40.965581"}
                  "892834014" {:custo "2315", :date "2021-01-08T17:39:40.965552"}
                  "893113307" {:custo "2429", :date "2021-01-08T17:39:40.965726"}
                  "893115795" {:custo "2404", :date "2021-01-08T17:39:40.965861"}
                  "892996521" {:custo "2378", :date "2021-01-08T17:39:40.965758"}
                  "892850651" {:custo "2445", :date "2021-01-08T17:39:40.965929"}
                  "893208393" {:custo "2247", :date "2021-01-08T17:39:40.965738"}
                  "893106226" {:custo "2078", :date "2021-01-08T17:39:40.965748"}})

;; fetch the API URL from env
(def url
  (cond
    (nil? (System/getenv "NEW_AP_API_URL")) "https://www.quintoandar.com.br/api/yellow-pages/search"))
:else (System/getenv "NEW_AP_API_URL")

;; return only new apartments
(defn filter-new-apartments [response]
  (-> response
      (api/extract-api-result-apartments)
      (db/filter-new-apatments database)))

(defn -main [& _]
  (let [response (api/fetch-apartments url 1800 2600 0 100)
        new-aps (filter-new-apartments response)]
    (prn new-aps)))