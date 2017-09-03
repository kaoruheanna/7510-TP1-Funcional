(ns logical-interpreter)

(defn parse-database
	"Receives the database string, parse it and return the relatios array"
	[database]
	(
		let [
			databaseArray (clojure.string/split-lines database) ; Separa el input en un array por cada linea
			databaseArray2 (map (fn [v] (clojure.string/replace v #"\." "")) databaseArray) ;Elimina el punto de cada elemento
		]
		(map (fn [v] (clojure.string/trim v)) databaseArray2) ; saca los espacios iniciales y finales
	)
)

(defn evaluate-query
	"Returns true if the rules and facts in database imply query, false if not. If
	either input can't be parsed, returns nil"
  	[database query]
	(
		let [relations (parse-database database)]
		; Chequea que la query este en el array
		; Si ningun elemento del array cumple la condicion, some devuelve nil
		(not (nil? (some (fn [v] (= v query)) relations)))
  	)
)
