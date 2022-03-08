(ns hekommerce.frontend.dispatches.http
  (:require [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [hekommerce.frontend.dispatches.ui :as ui]))

;(def base-uri "http://localhost:8080")
(def base-uri "https://hekommerce-staging.herokuapp.com")

(defn get-result-effect [{:keys [user]}]
  (if-not (nil? user)
    [::success-login user]
    [::ui/open-user-subscribe-alert true]))


(def check-result
  (rf/->interceptor
   :id :check-result
   :before (fn [ctx]
             (let [[_ result] (-> ctx :coeffects :event)
                   [id effect-result] (get-result-effect (js->clj result :keywordize-keys true))]
               (assoc-in ctx [:coeffects :event] [id effect-result])))))

(rf/reg-event-fx
 ::process-result
 [check-result]
 (fn [_ [effect result]]
   {:fx [[:dispatch [effect {:user result}]]]}))


(rf/reg-event-fx
 ::success-login
 (fn [{:keys [db]} [_ {:keys [user]}]]
   {:db (-> db
            (assoc-in [:user :data] user)
            (assoc-in [:user :is-logged?] true))
    :fx [[:dispatch [::ui/set-login-loading false]]
         [:dispatch [::ui/toggle-login-slide]]]}))


(rf/reg-event-fx
 ::fetch-user
 (fn [{:keys [db]} [_ login]]
   {:db (assoc-in db [:login-form :trying] login)
    :fx [[:dispatch [::ui/set-login-loading true]]
         [:http-xhrio {:method :get
                       :uri (str base-uri "/api/user/" login)
                       :format (ajax/json-request-format)
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [::process-result]
                       :on-failure []}]]}))


(rf/reg-event-fx
 ::create-user
 (fn [{:keys [db]} [_ data]]
   {:fx [[:dispatch [::ui/open-user-subscribe-form false]]
         [:http-xhrio {:method :post
                       :uri (str base-uri "/api/user")
                       :params data
                       :format (ajax/json-request-format )
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [::success-login]
                       :on-failure [::post-error]}]]}))


(rf/reg-event-fx
 ::post-error
 (fn [_ [_ data]]
   (.. js/console (log data))
   {:fx []}))
