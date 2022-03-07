(ns hekommerce.frontend.dispatches.ui
  (:require [re-frame.core :as rf]))


;;;;; menu drawer dispatches

;; toggle the drawer menu on/off

(rf/reg-event-db
 ::toggle-drawer
 (fn [db _]
   (update-in db [:drawer :is-open?] not)))

(rf/reg-event-db
 ::set-drawer
 (fn [db [_ state]]
   (assoc-in db [:drawer :is-open?] state)))



;; toggle the login slide on drawer menu

(rf/reg-event-db
 ::toggle-login-slide
 (fn [db _]
   (update-in db [:login-form :slide] not)))

(rf/reg-event-db
 ::set-login-slide
 (fn [db [_ state]]
   (assoc-in db [:login-form :slide] state)))
