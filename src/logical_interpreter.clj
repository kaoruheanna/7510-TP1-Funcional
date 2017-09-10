(ns logical-interpreter
	(:require [input-parser :refer :all])
)

(defn valid-relations
	"Checks if all the relations are valid"
	[relations]
	(every? (fn [v] (valid-relation-element v)) relations)
)

(defn is-value-in-list
	"Receives a list and a value, and checks if the list contains the value"
	[haystack needle]
	(not (nil? (some (fn [v] (= v needle)) haystack)))
)

; --------- FACTS -------------

(defn get-facts-array
	"Receives the array with all the facts strings, and returns an array containting the facts hashmaps"
	[factsStringsArray]
	(map split-fact factsStringsArray)
)

(defn add-fact-element-to-map
	"Receives the fact map and a fact element. Adds the fact and returns the new fact map"
	[factMap factElement]
	(
		let [
			methodName (:method factElement)
			argsElement (:args factElement)
			inputListArgs (list argsElement)
		]
		(if (contains? factMap methodName)
			(
				let [
					concatedArgs (concat (get factMap methodName) inputListArgs)
				]
				(merge factMap { methodName concatedArgs })				
			)
			(merge factMap { methodName inputListArgs })
		)
	)
)

(defn get-fact-map-from-fact-array
	"Receives the facts array, and return the fact map"
	[factsArray]
	(reduce add-fact-element-to-map {} factsArray)
)

(defn generate-fact-map
	"Receives an array with the input strings array, filter to process only facts and generates the facts map"
	[inputsArray]
	(
		let [
			factsArray (get-facts-array (filter valid-fact inputsArray))
		]
		(get-fact-map-from-fact-array factsArray)
	)
)

(defn evaluate-fact
	"Check if the received fact is present in the fact map"
	[factMap fact]
	(
		let [
			splittedFact (split-fact fact)
			methodName (:method splittedFact)
		]
		(if (contains? factMap methodName)
			(is-value-in-list (get factMap methodName) (:args splittedFact)) ;me fijo si el argumento esta dentro de todos los posibles valores
			false ; Si el metodo ni siquiera esta dentro de las keys es false
		)
	)
)
; --------- END FACTS -------------

; --------- RULES -------------
(defn get-rules-array
	"Receives the array with all the rules strings, and returns an array containting the Rule records"
	[rulesStringsArray]
	(map split-rule rulesStringsArray)
)

(defn add-rule-element-to-map
	"Receives the rule map and a rule element. Adds the rule and returns the new rule map"
	[ruleMap ruleElement]
	(merge ruleMap { (:rname ruleElement) ruleElement })
)


(defn get-rule-map-from-rule-array
	"Receives the rules array, and return the rule map"
	[rulesArray]
	(reduce add-rule-element-to-map {} rulesArray)
)

(defn generate-rule-map
	"Receives an array with the input strings array, filter to process only rules and generates the rule map"
	[inputsArray]
	(
		let [
			rulesArray (get-rules-array (filter valid-rule inputsArray))
		]
		(get-rule-map-from-rule-array rulesArray)
	)
)

(defn replace-args-in-fact
	"Receives the fact string, and the args translation list, and the returns the fact with the args replaced"
	[fact argsTranslation]
	(reduce 
		(fn [text args] 
			(clojure.string/replace text (first args) (second args))
		)
		fact
		argsTranslation
	)
)

(defn check-facts-for-rule
	""
	[rule factMap queryRule]
	(
		let [
			args (map list (:args rule) (:args queryRule))
		]	
		(println args)
	)
	true
)

(defn evaluate-rule
	"Evaluates if the received fact is true"
	[ruleMap factMap query]
	(
		let [
			queryRule (split-rule-query query)
			ruleName (:rname queryRule)
		]
		(if (contains? ruleMap ruleName)
			(check-facts-for-rule (get ruleMap ruleName) factMap queryRule)
			false
		)
	)
)

; --------- END RULES -------------

(defn parse-database-string
	"Receives the database string, parse it and return a relations strings array"
	[database]
	(
		let [
			databaseArray (clojure.string/split-lines database) ; Separa el input en un array por cada linea
			databaseArray2 (map (fn [v] (clojure.string/replace v #"\." "")) databaseArray) ;Elimina el punto de cada elemento
			databaseArray3 (map (fn [v] (clojure.string/trim v)) databaseArray2) ; saca los espacios iniciales y finales
			databaseArray4 (filter (fn [x] (not= x "")) databaseArray3) ; Elimina elementos vacios
		]
		databaseArray4
	)
)

(defn evaluate-query
	"Returns true if the rules and facts in database imply query, false if not. If
	either input can't be parsed, returns nil"
  	[database query]
	(
		let [
			inputStringsArray (parse-database-string database)
		]
		(if (false? (valid-relations inputStringsArray))
			nil
			(
				let [
					factMap (generate-fact-map inputStringsArray)
					ruleMap (generate-rule-map inputStringsArray)
				]
				(or 
					(evaluate-fact factMap query)
					(evaluate-rule ruleMap factMap query)
				)
			)
		)
	)
)


; Cuando llega un fact, en tiempo de ejecucion reemplazar los argumentos
; y comprobar los facts.
; Armado un mapa de rules, donde dice que strings hay que reemplazar