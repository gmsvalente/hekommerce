(ns hekommerce.frontend.events
  (:require [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [hekommerce.frontend.theme :refer [custom-theme]]))

;;;;; initial db event
(rf/reg-event-db
 ::init-db
 (fn [_]
   {:theme custom-theme
    :drawer {:is-open? false}
    :login-form {:loading? false
                 :slide true
                 :trying nil}
    :user {:is-logged? false
           :data nil}
    :dialogs {:user-subscribe-alert {:open? false}
              :user-subscribe-form {:open? false}}}))

;;;;; theme events
;;
;; helper function
(defn change-mode [mode]
  (condp = mode
    "light" "dark"
    "dark" "light"))

;; toggle the theme mode dark/light
(rf/reg-event-fx
 ::toggle-theme-mode
 (fn [{:keys [db]} _]
   {:db (update-in db [:theme :palette :mode] change-mode )}))


;;;;; drawer events
;;
;; toggle the drawer menu on/off
(rf/reg-event-fx
 ::toggle-drawer
 (fn [{:keys [db]} _]
   {:db (update-in db [:drawer :is-open?] not)}))


;;; login events
;;
;; 0. empty login form
;; 1. user enters the user name in login form.
;; 2. change login button to loading state and fetch the user name.
;; 3. if fetch is successful, check result for successful login user
;;    or nil user
;; 4. if login succesful slide the login form and show user data in user box if not ask for create user
;; 5. if login not successful show an alert to create new user,
;;    if canceled returns to 0.

;; slide event (1)

(rf/reg-event-db
 ::toggle-login-slide
 (fn [db _]
   (update-in db [:login-form :slide] not)))

(def base-uri "http://192.168.1.103:8080")

;; fetch event (2)
(rf/reg-event-fx
 ::fetch-user
 (fn [{:keys [db]} [_ login]]
   {:db (assoc-in db [:login-form :trying] login)
    :fx [[:dispatch [::set-login-loading true]]
         [:http-xhrio {:method :get
                       :uri (str base-uri "/api/user/" login)
                       :format (ajax/json-request-format)
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [::process-result]
                       :on-failure []}]]}))

;; login button event (2)
;; change state of the login button if loading or not
(rf/reg-event-fx
 ::set-login-loading
 (fn [{:keys [db]} [_ new-state]]
   {:db (assoc-in db [:login-form :loading?] new-state)}))


;; if fetch is successful, check for user or nil in fetch result (3)
;; helper function
(defn get-result-effect [{:keys [user]}]
  (if-not (nil? user) [::success-login user] [::open-user-subscribe-alert true]))

;; interceptor 
(def check-result
  (rf/->interceptor
   :id :check-result
   :before (fn [ctx]
             (let [[_ result] (-> ctx :coeffects :event)
                   [id effect-result] (get-result-effect (js->clj result :keywordize-keys true))]
               (assoc-in ctx [:coeffects :event] [id effect-result])))))

;; process result event using the interceptor
(rf/reg-event-fx
 ::process-result
 [check-result]
 (fn [_ [effect result]]
   {:fx [[:dispatch [effect result]]]}))

;; on succesful login
(rf/reg-event-fx
 ::success-login
 (fn [{:keys [db]} [_ {:keys [user]}]]
   {:db (-> db
            (assoc-in [:user :data] user)
            (assoc-in [:user :is-logged?] true))
    :fx [[:dispatch [::set-login-loading false]]
         [:dispatch [::toggle-login-slide]]]}))

;;; subscribe dialogs
;; when user doesn't exist ask for subscribe

(rf/reg-event-fx
 ::open-user-subscribe-alert
 (fn [{:keys [db]} [_ state]]
   {:db (assoc-in db [:dialogs :user-subscribe-alert :open?] state)
    :fx [[:dispatch [::set-login-loading false]]]}))

;; if user wants to subscribe


(rf/reg-event-fx
 ::open-user-subscribe-form
 (fn [{:keys [db]} [_ state]]
   {:db (assoc-in db [:dialogs :user-subscribe-form :open?] state)
    :fx [[:dispatch [::open-user-subscribe-alert false]]]}))


(rf/reg-event-fx
 ::create-user
 (fn [{:keys [db]} [_ data]]
   {:fx [[:dispatch [::open-user-subscribe-form false]]
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
