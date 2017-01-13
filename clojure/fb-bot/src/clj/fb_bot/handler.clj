(ns fb-bot.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [fb-bot.layout :refer [error-page]]
            [fb-bot.routes.home :refer [home-routes]]
            [fb-bot.routes.home :refer [fb-routes]]
            [compojure.route :as route]
            [fb-bot.env :refer [defaults]]
            [mount.core :as mount]
            [fb-bot.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'fb-routes
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(defn app [] (middleware/wrap-base #'app-routes))
