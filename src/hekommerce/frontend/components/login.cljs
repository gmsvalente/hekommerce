(ns hekommerce.frontend.components.login
  (:require [reagent.core :as r]
            [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.box :refer [box]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.material.divider :refer [divider]]
            [reagent-mui.material.button :refer [button]]
            [reagent-mui.material.text-field :refer [text-field]]
            [cljs.spec.alpha :as s]
            [hekommerce.common.spec :as model]))


(defn custom-styles [theme]
  {".box-title" {:font-family "Orbitron"
                 :display "flex"
                 :justify-content "center"}
   ".login-box" {:display "flex"
                 :color "black"
                 :margin-top "12px"
                 :flex-direction "column"
                 :justify-content "space-between"
                 :align-items "center"
                 :height "130px"}
   ".login-input" {}
   ".login-button" {:justify-content "center"
                    :width "40%"}})


(defn login* [{:keys [class-name]}]
  (let [login-entry (r/atom "")
        login-ok? #(s/valid? ::model/login @login-entry)
        helper-fn  #(if (or (empty? @login-entry)
                            (login-ok?))
                      false
                      "Wrong login format!")]
    (fn []
      [box {:class class-name
            :sx {:margin "12px 8px auto"
                 :padding "4px"}}
       [typography {:class "box-title"} "Login"]
       [divider {:variant "middle"}]
       [:div {:class "login-box"}
        [text-field {:class "login-input"
                     :id "user-login"
                     :label "User Name"
                     :variant "outlined"
                     :onChange #(reset! login-entry (.. % -target -value))
                     :helperText (helper-fn)}]
        [button {:variant "contained"

                 :class "login-button"} "Login"]]])))

(def login (styled login* custom-styles))
