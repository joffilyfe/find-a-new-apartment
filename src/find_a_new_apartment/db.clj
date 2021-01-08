(ns find-a-new-apartment.db)

;; now
(defn- now []
  (str (java.time.LocalDateTime/now)))

(defn add-apartment
  [apartment, db]
  (let [id (apartment "id")
        custo (apartment "custo")]
    (swap! db assoc id {:custo custo :date (now)})))

(defn- should-insert?
  "Verifica se um deve ser inserido em um banco de dados"
  [apartment db]
  (let [id (apartment "id")]
    (not (contains? db id))))


;; filtra os novos apartamentos
(defn filter-new-apatments [apartments db]
  (filter #(should-insert? % @db) apartments))