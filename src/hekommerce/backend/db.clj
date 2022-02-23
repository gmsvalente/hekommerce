(ns hekommerce.backend.db
  (:require [xtdb.api :as xt]))

(def node (xt/start-node
           {:xtdb.jdbc/connection-pool
            {:dialect {:xtdb/module 'xtdb.jdbc.psql/->dialect}
             :db-spec {:host "192.168.0.21"
                       :port "5432"
                       :dbname "userdb"
                       :user "user"
                       :password "password"}}
            :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log
                          :connection-pool :xtdb.jdbc/connection-pool}
            :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store
                                  :connection-pool :xtdb.jdbc/connection-pool}}))

