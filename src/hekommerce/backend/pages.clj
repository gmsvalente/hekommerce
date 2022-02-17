(ns hekommerce.backend.pages
  (:require [hiccup.page :as hp ]))

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
