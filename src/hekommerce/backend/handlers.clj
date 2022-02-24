(ns hekommerce.backend.handlers
  (:require [hekommerce.backend.pages :refer [land-page]]
            [hekommerce.backend.api :as api]))

(defn page-handler [req]
  {:status 200
   :body (land-page)})

(defn all-users [req]
  (let [users (api/get-users)]
    {:status 200
     :body {:users users}}))

(defn get-user [req]
  (let [user (-> req :path-params :user)]
    {:status 200
     :body {:user (ffirst (api/get-user user))}}))

(defn create-user [req]
  (let [user (-> req :parameters :body)
        response (api/post-user user)]
    (if (:error response)
      {:status 500
       :body response}
      {:status 201
       :body {:user response}})))
