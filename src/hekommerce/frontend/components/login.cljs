(ns hekommerce.frontend.components.login
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent-mui.styles :refer [styled]]
            [reagent-mui.material.typography :refer [typography]]
            [reagent-mui.material.divider :refer [divider]]
            [reagent-mui.lab.loading-button :refer [loading-button]]
            [reagent-mui.material.text-field :refer [text-field]]
            [reagent-mui.material.slide :refer [slide]]
            [cljs.spec.alpha :as s]
            [hekommerce.frontend.utils :refer [target-value]]
            [hekommerce.common.spec :as model]
            [hekommerce.frontend.events :as rfe]
            [hekommerce.frontend.subs :as rfs]))

(defn custom-styles [theme]
  {".login-container" {:position "relative"
                       :height "185px"}
   ".login-slide" {:position "absolute"
                   :overflow "hidden"
                   :left "10%"
                   :right "10%"}
   ".user-slide"  {:position "absolute"
                   :overflow "hidden"
                   :left "15%"
                   :right "15%"
                   :z-index -1}
   ".login-box" {:margin "15px"
                 :height "105px"
                 :display "flex"
                 :flex-direction "column"
                 :justify-content "space-between"
                 :align-items "center"}})

(defn login-box []
  (let [login-user (r/atom "")
        login-ok? #(s/valid? ::model/login @login-user)
        helper-fn  #(when-not (or (empty? @login-user)
                                  (login-ok?))
                      "Wrong login format!")
        loading? (rf/subscribe [::rfs/loading-user?])
        slide? (rf/subscribe [::rfs/login-form-slide])]
    (fn []
      [:div {:class "login-slide"}
       [slide {:direction "right"
               :in @slide?}
        [:div {:class "login-box"}
         [text-field {:size "small"
                      :id "user-login"
                      :label "User Name"
                      :variant "outlined"
                      :value @login-user
                      :on-change #(reset! login-user (target-value %))
                      :helperText (helper-fn)}]
         [loading-button {:variant "contained"
                          :loading @loading?
                          :on-click #(when (login-ok?) (rfe/fetch-user @login-user))}
          [typography {:style {:font-family "Orbitron"}} "Login"]]]]])))


(defn user-box []
(let [user  (rf/subscribe [::rfs/user-data])
      slide? (rf/subscribe [::rfs/login-form-slide])]
  (fn []
    (let [{:keys [user/name user/login]} @user]
      [:div {:class "user-slide"}
       [slide {:direction "left"
               :in (not @slide?)}
        [:div {:class "user-box"}
         [typography (str "Name: " name)]
         [typography (str "Login: " login)]
         [typography (str "Products:  products")]]]]))))

(defn login-container [& children]
  (into [:div {:class "login-container"}] children))

(defn login* [{:keys [class-name]}]
  [:div {:class class-name}
   [login-container
    [divider {:style {:margin "7px 4px auto"}}
     [typography {:style {:font-family "Orbitron"}} "USER"]]
    [login-box]
    [user-box]]])

(def login (styled login* custom-styles))
