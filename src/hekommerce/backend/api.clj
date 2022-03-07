(ns hekommerce.backend.api
  (:require [xtdb.api :as xt]
            [clojure.spec.alpha :as s]
            [hekommerce.common.spec :as model]
            [hekommerce.backend.db :refer [node]]))

(defn get-users []
  (let [db (xt/db node)
        users (xt/q db '{:find [n]
                         :where [[_ :user/login n]]})]
    (reduce into [] users)))


(defn get-user [user]
  (let [db (xt/db node)]
    (def u user)
    (xt/q db '{:find [(pull login [*])]
               :where [[login :user/login user]]
               :in [user]}
          user)))

(defn post-user [user]
  (let [tx-id (:user/login user)
        content (merge {:xt/id tx-id} user)
        response
        (try
          (xt/submit-tx node [[::xt/put content]])
          (catch xtdb.IllegalArgumentException xe
            {:error (.. xe getMessage)
             :tx-id tx-id
             :content content}))]
    (if (:error response)
      response
      content)))


(comment 
  (xt/submit-tx node [[::xt/put {:xt/id 0
                                 :user 'joao}]
                      [::xt/put {:xt/id 1
                                 :user 'jose}]])

  (xt/q (xt/db node) '{:find [user]
                       :where [[e :xt/id 1]
                               [e :user user]]}))
