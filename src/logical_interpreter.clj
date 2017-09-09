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

		; (println factElement)
		; (println (str "metodo:" methodName))
		; (println (str "args:" argsElement))
		; (println factMap)
		; (println (type methodName))
		; (println (type :varon))
		; (println (get factMap methodName))

		; (println (contains? {"varon" "kao"} lala))
		; (println (get {"varon" "kao"} lala))
		; (if (contains? factMap methodName)
		; 	(println "lo tiene")
		; 	(println "no lo tiene")
		; )
		; {"varon" '( '("juan"))}
	)
)

(defn parse-database
	"Receives the database string, parse it and return the relatios array"
	[database]
	(
		let [
			databaseArray (clojure.string/split-lines database) ; Separa el input en un array por cada linea
			databaseArray2 (map (fn [v] (clojure.string/replace v #"\." "")) databaseArray) ;Elimina el punto de cada elemento
			databaseArray3 (map (fn [v] (clojure.string/trim v)) databaseArray2) ; saca los espacios iniciales y finales
			databaseArray4 (filter (fn [x] (not= x "")) databaseArray3) ; Elimina elementos vacios
			factsArray (get-facts-array databaseArray4)
		]
		(reduce add-fact-element-to-map {} factsArray)
	)
)


(defn evaluate-query
	"Returns true if the rules and facts in database imply query, false if not. If
	either input can't be parsed, returns nil"
  	[database query]
	(
		let [relations (parse-database database)]
		(println (:varon relations))
		true
		; (if (false? (valid-relations relations))
		; 	nil
		; 	(not (nil? (some (fn [v] (= v query)) relations)))
		; )
		; Chequea que la query este en el array
		; Si ningun elemento del array cumple la condicion, some devuelve nil
	)
)


; Cuando llega un fact, en tiempo de ejecucion reemplazar los argumentos
; y comprobar los facts.
; Armado un mapa de rules, donde dice que strings hay que reemplazar