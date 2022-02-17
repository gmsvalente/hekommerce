(ns hekommerce.frontend.components.drawer
  (:require [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.drawer :refer [drawer]]
            [reagent-mui.material.box :refer [box]]
            [re-frame.core :as rf]
            [hekommerce.frontend.subs :as rfs]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.components.login :refer [login]]
            [hekommerce.frontend.components.menu :refer [menu]]))

(defn custom-style [theme])

(defn drawer-components [{:keys [class-name]}]
  [box {:sx {:display "flex"
               :flex-direction "column"
               :justify-content "center"
               :align-items "center"
               :width 340
               "@media screen and (max-width:600px)"
               {:width 280}}}
   [login]
   [menu]])

(defn drawer* [{:keys [class-name]}]
  [:div {:class class-name}
   [drawer {:anchor "right"
            :open @(rf/subscribe [::rfs/menu-open?])
            :on-close #(rf/dispatch [::rfe/toggle-menu])}
    [drawer-components]]])

(def drawer-menu (styled drawer* custom-style))

