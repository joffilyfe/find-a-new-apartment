(ns find-a-new-apartment.api
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def api-response-fields
  (str "id,aluguel,area,quartos,custo,"
       "endereco,regiao_nome,cidade,"
       "visit_status,special_conditions,"
       "listing_tags,tipo,for_rent,for_sale"))

(defn build-q-field
  "Retorna o parâmetro 'q' utilizado durante a consulta da API"
  [min-price max-price]
  (format "(and custo:[%s,%s]for_rent:'true')"
          min-price max-price))

(defn build-api-query-body
  [min-price max-price start size]
  {:criteria
   {:q (build-q-field min-price max-price)
    :q.parser "structured"
    :return api-response-fields
    :start start
    :expr.distance "floor(haversin(-23.618337831496305%2C-46.635496999999994%2Clocal.latitude%2Clocal.longitude)*1000*0.002)"
    :size size
    :fq "local:['-23.611692663457646,-46.64785661914062','-23.624982999534968,-46.62313738085937']"
    :expr.rank "((-10*distance%2Brelevance_score)*(0.1))"
    :sort "rank desc"}})

(defn fetch-apartments
  "Retorna a resposta de consulta da API"
  [url min-price max-price start size]
  (let [data (build-api-query-body min-price max-price start size)]
    (client/post url
                 {:body (json/write-str data) :accept :json}
                 {:headers {"User-Agent" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"}})))

(defn extract-api-result-apartments
  "Extrai a lista de apartmentos a partir da resposta da API"
  [data]
  (-> data
      (:body)
      (json/read-str)
      (second)
      (second)
      (get "hit")
      ((fn [aps] (map #(% "fields") aps)))))
