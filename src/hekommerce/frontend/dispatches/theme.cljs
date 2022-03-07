(ns hekommerce.frontend.dispatches.theme
  (:require [re-frame.core :as rf ]))


(defn change-mode [mode]
  (condp = mode
    "light" "dark"
    "dark" "light"))

;; toggle the theme mode dark/light
(rf/reg-event-fx
 ::toggle-theme-mode
 (fn [{:keys [db]} _]
   {:db (update-in db [:theme :palette :mode] change-mode )}))
