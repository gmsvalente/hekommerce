(ns hekommerce.frontend.theme
  (:require [reagent-mui.styles :as styles]
            [reagent-mui.colors :as colors]
            [reagent-mui.material.css-baseline :refer [css-baseline]]
            [hekommerce.frontend.subs :as rfs]
            [re-frame.core :as rf]))

(def custom-theme
  {:palette {:primary colors/red
             :secondary colors/amber
             :mode "light"}
   :typography {:font-family "Orbitron"}})


(defn with-theme [& children]
  (let [theme @(rf/subscribe [::rfs/get-theme])]
    [styles/theme-provider (styles/create-theme (clj->js theme))
     [css-baseline]
     (into [:<>] children)]))

