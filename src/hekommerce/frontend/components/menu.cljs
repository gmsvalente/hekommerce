(ns hekommerce.frontend.components.menu
  (:require [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.divider :refer [divider]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.material.button :refer [button]]
            [hekommerce.frontend.subs :as rfs]
            [re-frame.core :as rf]))

(defn menu-prod-button [text]
  (let [logged? (rf/subscribe [::rfs/logged?])]
    (fn []
      [button {:variant "contained"
               :color "secondary"
               :disabled (not @logged?)}
       (str text)])))

(def add-prod-button 
  [menu-prod-button "Add Product"])

(def rem-prod-button 
  [menu-prod-button "Remove Product"])

(def edit-prod-button
  [menu-prod-button "Edit Product"])

(defn menu-styles [theme]
  {".menu-buttons" {:display "flex"
                    :margin "55px auto"
                    :height "150px"
                    :width "85%"
                    :flex-direction "column"
                    :justify-content "space-between"
                    :align-items "space-around"}
   ".menu"  {:position "relative"}})

(defn menu* [{:keys [class-name]}]
  [:div {:class class-name}
   [:div.menu
    [divider {:style {:margin "7px 4px auto"}}
     [typography {:style {:font-family "Orbitron"}} "MENU"]]
    [:div.menu-buttons 
     add-prod-button
     edit-prod-button
     rem-prod-button]]])

(def menu (styled menu* menu-styles))
