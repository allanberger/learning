(ns user
  (:require [mount.core :as mount]
            fb-bot.core))

(defn start []
  (mount/start-without #'fb-bot.core/http-server
                       #'fb-bot.core/repl-server))

(defn stop []
  (mount/stop-except #'fb-bot.core/http-server
                     #'fb-bot.core/repl-server))

(defn restart []
  (stop)
  (start))


