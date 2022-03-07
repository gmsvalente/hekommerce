(ns hekommerce.backend.server
  (:require [environ.core :refer [env]]
            [ring.adapter.jetty :as jetty]
            [reitit.coercion.spec :as rcs]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [muuntaja.core :as m]
            [hekommerce.backend.routes :refer
             [root-route public-route api-routes]]))


(def routes
  (ring/ring-handler
   (ring/router
    [root-route
     public-route
     api-routes]
    {:data {:coercion rcs/coercion
            :muuntaja m/instance
            :middleware [muuntaja/format-negotiate-middleware
                         muuntaja/format-response-middleware
                         exception/exception-middleware
                         muuntaja/format-request-middleware
                         coercion/coerce-response-middleware
                         coercion/coerce-request-middleware]}})
   (ring/routes
    (ring/create-default-handler
     {:not-found (fn [_]
                   {:status 404
                    :body "Sorry!! 404"})
      :method-not-allowed (fn [_]
                            {:status 405
                             :body "Method not allowed!! 405"})}))))

(defonce server (atom nil))

(defn -main [port]
  (reset! server
          (jetty/run-jetty #'routes
                           {:port (Integer/parseInt (or port "8080"))
                            :host "192.168.1.103"
                            :join? (or (:join-server env) false)})))

