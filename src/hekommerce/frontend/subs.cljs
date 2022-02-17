(ns hekommerce.frontend.subs
  (:require [re-frame.core :as rf]
            [reagent-mui.icons.dark-mode :refer [dark-mode]]
            [reagent-mui.icons.light-mode :refer [light-mode]]))

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

(rf/reg-sub
 ::menu-open?
 (fn [db]
   (-> db :menu :is-open?)))
