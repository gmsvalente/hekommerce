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
                         coercion/coerce-response-middleware
                         coercion/coerce-request-middleware]}})
   (ring/routes
    (ring/create-default-handler))))

(defonce server (atom nil))

(defn -main [port]
  (reset! server
          (jetty/run-jetty #'routes
                           {:port (Integer/parseInt (or port "8080"))
                            :join? (or (:join-server env) false)})))

