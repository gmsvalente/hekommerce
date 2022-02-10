(ns hekommerce.frontend.theme
  (:require [reagent-mui.styles :as styles]
            [reagent-mui.colors :as colors]
            [reagent-mui.material.css-baseline :refer [css-baseline]]))

(def custom-theme
  {:palette {:primary colors/orange
             :secondary colors/blue
             :mode "light"}
   :typography {:font-family "Orbitron"}})


(defn with-theme [& children]
  [styles/theme-provider (styles/create-theme (clj->js custom-theme))
   [css-baseline]
   (into [:<>] children)])

