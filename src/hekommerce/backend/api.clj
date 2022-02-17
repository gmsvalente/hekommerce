(ns hekommerce.backend.api
  (:require [xtdb.api :as xt]
            [clojure.spec.alpha :as s]
            [hekommerce.common.spec :as model]
            [hekommerce.backend.db :refer [node]]))

(defn get-users []
  (let [db (xt/db node)
        users (xt/q db '{:find [n]
                         :where [[_ :user/name n]]})]
    (reduce into [] users)))


(comment 

  (xt/submit-tx node [[::xt/put {:xt/id 0
                                 :user 'joao}]
                      [::xt/put {:xt/id 1
                                 :user 'jose}]])

  (xt/q (xt/db node) '{:find [user]
                       :where [[e :xt/id 1]
                               [e :user user]]}))
