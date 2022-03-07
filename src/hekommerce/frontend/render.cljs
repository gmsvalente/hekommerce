(ns hekommerce.frontend.render
  (:require [reagent.dom :refer [render]]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.theme :refer [with-theme]]
            [hekommerce.frontend.components.header :refer [header]]
            [hekommerce.frontend.components.cart :refer [cart-button]]
            [hekommerce.frontend.components.drawer :refer [drawer-menu]]
            [hekommerce.frontend.components.dialogs :refer [user-subscribe-form-dialog user-subscribe-alert-dialog]]))

(defn root []
  [with-theme
   [user-subscribe-form-dialog]
   [user-subscribe-alert-dialog]
   [:div
    [header]
    [drawer-menu]
    [cart-button]]])

(defn ^:dev/after-load mount-root []
  (render root (.. js/document (getElementById "root"))))

(defn init []
  (rfe/set-initial-db)
  (mount-root))
