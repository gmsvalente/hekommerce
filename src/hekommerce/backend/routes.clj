(ns hekommerce.backend.routes
  (:require [hekommerce.backend.handlers :as h]
            [reitit.ring :as ring]
            [hekommerce.common.spec :as model]))


(def root-route
  ["/" {:name ::land-page
        :handler #'h/page-handler}])

(def api-routes
  [["/api"
    ["/users" {:name ::all-users
               :get {:handler #'h/all-users}}]
    ["/user" {:name ::post-user
              :post {:handler #'h/create-user
                     :parameters {:body ::model/user}}}]
    ["/user/:user" {:name ::get-user
                    :get {:handler #'h/get-user}}]]])

(def public-route
  ["/public/*" (ring/create-file-handler)])

