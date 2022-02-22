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
    :user {:login-form {:loading? false
                        :slide true}
           :is-logged? false
           :data nil}}))

;;; dark/light mode
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


;;; drawer 
;;

;; toggle the drawer menu on/off
(rf/reg-event-fx
 ::toggle-drawer
 (fn [{:keys [db]} _]
   {:db (update-in db [:drawer :is-open?] not)}))


;;; login
;;
;; 0. empty login form
;; 1. user enters the user name in login form.
;; 2. change login button to loading state and fetch the user name.
;; 3. if fetch is successful, check result for successful login user
;;    or nil user
;; 4. if login succesful slide the login form and show user data in user box if not ask for create user
;; 5. if login not successful show an alert to create new user,
;;    if canceled returns to 0.

;; (1)

(rf/reg-event-db
 ::toggle-login-slide
 (fn [db _]
   (update-in db [:user :login-form :slide] not)))

;; (2) fetch 
(rf/reg-event-fx
 ::fetch-user
 (fn [_ [_ login]]
   {:fx [[:dispatch [::toggle-login-loading true]]
         [:http-xhrio {:method :get
                       :uri (str "http://localhost:8080/api/user/" login)
                       :format (ajax/json-request-format)
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [::process-result]
                       :on-failure #(.. js/console (log "cant fetch user"))}]]}))

;; (2) change state of the login button if loading or not
(rf/reg-event-fx
 ::toggle-login-loading
 (fn [{:keys [db]} [_ new-state]]
   {:db (assoc-in db [:user :login-form :loading?] new-state)}))


;; (3) if fetch is successful, check for user or nil in fetch result

;; helper function
(defn get-result-effect [{:keys [user]}]
  (if user ::success-login ::create-user))

;; interceptor
(def check-result
  (rf/->interceptor
   :id :check-result
   :before (fn [ctx]
             (let [[id result] (-> ctx :coeffects :event)
                   effect (get-result-effect (js->clj result :keywordize-keys true))]
               (assoc-in ctx [:coeffects :event] [id [effect result]])))))

;; event handler
(rf/reg-event-fx
 ::process-result
 [check-result]
 (fn [cofx [_ [effect result]]]
   {:fx [[:dispatch [effect result]]]}))

;; on succesful login
(rf/reg-event-fx
 ::success-login
 (fn [{:keys [db]} [_ {:keys [user]}]]
   {:db (-> db
            (assoc-in [:user :data] user)
            (assoc-in [:user :is-logged?] true))
    :fx [[:dispatch [::toggle-login-loading]]
         [:dispatch [::toggle-login-slide]]]}))

;; when user doesn't exist ask for creation 
(rf/reg-event-fx
 ::create-user
 (fn [_ _]
   (.. js/window (alert "create user"))))

