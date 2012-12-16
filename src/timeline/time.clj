(ns timeline.time)

(def days-in-month
  (array-map
    :january   31
    :february  28
    :march     31
    :april     30
    :may       31
    :june      30
    :july      31
    :august    31
    :september 30
    :october   31
    :november  30
    :december  31))

; Range is full retarded
(def each-day-of-each-month
  (for [[month days] days-in-month
        day          (range 1 (inc days))]
    {:day-of-month day :month month}))

(defn group-by-week [days]
  (partition 7 7 (repeat nil) days))

(defn last-week-of-month [{:keys [day-of-month month]}]
  (-> (days-in-month month) (- day-of-month) (< 7)))

(defn last-day-of-month [{:keys [day-of-month month]}]
  (= (days-in-month month) day-of-month))
