(ns incomplete-database-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def incomplete-database "
	varon(juan).
	varon
")

(deftest valid-fact-test
    (testing "'varon' should be an invalid fact"
        (is (= (valid-fact "varon")
            false))) 
    (testing "'varon' should be a valid fact"
        (is (= (valid-fact "varon(juan)")
            true))) 
    (testing "'padre(juan, pepe)' should be a valid fact"
        (is (= (valid-fact "padre(juan, pepe)")
            true))) 
)

(deftest valid-rule-test
    (testing "'varon' should be an invalid rule"
        (is (= (valid-rule "varon")
            false))) 
    (testing "'hijo(X, Y) :- varon(X), padre(Y, X)' should be a valid rule"
        (is (= (valid-rule "hijo(X, Y) :- varon(X), padre(Y, X)")
            true)))
)

(deftest valid-relation-element-test
    (testing "'varon' should be an invalid relation element"
        (is (= (valid-relation-element "varon")
            false))) 
    (testing "'varon' should be a valid relation element"
        (is (= (valid-relation-element "varon(juan)")
            true))) 
    (testing "'hijo(X, Y) :- varon(X), padre(Y, X)' should be a valid relation element"
        (is (= (valid-relation-element "hijo(X, Y) :- varon(X), padre(Y, X)")
            true)))
)

(deftest incomplete-database-fact-test
  (testing "varon(juan) should be nil"
    (is (= (evaluate-query incomplete-database "varon(juan)")
           nil))) 
  (testing "varon(maria) should be nil"
    (is (= (evaluate-query incomplete-database "varon(maria)")
           nil))) 
  (testing "mujer(cecilia) should be nil"
    (is (= (evaluate-query incomplete-database "mujer(cecilia)")
           nil))) 
  (testing "padre(juan, pepe) should be nil"
    (is (= (evaluate-query incomplete-database "padre(juan, pepe)")
           nil))) 
  (testing "padre(mario, pepe) should be nil"
    (is (= (evaluate-query incomplete-database "padre(mario, pepe)")
           nil))))

(deftest incomplete-database-rule-test
  (testing "hijo(pepe, juan) should be nil"
    (is (= (evaluate-query incomplete-database "hijo(pepe, juan)")
           nil))) 
  (testing "hija(maria, roberto) should be nil"
    (is (= (evaluate-query incomplete-database "hija(maria, roberto)")
           nil))))
