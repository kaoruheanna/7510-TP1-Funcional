(ns logical-interpreter
	(:require [input-parser :refer :all])
)

(defn valid-relations
	"Checks if all the relations are valid"
	[relations]
	(every? (fn [v] (valid-relation-element v)) relations)
)

(defn get-facts-array
	" "
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

(defn generate-fact-map
	"Receives an array with the input strings array, filter to process only facts and generate the facts map"
	[inputsArray]
	(
		let [
			factsArray (get-facts-array (filter valid-fact inputsArray))
		]
		(get-fact-map-from-fact-array factsArray)
	)
)

(defn is-value-in-list
	"Receives a list and a value, and checks if the list contains the value"
	[haystack needle]
	(not (nil? (some (fn [v] (= v needle)) haystack)))
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
				]
				(evaluate-fact factMap query)
			)
		)
	)
)


; Cuando llega un fact, en tiempo de ejecucion reemplazar los argumentos
; y comprobar los facts.
; Armado un mapa de rules, donde dice que strings hay que reemplazar