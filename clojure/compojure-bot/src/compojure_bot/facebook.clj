(ns compojure-bot.facebook
  (:gen-class)
  (:require [clojure.string :as s]
            [compojure-bot.messages :as msg]))

(defn webhook-is-valid? [request]
  (let [params (:params request)]
    (println "Incoming Webhook Request:")
    (println request)
    (if (= true (= (params "hub.mode") "subscribe")
          (= (params "hub.verify_token") (System/getenv "FB_PAGE_ACCESS_TOKEN")))
      {:status 200 :body (params "hub.challenge")}
      {:status 403})))

(defn onMessage [event]
  (let [senderID (get-in event [:sender :id]) recipientID (get-in event [:recipient :id]) timeOfMessage (get-in event [:timestamp]) message (get-in event [:message])]
    (println (str "Received message for user " senderID " and page " recipientID " at " timeOfMessage " with message:"))
    (println message)
    ; Add rules here
    (cond
      (s/includes? (s/lower-case (:text message)) "help") [senderID "Hi there, happy to help :)"]
      (s/includes? (s/lower-case (:text message)) "image") [senderID "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/M101_hires_STScI-PRC2006-10a.jpg/1280px-M101_hires_STScI-PRC2006-10a.jpg"]
      :else [senderID (:text message)])))

(defn route-request [request]
  (let [data (get-in request [:params])]
    (println "Incoming Request:")
    (println request)
    (when (= (:object data) "page")
      (doseq [pageEntry (:entry data)]
        (doseq [messagingEvent (:messaging pageEntry)]
          ; Check for text or attachments here
          (cond (contains? messagingEvent :message) (msg/sendTextMessage (onMessage messagingEvent))
            :else (println (str "Webhook received unknown messagingEvent: " messagingEvent))))))))
