(ns hekommerce.frontend.events
  (:require [re-frame.core :as rf]
            [hekommerce.frontend.theme :refer [custom-theme]]))


(rf/reg-event-db
 ::init-db
 (fn [_]
   {:theme custom-theme}))

(defn change-mode [mode]
  (condp = mode
    "light" "dark"
    "dark" "light"))

(rf/reg-event-fx
 ::toggle-theme-mode
 (fn [cofx _]
   {:db (update-in (:db cofx) [:theme :palette :mode] change-mode )}))
