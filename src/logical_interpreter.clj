(ns logical-interpreter)

; (def FACT-PATTERN #"^[a-zA-Z]*\([a-zA-Z,\ ]*\)")
; (def RULE-PATTERN #"^.* :- .*$")

; (defn valid-pattern
; 	"Checks if the input has a valid pattern"
; 	[input pattern]
; 	(not (nil? (re-matches pattern input)))
; )

; (defn valid-fact
; 	"Checks if the input has a valid fact pattern"
; 	[input]
; 	(valid-pattern input FACT-PATTERN)
; )

; (defn valid-rule
; 	"Checks if the input has a valid fact pattern"
; 	[input]
; 	(valid-pattern input RULE-PATTERN)
; )

; (defn valid-relation-element
; 	"Checks if the input is a valid rule or a valid fact"
; 	[input]
; 	(or (valid-fact input) (valid-rule input))
; )


(defn valid-relations
	"Checks if all the relations are valid"
	[relations]
	(every? (fn [v] (valid-relation-element v)) relations)
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
		]
		databaseArray4
	)
)

; (defn split-fact
; 	"Receives a string, and returns the fact name"
; 	[input]
; 	(re-find #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\)" input)
; )

(defn evaluate-query
	"Returns true if the rules and facts in database imply query, false if not. If
	either input can't be parsed, returns nil"
  	[database query]
	(
		let [relations (parse-database database)]
		(println (split-fact query))
		(if (false? (valid-relations relations))
			nil
			(not (nil? (some (fn [v] (= v query)) relations)))
		)
		; Chequea que la query este en el array
		; Si ningun elemento del array cumple la condicion, some devuelve nil
	)
)
