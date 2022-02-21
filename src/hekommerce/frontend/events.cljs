(ns hekommerce.frontend.events
  (:require [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [hekommerce.frontend.theme :refer [custom-theme]]))


;;; initial db
(rf/reg-event-db
 ::init-db
 (fn [_]
   {:theme custom-theme
    :drawer {:is-open? false}
    :user {:login {:loading? false
                   :slide-on? true}
           :logged-in {:slide-on? false}
           :is-logged? false
           :data nil}}))

;;; dark/light mode
(defn change-mode [mode]
  (condp = mode
    "light" "dark"
    "dark" "light"))

(rf/reg-event-fx
 ::toggle-theme-mode
 (fn [{:keys [db]} _]
   {:db (update-in db [:theme :palette :mode] change-mode )}))


;;; drawer 
(rf/reg-event-fx
 ::toggle-drawer
 (fn [{:keys [db]} _]
   {:db (update-in db [:drawer :is-open?] not)}))


;;; login
(rf/reg-event-fx
 ::toggle-login-loading
 (fn [{:keys [db]} [_ new-state]]
   {:db (assoc-in db [:user :loading?] new-state)}))

(rf/reg-event-fx
 ::success-login
 (fn [{:keys [db]} [_ result]]
   {:db (-> db
            (assoc-in [:user :data] (:user result))
            (assoc-in [:user :is-logged?] true))
    :fx [[:dispatch [::toggle-login-loading false]]]}))

(rf/reg-event-fx
 ::set-user
 (fn [{:keys [db]} [_ login]]
   {:db (assoc-in db [:user :loading?] true)
    :http-xhrio {:method :get
                 :uri (str "http://localhost:8080/api/user/" login)
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::success-login]
                 :on-failure #(.. js/console (log "cant fetch user"))}}))

(rf/reg-event-db
 ::toggle-login-slide
 (fn [db _]
   (-> db
       (update-in [:user :login :slide-on?] not)
       (update-in [:user :logged-in :slide-on?] not))))

