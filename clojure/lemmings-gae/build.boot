(set-env!
 :gae {:app-id "aeb-clojure"
       :version "stable"
       :module {:name "default"}}
 :build-dir "target"
 :asset-paths #{"resources/public"}

 ; clojure needs to have the source availble? otherwise Could not locate greetings/hello__init.class or greetings/hello.clj on classpath.
 :resource-paths #{"src"}

 :source-paths #{"config"}

 :repositories {"maven-central" "http://mvnrepository.com"
                "central" "http://repo1.maven.org/maven2/"
                "clojars" "https://clojars.org/repo"}

 :dependencies   '[[org.clojure/clojure "1.8.0" :scope "runtime"]
                   [org.clojure/tools.logging "0.3.1"]

                   [migae/boot-gae "0.1.1-SNAPSHOT" :scope "test"]

                   [javax.servlet/servlet-api "2.5" :scope "provided"]

                   ;; this is for the GAE runtime (NB: scope provided):
                   [com.google.appengine/appengine-java-sdk RELEASE :scope "provided" :extension "zip"]

                   ;; ;; this is required for gae appstats (NB: scope runtime, not provided?):
                   [com.google.appengine/appengine-api-labs RELEASE :scope "runtime"]

                   ;; this is for the GAE services like datastore (NB: scope runtime):
                   ;; (required for appstats, which uses memcache)
                   [com.google.appengine/appengine-api-1.0-sdk RELEASE :scope "runtime"]

                   ;; [org.mobileink/migae.datastore "0.3.3-SNAPSHOT" :scope "runtime"]

                   [hiccup/hiccup "1.0.5"]
                   [cheshire/cheshire "5.7.0"]
                   [compojure/compojure "1.5.2"]
                   [ring/ring-core "1.5.1"]
                   [ring/ring-devel "1.5.1" :scope "test"]
                   [ring/ring-servlet "1.5.1"]
                   [ring/ring-defaults "0.2.1"]
                   [ns-tracker/ns-tracker "0.3.0"]])


(require '[migae.boot-gae :as gae]
         '[boot.task.built-in :as builtin])

(def web-inf-dir "WEB-INF")
(def classes-dir (str web-inf-dir "/classes"))

;; copied from gae/build:
(deftask build
  "assemble, configure, and build app"
  [k keep bool "keep intermediate .clj and .edn files"
   p prod bool "production build, without reloader"
   v verbose bool "verbose"]
  (comp (gae/install-sdk :verbose verbose)
        (gae/libs :verbose verbose)
        (gae/logging :verbose verbose)
        (builtin/javac :options ["-target" "1.7"])
        (if prod identity (gae/reloader :keep keep :verbose verbose))
        (gae/servlets :keep keep :verbose verbose)
        (gae/webxml :verbose verbose)
        (gae/appengine :verbose verbose)
        (builtin/sift :move {#"(.*clj$)" (str classes-dir "/$1")})
        (builtin/sift :move {#"(.*\.class$)" (str classes-dir "/$1")})
        (gae/target)))

(deftask run
  "run locally"
  []
  (comp (build :prod false)
        (gae/run :http-address "0.0.0.0")))

(deftask monitor
  "watch etc. for gae project"
  []
  (comp (builtin/watch)
        (builtin/sift :move {#"(.*\.clj$)" (str classes-dir "/$1")})
        (target :no-clean true :service false :monitor false)))
