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
