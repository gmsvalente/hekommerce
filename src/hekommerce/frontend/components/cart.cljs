(ns hekommerce.frontend.components.cart
  (:require [reagent-mui.material.fab :refer [fab]]
            [reagent-mui.material.box :refer [box]]
            [reagent-mui.icons.shopping-cart :refer [shopping-cart]]
            [reagent-mui.styles :refer [styled]]))

(defn custom-style [theme]
  (def t theme)
  {".card-box" {:margin-top "15px"
                :margin-right "15px"
                :float "right"}
   ".card-button" {:z-index 10
                   :box-shadow (get-in theme [:theme :shadows 2])}})

(defn cart-button* [{:keys [class-name]}]
  [:div {:class class-name}
   [box {:class "card-box"}
    [fab {:class "card-button"
          :color "secondary"}
     [shopping-cart]]]])


(def cart-button (styled cart-button* custom-style))
