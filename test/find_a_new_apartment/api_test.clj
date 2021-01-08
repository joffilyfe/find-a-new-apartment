(ns find-a-new-apartment.api-test
  (:require [clojure.test :as t :refer [deftest is testing]]
            [find-a-new-apartment.api :as api]
            [clojure.string :as str]))

(deftest the-api-response-fields
  (testing "The api response fields must include the custo keywor"
    (is (str/includes? api/api-response-fields "custo")))

  (testing "The api response fields must include the id"
    (is (str/includes? api/api-response-fields "id"))))

(deftest the-build-api-query-body
  (testing "The query Q field"
    (let [q (api/build-q-field 50 1000)]
      (is (= "(and custo:[50,1000]for_rent:'true')" q))))

  (testing "Must contain the price range"
    (let [result (api/build-api-query-body 50 1000 0 10)
          q (get-in result [:criteria :q])]
      (is (= "(and custo:[50,1000]for_rent:'true')" q)))))

(def fake-api-response-data
  {:body "{\"status\":{\"rid\":\"id\"},\"hits\":{\"hit\":[{\"fields\": {}},{\"fields\": {}}]}}"})

(deftest parse-api-fetch-result
  (testing "The API response body"
    (testing "Should contain a list with apartments"
      (let [apartment-list-size
            (count (api/extract-api-result-apartments fake-api-response-data))]
        (is (= 2 apartment-list-size))))))
