(ns clojure-test.core
  (:require
    [clojure.tools.nrepl.server :as nrepl-server]
    [cider.nrepl :refer (cider-nrepl-handler)]
    [environ.core :refer [env]]
    [aleph.http :as http]
    [compojure
     [core :refer [defroutes ANY GET POST PUT]]
     [route :as route]]
    [ring.middleware
     [params :refer [wrap-params]]
     [flash :refer [wrap-flash]]
     [defaults :refer [site-defaults wrap-defaults]]]
    [taoensso.timbre :as timbre]
    [clojure-test.part2 :as myservice]
    )
  )
(timbre/refer-timbre)

(defroutes app
           "Application Routing URL"
           (POST "/transactionservice/transaction" ctx (myservice/transaction-service ctx))
           (GET "/transactionservice/types/:type" [type] (myservice/get-type type))
           (GET "/transactionservice/transaction/:transaction_id" [transaction_id] (myservice/get-from-id transaction_id))
           (GET "/transactionservice/sum/:transaction_id" [transaction_id] (myservice/get-sum transaction_id))
           (GET "/" [] "<h1>Hello World</h1>")
           (route/not-found "<h1>Page not found</h1>"))

(def handler
  (-> #'app
      wrap-flash
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)
            (dissoc :session)))
      wrap-params))

(def stop-http nil)

(defn start-http
  "Starting Application"
  []
  (alter-var-root
    #'stop-http
    (constantly
      (let [s (http/start-server handler {:port (read-string (env :http-port "9010"))})]
        (fn [] (.close s))))))

(defn -main
  "Entry Poin Application"
  [& args]
  (start-http)
  (clojure.tools.nrepl.server/start-server :port (read-string (env :repl-port "9011"))
                                           :handler cider-nrepl-handler
                                           :bind "0.0.0.0")
  (println "Running...!"))
