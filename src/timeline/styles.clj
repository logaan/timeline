(ns timeline.styles
  (:require [gaka.core :as gaka])) 

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
