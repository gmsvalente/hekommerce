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
 ::loading-user?
 (fn [db]
   (-> db :user :login :loading?)))

(rf/reg-sub
 ::login-slide-on?
 (fn [db]
   (-> db :user :login :slide-on?)))

(rf/reg-sub
 ::logged-in-slide-on?
 (fn [db]
   (-> db :user :logged-in :slide-on?)))
