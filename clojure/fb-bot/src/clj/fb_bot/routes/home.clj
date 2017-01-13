(ns fb-bot.routes.home
  (:require [fb-bot.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [fb-bot.facebook :as fb]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

(defroutes fb-routes
  (POST "/webhook" request (fb/route-request request) {:status 200})
  (GET "/webhook" request (fb/webhook-is-valid? request)))
