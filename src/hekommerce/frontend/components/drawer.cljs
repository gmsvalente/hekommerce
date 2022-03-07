(ns hekommerce.frontend.components.drawer
  (:require [re-frame.core :as rf]
            [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.drawer :refer [drawer]]
            [hekommerce.frontend.subs :as rfs]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.components.login :refer [login]]
            [hekommerce.frontend.components.menu :refer [menu]]))

(defn custom-style [theme]
  {".drawer-components" {:display "flex"
                         :flex-direction "column"
                         :height "100vh"
                         :width 340
                         "@media screen and (max-width:600px)"
                         {:width 280}}})

(defn drawer-components []
  [:div {:class "drawer-components"}
   [login]
   [menu]])


(defn drawer* [{:keys [class-name]}]
  [drawer {:anchor "right"
           :open @(rf/subscribe [::rfs/drawer-open?])
           :on-close rfe/close-drawer}
   [:div {:class class-name}
    [drawer-components]]])

(def drawer-menu (styled drawer* custom-style))

