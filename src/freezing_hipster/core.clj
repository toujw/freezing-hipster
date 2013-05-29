(ns freezing-hipster.core
  (:use [ororo.core :only [history]]
        [clojure-csv.core :only [write-csv]])
  (:import [org.joda.time DateTime DateTimeComparator]
           [org.joda.time.format DateTimeFormat])
  (:gen-class))

(defn- make-csv-row
  "Creates a CSV row suitable for output from the raw history data."
  [raw-hist]
  (let [{:keys [date maxhumidity minhumidity maxtempi mintempi meanpressurei]}
        (first (:dailysummary raw-hist))]
    [(str (:mon date) "/" (:mday date) "/" (:year date))
     maxhumidity
     minhumidity
     maxtempi
     mintempi
     meanpressurei]))

(defn- print-usage
  "Prints application usage."
  []
  (println "Usage: java -jar freezing-hipster.jar KEY LOCATION FROM-DATE TO-DATE OUT-FILE"))

(defn- date-seq
  "Returns a sequence of joda time objects between from and to (inclusive)."
  [from to]
  (let [cmp (DateTimeComparator/getDateOnlyInstance)]
    (if (> (.compare cmp from to) 0)
      (throw (IllegalArgumentException. "The from date must be earlier than the to date."))
      (take-while #(<= (.compare cmp % to) 0)
                  (iterate #(.plusDays ^DateTime % 1) from)))))

(defn -main
  "Gets history data and writes it to a CSV file."
  [& args]
  (try
    (if (not= (count args) 5)
      (print-usage)
      (let [[key loc from to file] args
            formatter (DateTimeFormat/forPattern "YYYYMMdd")
            [from to] [(.parseDateTime formatter from) (.parseDateTime formatter to)]
            rows (->> (date-seq from to)
                      (map #(.toString ^DateTime % "YYYYMMdd"))
                      (map #(history key loc %))
                      (map make-csv-row))]
        (spit file (write-csv rows))))
    (catch IllegalArgumentException e
      (println (.getMessage e)))))
