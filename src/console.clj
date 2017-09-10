(ns console
	(:require 
		[logical-interpreter :refer :all]
	)
)

(defn read-file
	"Reads the file"
	[file]
	(slurp file)
)

(defn run-check
	"Process that executes the check"
	[args]
	(if (< (count args) 2)
		(println "paramentros invalidos")
		(
			let [
				database (read-file (first args))
				query (second args)
			]
			(println (evaluate-query database query) )	
		)
	)
)

(defn -main [& args]
	(run-check args)
)