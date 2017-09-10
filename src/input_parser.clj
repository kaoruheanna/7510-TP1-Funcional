(ns input-parser
	(:require [rule-record :refer :all])
)

(def FACT-PATTERN #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\)")
(def RULE-PATTERN #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\) :- (([a-zA-Z]*)\(([a-zA-Z,\ ]*)\), )*([a-zA-Z]*)\(([a-zA-Z,\ ]*)\)")
(def RULE-START-PATTERN #"^([a-zA-Z]*)\(([a-zA-Z,\ ]*)\) :- ")
(def RULE-FACT-PATTERN #"([a-zA-Z]*\([a-zA-Z,\ ]*\))")

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
	"Receives a string like '(one, one, two)' and returns an array (one one two)"
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
	"Receives a string and returns a hash map { method: methodName, args: [arg1 arg2 ...]"
	[input]
	(splitted-hash (re-find FACT-PATTERN input))
)

(defn get-facts-from-rule-string
	"Receives the rule string and returns the facts"
	[input]
	(
		let [
			factsString (clojure.string/replace input RULE-START-PATTERN "")
			matchesSeq (re-seq RULE-FACT-PATTERN factsString) ;returns ([varon(X) varon(X)] [padre(Y, X) padre(Y, X)])
		]
		(map (fn[v] (nth v 0)) matchesSeq)
	)
)

(defn split-rule
	"Receives a string and returns a rule record"
	[input]
	(
		let [
			splitted-hash (re-find RULE-PATTERN input)
			ruleName (nth splitted-hash 1)
			args (get-args-array (nth splitted-hash 2))
			facts (get-facts-from-rule-string input)
		]
		(new-rule ruleName args facts)
	)
)

(defn split-rule-query
	"Receives a rule query string and returns a rule record with empty facts"
	[input]
	(
		let [
			splitted (splitted-hash (re-find FACT-PATTERN input)) ;el query de rule tiene el mismo formato que un fact
			ruleName (:method splitted)
			args (:args splitted)
			facts nil
		]
		(new-rule ruleName args facts)
	)
)