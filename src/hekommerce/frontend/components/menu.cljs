(ns hekommerce.frontend.components.menu
  (:require [reagent-mui.styles :refer [styled]]
            [re-frame.core :as rf]
            [hekommerce.frontend.subs :as rfs]
            [hekommerce.frontend.events :as rfe]))

(defn custom-style [theme]
)

(defn menu* [{:keys [class-name]}]
  [:div {:class class-name}])

(def menu (styled menu* custom-style))

