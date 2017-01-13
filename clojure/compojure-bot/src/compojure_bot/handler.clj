(ns compojure-bot.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [compojure-bot.facebook :as fb]))

(defroutes fb-routes
  (POST "/webhook" request (fb/route-request request) {:status 200})
  (GET "/webhook" request (fb/webhook-is-valid? request)))

(def app
  (-> fb-routes
      (wrap-keyword-params)
      (wrap-json-params)))
