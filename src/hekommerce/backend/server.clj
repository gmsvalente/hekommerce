(ns hekommerce.backend.server
  (:require [environ.core :refer [env]]
            [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [hiccup.page :as hp]))

(defn land-page []
  (hp/html5
   [:head
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:meta {:charset "utf8"}]
    [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
    [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin "true"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Orbitron&display=swap"}]]
   [:body 
    [:div#root]
    [:script {:src "/public/js/main.js"
              :type "text/javascript"}]]))

(defn page-handler [req]
  {:status 200
   :body (land-page)})

(def routes
  (ring/ring-handler
   (ring/router
    [["/" {:get {:handler page-handler}}]
     ["/public/*" (ring/create-file-handler)]])
   (ring/create-default-handler)))

(def server (atom nil))

(defn -main [port]
  (reset! server
          (jetty/run-jetty #'routes {:port (Integer/parseInt (or port "8080"))
                                     :join? (or (:join-server env) false)})))

