(ns compojure-bot.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.util.response :refer [response]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure-bot.facebook :as fb]))

(defroutes fb-routes
  (POST "/webhook" request (fb/route-request request) {:status 200})
  (GET "/webhook" request (fb/webhook-is-valid? request)))

(def app
  (-> fb-routes
      (wrap-json-body {:keywords? true})
      (wrap-params)))
