(ns hekommerce.frontend.events
  (:require [re-frame.core :as rf]
            [hekommerce.frontend.theme :refer [custom-theme]]))


(rf/reg-event-db
 ::init-db
 (fn [_]
   {:theme custom-theme}))

