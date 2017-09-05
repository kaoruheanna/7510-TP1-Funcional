(ns input-parser)

(def FACT-PATTERN #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\)")
(def RULE-PATTERN #"^.* :- .*$")

(defn valid-pattern
	"Checks if the input has a valid pattern"
	[input pattern]
	(not (nil? (re-matches pattern input)))
)

(defn valid-fact
	"Checks if the input has a valid fact pattern"
	[input]
	(valid-pattern input FACT-PATTERN)
)

(defn valid-rule
	"Checks if the input has a valid fact pattern"
	[input]
	(valid-pattern input RULE-PATTERN)
)

(defn valid-relation-element
	"Checks if the input is a valid rule or a valid fact"
	[input]
	(or (valid-fact input) (valid-rule input))
)

(defn get-args-array
	""
	[input]
	(map (fn [v] (clojure.string/trim v) ) (clojure.string/split input #",") )
)

(defn splitted-hash
	"Receives the result of the re-find and returns a hash map { method: methodName, args: [arg1 arg2 ...]"
	[input]
	{
		:method (nth input 1)
		:args (get-args-array (nth input 2))
	}
)

(defn split-fact
	"Receives a string and a hash map { method: methodName, args: [arg1 arg2 ...]"
	[input]
	(splitted-hash (re-find #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\)" input))
)