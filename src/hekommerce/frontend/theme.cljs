(ns hekommerce.frontend.theme
  (:require [reagent-mui.styles :refer [theme-provider create-theme]]
            [reagent-mui.colors :refer [red amber]]
            [reagent-mui.material.css-baseline :refer [css-baseline]]
            [hekommerce.frontend.subs :as rfs]
            [re-frame.core :as rf]))

(def custom-theme
  {:palette {:primary red
             :secondary amber
             :mode "light"}
   :typography {:font-family "Orbitron"}})


(defn with-theme [& children]
  (let [theme @(rf/subscribe [::rfs/get-theme])]
    [theme-provider (create-theme (clj->js theme))
     [css-baseline]
     (into [:<>] children)]))

