(ns hekommerce.backend.api
  (:require [xtdb.api :as xt]
            [clojure.spec.alpha :as s]
            [hekommerce.common.spec :as cs]
            [hekommerce.backend.db :refer [node]]))

(comment 
  (def joao
    (s/conform ::cs/user {:user/name "joao"
                          :user/id (java.util.UUID/randomUUID)}))

  (def jose
    (s/conform ::cs/user {:user/name "jose"
                          :user/id (java.util.UUID/randomUUID)}))

  (xt/submit-tx node [[::xt/put {:xt/id 0
                                 :user joao}]
                      [::xt/put {:xt/id 1
                                 :user jose}]])

  (xt/q (xt/db node) '{:find [user]
                       :where [[e :xt/id 1]
                               [e :user user]]}))
