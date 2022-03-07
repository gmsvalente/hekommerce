(ns hekommerce.frontend.dispatches.ui
  (:require [re-frame.core :as rf]))


(rf/reg-event-db
 ::toggle-drawer
 (fn [db _]
   (update-in db [:drawer :is-open?] not)))

(rf/reg-event-db
 ::set-drawer
 (fn [db [_ state]]
   (assoc-in db [:drawer :is-open?] state)))


(rf/reg-event-db
 ::toggle-login-slide
 (fn [db _]
   (update-in db [:login-form :slide] not)))

(rf/reg-event-db
 ::set-login-slide
 (fn [db [_ state]]
   (assoc-in db [:login-form :slide] state)))


(rf/reg-event-db
 ::set-login-loading
 (fn [db [_ new-state]]
   (assoc-in db [:login-form :loading?] new-state)))


(rf/reg-event-fx
 ::open-user-subscribe-alert
 (fn [{:keys [db]} [_ state]]
   {:db (assoc-in db [:dialogs :user-subscribe-alert :open?] state)
    :fx [[:dispatch [::set-login-loading false]]]}))


(rf/reg-event-fx
 ::open-user-subscribe-form
 (fn [{:keys [db]} [_ state]]
   {:db (assoc-in db [:dialogs :user-subscribe-form :open?] state)
    :fx [[:dispatch [::open-user-subscribe-alert false]]]}))

(rf/reg-event-fx
 ::logout-user
 (fn [{:keys [db]}]
   {:db (-> db
            (assoc-in [:user :is-logged?] false)
            (assoc-in [:user :data] nil))
    :fx [[:dispatch [::toggle-login-slide]]]}))

