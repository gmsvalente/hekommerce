(ns hekommerce.frontend.components.menu
  (:require [reagent-mui.material.divider :refer [divider]]
            [reagent-mui.material.typography :refer [typography]]))

(defn menu []
  [:div {:style {:position "relative"}}
   [divider {:style {:margin "7px 4px auto"}}
    [typography {:style {:font-family "Orbitron"}} "MENU"]]])
