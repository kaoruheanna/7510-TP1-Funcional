(ns facts-hashmap-test
  	(:require [clojure.test :refer :all]
    [logical-interpreter :refer :all]))

(def facts-array '(
	{:method "varon" :args '("juan") }
	{:method "mujer" :args '("maria") }
	{:method "varon" :args '("lucas") }
	{:method "varon" :args '("marcos") }
	{:method "mujer" :args '("ana") }
	{:method "padre" :args '("roberto","alejandro") }
	{:method "padre" :args '("roberto","cecilia") }
))

(def hashmapTest1
	{"varon" '( '("juan"))}
)
(deftest add-fact-element-test
    (testing "add first element"
        (is (= 
        		(add-fact-element-to-map {} (nth facts-array 0) )
            	hashmapTest1
            )
        )
    ) 
)

(def hashmapTest2
	{
		"varon" '( '("juan"))
		"mujer" '( '("maria"))
	}
)
(deftest add-fact-element-test
    (testing "add second element"
        (is (= 
        		(add-fact-element-to-map hashmapTest1 (nth facts-array 1) )
            	hashmapTest2
            )
        )
    ) 
)

(def hashmapTest3
	{
		"varon" '( '("juan") '("lucas"))
		"mujer" '( '("maria"))
	}
)
(deftest add-fact-element-test
    (testing "add third element"
        (is (= 
        		(add-fact-element-to-map hashmapTest2 (nth facts-array 2) )
            	hashmapTest3
            )
        )
    ) 
)

(def hashmapTest4
	{
		"varon" '( '("juan") '("lucas") '("marcos"))
		"mujer" '( '("maria"))
	}
)
(deftest add-fact-element-test
    (testing "add fourth element"
        (is (= 
        		(add-fact-element-to-map hashmapTest3 (nth facts-array 3) )
            	hashmapTest4
            )
        )
    ) 
)

(def hashmapTest5
	{
		"varon" '( '("juan") '("lucas") '("marcos"))
		"mujer" '( '("maria") '("ana"))
	}
)
(deftest add-fact-element-test
    (testing "add fifth element"
        (is (= 
        		(add-fact-element-to-map hashmapTest4 (nth facts-array 4) )
            	hashmapTest5
            )
        )
    ) 
)

(def hashmapTest6
	{
		"varon" '( '("juan") '("lucas") '("marcos"))
		"mujer" '( '("maria") '("ana"))
		"padre" '( '("roberto" "alejandro"))
	}
)
(deftest add-fact-element-test
    (testing "add sixth element"
        (is (= 
        		(add-fact-element-to-map hashmapTest5 (nth facts-array 5) )
            	hashmapTest6
            )
        )
    ) 
)

(def hashmapTest7
	{
		"varon" '( '("juan") '("lucas") '("marcos"))
		"mujer" '( '("maria") '("ana"))
		"padre" '( '("roberto" "alejandro") '("roberto" "cecilia"))
	}
)
(deftest add-fact-element-test
    (testing "add seventh element"
        (is (= 
        		(add-fact-element-to-map hashmapTest6 (nth facts-array 6) )
            	hashmapTest7
            )
        )
    ) 
)

(deftest facts-map-test
    (testing "generate facts map"
        (is (= 
        		(get-fact-map-from-fact-array facts-array)
            	hashmapTest7
            )
        )
    ) 
)