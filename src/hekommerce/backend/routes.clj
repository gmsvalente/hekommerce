(ns hekommerce.backend.routes
  (:require [hekommerce.backend.handlers :as h]
            [reitit.ring :as ring]))


(def root-route
  ["/" {:name ::land-page
        :handler #'h/page-handler}])

(def api-routes
  [["/api"
    ["/users" {:name ::all-users
               :get {:handler #'h/all-users}}]]])

(def public-route
  ["/public/*" (ring/create-file-handler)])

