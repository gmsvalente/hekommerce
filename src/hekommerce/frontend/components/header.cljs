(ns hekommerce.frontend.components.header
  (:require [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.app-bar :refer [app-bar]]
            [reagent-mui.material.toolbar :refer [toolbar]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.icons.menu :refer [menu]]
            [reagent-mui.material.icon-button :refer [icon-button]]
            [re-frame.core :as rf]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.subs :as rfs]))

(defn custom-style [theme]
  {".bar" {:position "relative"
           :z-index 1}
   ".header" {:justify-content "space-between"}
   ".header-title" {:font-family "Orbitron"
                    :font-size 30
                    "@media screen and (max-width: 600px)"
                    {:font-size 24}}})

(defn header* [{:keys [class-name]}]
  [:div {:class class-name}
   [app-bar {:class "bar"}
    [toolbar {:class "header"}
     [typography {:class "header-title"} "HEKOMMERCE"]
     [:div {:class "button-box"}
      [icon-button {:on-click #(rf/dispatch [::rfe/toggle-theme-mode])}
       [@(rf/subscribe [::rfs/theme-mode-btn-icon])]]
      [icon-button {:on-click #(rf/dispatch [::rfe/toggle-menu])}
       [menu]]]]]])

(def header (styled header* custom-style))

