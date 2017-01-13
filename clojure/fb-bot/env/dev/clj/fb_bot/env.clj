(ns fb-bot.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [fb-bot.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[fb-bot started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[fb-bot has shut down successfully]=-"))
   :middleware wrap-dev})
