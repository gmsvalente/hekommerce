(ns hekommerce.frontend.render
  (:require [reagent.dom :refer [render]]
            [goog.dom :refer [getElement]]
            [re-frame.core :as rf]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.theme :refer [with-theme]]
            [hekommerce.frontend.components.header :refer [header]]
            [hekommerce.frontend.components.cart :refer [cart-button]]))

(defn root []
  [with-theme
   [header]
   [cart-button]])

(defn ^:dev/after-load mount-root []
  (render root (getElement "root")))

(defn init []
  (rf/dispatch-sync [::rfe/init-db])
  (mount-root))
