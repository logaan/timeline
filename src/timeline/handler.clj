(ns timeline.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [gaka.core :as gaka]))

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

(def box-size 15)

(defn px [n] (str n "px"))

(def styles
  (gaka/css
    [:div.timeline
     :border "1px solid black"
     :display "inline-block"
     [:div
       :display "inline-block"
       :padding "0px"
       :margin  "0px"]
     [:div.week
      :width   (px box-size) 
      :height  (px (* 7 (+ box-size 2))) 
      [:div.day
       :border "1px solid #EEE"
       :width  (px box-size) 
       :height (px box-size)]
      [:div.day.last-week
       :border-right "1px solid black"]    
      [:div.day.empty 
       :border "1px solid white"]    
      [:div.day.last-day
       :border-bottom "1px solid black"]]]))

(defn day [day]
  (if day
    [:div {:class
           (str "day"
                (if (last-week-of-month day) " last-week")
                (if (last-day-of-month day)  " last-day"))}]
    [:div.day.empty]))

(defn week [week]
  [:div.week (map day week)])

(defn contents []
  (hiccup/html
    [:html
     [:head
      [:style styles]]
     [:body
      [:h1 "Timeline"]
      [:div.timeline
       [:div.year
        (map week (group-by-week each-day-of-each-month))]]]])) 

(defroutes app-routes
  (GET "/" [] (contents))

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
