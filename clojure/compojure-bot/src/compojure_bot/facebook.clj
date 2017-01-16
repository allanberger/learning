(ns compojure-bot.facebook
  (:gen-class)
  (:require [org.httpkit.client :as http]
            [clojure.pprint :as pprint]
            [clojure.data.json :as json]
            [clojure.string :as s]))

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

(defn sendImageMessage [[recipientId imageUrl]]
    (def messageData {:recipient {:id recipientId} :message {:attachment {:type "image" :payload {:url imageUrl}}}})
    (println messageData)
    (sendAPI messageData))

(defn onMessage [event]
  (let [senderID (get-in event [:sender :id]) recipientID (get-in event [:recipient :id]) timeOfMessage (get-in event [:timestamp]) message (get-in event [:message])]
    (println (str "Received message for user " senderID " and page " recipientID " at " timeOfMessage " with message:"))
    (println message)
    (println (:text message))
    ; Add rules here
    (cond
      (s/includes? (s/lower-case (:text message)) "help") (sendTextMessage [senderID "Hi there, happy to help :)"])
      (s/includes? (s/lower-case (:text message)) "image") (sendImageMessage [senderID "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/M101_hires_STScI-PRC2006-10a.jpg/1280px-M101_hires_STScI-PRC2006-10a.jpg"])
      :else (sendTextMessage [senderID (:text message)]))))

(defn route-request [request]
  (def data (get-in request [:params]))
  (println "Incoming Request:")
  (println request)
  (if (= (:object data) "page")
    (doseq [pageEntry (:entry data)]
      (doseq [messagingEvent (:messaging pageEntry)]
        ; Check for text or attachments here
        (cond (contains? messagingEvent :message) (onMessage messagingEvent)
          :else (println (str "Webhook received unknown messagingEvent: " messagingEvent)))))))
