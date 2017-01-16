(ns compojure-bot.facebook
  (:gen-class)
  (:require [org.httpkit.client :as http]
            [clojure.pprint :as pprint]
            [clojure.data.json :as json]))

(defn webhook-is-valid? [request]
  (def params (:params request))
  (println "Incoming Webhook Request:")
  (println request)
  (if (= true (= (params "hub.mode") "subscribe")
        (= (params "hub.verify_token") (System/getenv "FB_PAGE_ACCESS_TOKEN")))
    {:status 200 :body (params "hub.challenge")}
    {:status 403}))

(defn sendAPI [messageData]
    (try
        (def response (http/post "https://graph.facebook.com/v2.6/me/messages"
                        {:query-params {"access_token" (System/getenv "FB_PAGE_ACCESS_TOKEN")}
                         :headers {"Content-Type" "application/json"}
                         :body (json/write-str messageData)
                         :insecure? true}))
        (println "Response to FB:")
        (println @response)
        (catch Exception e (str "caught exception: " (.getMessage e)))))

(defn sendTextMessage [[recipientId messageText]]
    (def messageData {:recipient {:id recipientId} :message {:text messageText}})
    (println messageData)
    (sendAPI messageData))

(defn onMessage [event]
  (let [senderID (get-in event [:sender :id]) recipientID (get-in event [:recipient :id]) timeOfMessage (get-in event [:timestamp]) message (get-in event [:message])]
    (println (str "Received message for user " senderID " and page " recipientID " at " timeOfMessage " with message:"))
    (println message)
    [senderID (:text message)]))

(defn route-request [request]
  (def data (get-in request [:params]))
  (println "Incoming Request:")
  (println request)
  (if (= (:object data) "page")
    (doseq [pageEntry (:entry data)]
      (doseq [messagingEvent (:messaging pageEntry)]
        (cond (contains? messagingEvent :message) (sendTextMessage (onMessage messagingEvent))
          :else (println (str "Webhook received unknown messagingEvent: " messagingEvent)))))))
