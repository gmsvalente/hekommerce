(ns hekommerce.backend.db
  (:require [xtdb.api :as xt]
            [environ.core :refer [env]]))

(def database-url
  (or (:jdbc-database-url env)
      "jdbc:postgresql://localhost:5432/userdb?password=password&user=user"))

(def node (xt/start-node
               {:xtdb.jdbc/connection-pool
                {:dialect {:xtdb/module 'xtdb.jdbc.psql/->dialect}
                 :db-spec {:jdbcUrl database-url}}
                :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log
                              :connection-pool :xtdb.jdbc/connection-pool}
                :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store
                                      :connection-pool :xtdb.jdbc/connection-pool}}))

