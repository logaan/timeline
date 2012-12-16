(ns timeline.views
  (:require [hiccup.core :as hiccup])) 

(defn day [day]
  (if day
    [:div {:class
           (str "day"
                (if (last-week-of-month day) " last-week")
                (if (last-day-of-month day)  " last-day"))}]
    [:div.day.empty]))

(defn week [week]
  [:div.week (map day week)])

(defn contents [weeks styles]
  (hiccup/html
    [:html
     [:head
      [:style styles]]
     [:body
      [:h1 "Timeline"]
      [:div.timeline
       [:div.year
        (map week weeks)]]]])) 
