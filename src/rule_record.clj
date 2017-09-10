(ns rule-record)

(defrecord Rule [rname args facts])

(defn new-rule [rname args facts]
	(new Rule rname args facts)
)