(ns timeline.handler
  (:use compojure.core )
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [timeline
             [views :as views]
             [time :as time]
             [styles :as styles]]))

(defroutes app-routes
  (GET "/" []
    (views/contents (group-by-week time/each-day-of-each-month)
              styles/styles))

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
