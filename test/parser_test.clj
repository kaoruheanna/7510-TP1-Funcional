(ns parser-test
  (:require [clojure.test :refer :all]
            [input-parser :refer :all]))

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
    (testing "'varon(juan)' should be a valid relation element"
        (is (= (valid-relation-element "varon(juan)")
            true))) 
    (testing "'hijo(X, Y) :- varon(X), padre(Y, X)' should be a valid relation element"
        (is (= (valid-relation-element "hijo(X, Y) :- varon(X), padre(Y, X)")
            true)))
)

(deftest fact-hash-map
    (testing "'hashmap equivalence equal"
        (is (= {:name "Steve" :age 24 :salary 7886 :company "Acme"} {:name "Steve" :age 24 :salary 7886 :company "Acme"})
            ))
    (testing "'hashmap equivalence different keys"
        (is (not= {:name "Steve" :age 24 :salary 7886 :company "Acme"} {:name "Steve" :age 24 })
            ))
    (testing "'hashmap equivalence different values"
        (is (not= {:name "Steve" :age 25 } {:name "Steve" :age 24 })
            ))
    (testing "'hashmap equivalence equal containing array"
        (is (= {:name "Steve" :children '("juan" "roman" "riquelme") } {:name "Steve" :children '("juan" "roman" "riquelme") })
            ))
    (testing "'hashmap equivalence different containing array"
        (is (not= {:name "Steve" :children '("juan" "roman" "riquelme") } {:name "Steve" :children '("lionel" "andres" "messi") })
            ))
    (testing "'varon(juan)' should return { method:varon, args: (juan) }"
        (is (= (split-fact "varon(juan)") {:method "varon" :args '("juan") } )
            ))
    (testing "'mujer(lucas)' should return { method:mujer, args: (lucas) }"
        (is (= (split-fact "mujer(juan)") {:method "mujer" :args '("juan") } )
            ))
    (testing "'varon(lucas)' should return { method:varon, args: (lucas) }"
        (is (= (split-fact "varon(lucas)") {:method "varon" :args '("lucas") } )
            ))
    (testing "add(zero, zero, zero)"
        (is (= (split-fact "add(zero, zero, zero)") {:method "add" :args '("zero" "zero" "zero") } )
            ))
)

