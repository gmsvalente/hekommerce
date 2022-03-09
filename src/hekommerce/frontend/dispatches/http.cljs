(ns hekommerce.frontend.dispatches.http
  (:require [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [hekommerce.frontend.dispatches.ui :as ui]))

(defn get-result-effect
  "Return ths dispatch key and data"
  [{:keys [user]}]
  (if-not (nil? user)
    [::success-login {:user user}]
    [::ui/open-user-subscribe-alert true]))

(def check-result
  (rf/->interceptor
   :id :check-result
   :before
   (fn [{:keys [coeffects] :as ctx}]
     (let [[_ result] (:event coeffects)
           [id effect-result] (get-result-effect (js->clj result :keywordize-keys true))]
       (assoc-in ctx [:coeffects :event] [id effect-result])))))

(rf/reg-event-fx
 ::process-result
 [check-result]
 (fn [_ [effect result]]
   {:fx [[:dispatch [effect result]]]}))


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
 (fn [{:keys [db]} [_ user]]
   {:db (assoc-in db [:login-form :trying] user)
    :fx [[:dispatch [::ui/set-login-loading true]]
         [:http-xhrio {:method :get
                       :uri (str "/api/user/" user)
                       :format (ajax/json-request-format)
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [::process-result]
                       :on-failure []}]]}))


(rf/reg-event-fx
 ::create-user
 (fn [{:keys [db]} [_ data]]
   {:fx [[:dispatch [::ui/open-user-subscribe-form false]]
         [:http-xhrio {:method :post
                       :uri "/api/user"
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
