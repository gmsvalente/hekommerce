(ns hekommerce.frontend.render
  (:require [reagent.dom :refer [render]]
            [goog.dom :refer [getElement]]))

(defn root []
  [:div
   [:h1 "HEKOMMERCE"]])


(defn ^:dev/after-load mount-root []
  (render root (getElement "root")))

(defn init []
  (mount-root))

