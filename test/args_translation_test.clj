(ns args-translation-test
  (:require [clojure.test :refer :all]
            [logical-interpreter :refer :all]))

(def argsRule (list "X" "Y" "Z"))
(def argsQuery1 (list "two" "one" "one"))
(def argsTranslation1 (map list argsRule argsQuery1))
(deftest args-translation-test-1
	(testing "add(Y, Z, X) with (two, one, one)"
		(is (= 
			(replace-args-in-fact "add(Y, Z, X)" argsTranslation1)
			"add(one, one, two)"
		))
	)
)

(def argsQuery2 (list "zero" "four" "seven"))
(def argsTranslation2 (map list argsRule argsQuery2))
(deftest args-translation-test-2
	(testing "add(Y, Z, X) with (zero, four, seven)"
		(is (= 
			(replace-args-in-fact "add(Y, Z, X)" argsTranslation2)
			"add(four, seven, zero)"
		))
	)
)

(def argsRule3 (list "X" "Y"))
(def argsQuery3 (list "Luis" "Maria"))
(def argsTranslation3 (map list argsRule3 argsQuery3))
(deftest args-translation-test-3
	(testing "padre(Y, X) with (Luis, Maria)"
		(is (= 
			(replace-args-in-fact "padre(Y, X)" argsTranslation3)
			"padre(Maria, Luis)"
		))
	)
)
    

