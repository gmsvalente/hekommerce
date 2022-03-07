(ns hekommerce.frontend.subs
  (:require [re-frame.core :as rf]
            [reagent-mui.icons.dark-mode :refer [dark-mode]]
            [reagent-mui.icons.light-mode :refer [light-mode]]))

;;; theme
(rf/reg-sub
 ::get-theme
 (fn [db]
   (:theme db)))

(rf/reg-sub
 ::get-theme-mode
 :<- [::get-theme]
 (fn [theme]
   (-> theme :palette :mode)))

(rf/reg-sub
 ::theme-mode-btn-icon
 :<- [::get-theme-mode]
 (fn [mode]
   (condp = mode
     "light" dark-mode
     "dark" light-mode
     'oops)))

;;; drawer
(rf/reg-sub
 ::drawer-open?
 (fn [db]
   (-> db :drawer :is-open?)))

;;; login
(rf/reg-sub
 ::logged?
 (fn [db]
   (-> db :user :is-logged?)))

(rf/reg-sub
 ::loading-user?
 (fn [db]
   (-> db :login-form :loading?)))

(rf/reg-sub
 ::login-form-slide
 (fn [db]
   (-> db :login-form :slide)))

(rf/reg-sub
 ::trying-login
 (fn [db]
   (-> db :login-form :trying)))

(rf/reg-sub
 ::create-user-form-open?
 (fn [db]
   (-> db :dialogs :user-creation :open?)) )

;;; user-data

(rf/reg-sub
 ::user-data
 (fn [db]
   (-> db :user :data)))


;;; dialogs

(rf/reg-sub
 ::user-subscribe-alert-open?
 (fn [db]
   (-> db :dialogs :user-subscribe-alert :open?)))

(rf/reg-sub
 ::user-subscribe-form-open?
 (fn [db]
   (-> db :dialogs :user-subscribe-form :open?)))
