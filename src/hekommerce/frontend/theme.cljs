(ns hekommerce.frontend.theme
  (:require [reagent-mui.styles :refer [theme-provider create-theme]]
            [reagent-mui.colors :refer [red amber]]
            [reagent-mui.material.css-baseline :refer [css-baseline]]
            [hekommerce.frontend.subs :as rfs]
            [re-frame.core :as rf]))

(def default-theme-mode
  (let [mode (.. js/window
                 (matchMedia "(prefers-color-scheme: dark)")
                 -matches)]
    (if mode
      "dark"
      "light")))

(def custom-theme
  {:palette {:primary red
             :secondary amber
             :mode default-theme-mode}
   :typography {:font-family "Orbitron"}})


(defn with-theme [& children]
  (let [theme @(rf/subscribe [::rfs/get-theme])]
    [theme-provider (create-theme (clj->js theme))
     [css-baseline]
     (into [:<>] children)]))

