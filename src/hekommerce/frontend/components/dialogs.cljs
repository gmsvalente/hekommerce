(ns hekommerce.frontend.components.dialogs
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [reagent-mui.material.dialog :refer [dialog]]
            [reagent-mui.material.dialog-title :refer [dialog-title]]
            [reagent-mui.material.dialog-content :refer [dialog-content]]
            [reagent-mui.material.dialog-content-text :refer [dialog-content-text]]
            [reagent-mui.material.dialog-actions :refer [dialog-actions]]
            [reagent-mui.material.button :refer [button]]
            [reagent-mui.material.text-field :refer [text-field]]
            [hekommerce.frontend.utils :refer [target-value]]
            [hekommerce.frontend.subs :as rfs]
            [hekommerce.frontend.events :as rfe]))

(defn user-subscribe-form-dialog []
  (let [open? (rf/subscribe [::rfs/user-subscribe-form-open?])
        login (rf/subscribe [::rfs/trying-login])]
    (fn []
      (let [
            user (r/atom {:user/name "" :user/email ""})]
        [:div 
         [dialog {:open @open? :on-close #(rf/dispatch [::rfe/open-user-subscribe-form false])}
          [dialog-title "Subscribe"]
          [dialog-content {:style {:display "flex"
                                   :flex-direction "column"
                                   :justify-content "space-around"
                                   :height "300px"}}
           [text-field {:size "small"
                        :id "user-login"
                        :variant "outlined"
                        :disabled true
                        :defaultValue @login
                        :label "User login"}]
           [text-field {:size "small"
                        :id "user-name"
                        :variant "outlined"
                        :label "User name"
                        :on-change #(swap! user assoc :user/name (target-value %))}]
           [text-field {:size "small"
                        :id "user-email"
                        :variant "outlined"
                        :label "User email"
                        :on-change #(swap! user assoc :user/email (target-value %))}]]
          [dialog-actions
           [button {:variant "outlined"
                    :color "error"
                    :on-click #(rf/dispatch [::rfe/open-user-subscribe-form false])} "Cancel"]
           [button {:variant "contained"
                    :color "success"
                    :on-click #(.. js/console (log "create user!!!"))} "Ok"]]]]))))


(defn user-subscribe-alert-dialog []
  (let [user-login (rf/subscribe [::rfs/trying-login])
        open? (rf/subscribe [::rfs/user-subscribe-alert-open?])]
    (fn []
      [:div 
       [dialog {:open @open? :on-close #(rf/dispatch [::rfe/open-user-subscribe-alert false])}
        [dialog-title {:id "ask-user-creation-dialog"}
         "Create user " [:span {:style {:color "blue"}} @user-login] " ?"]
        [dialog-content
         [dialog-content-text {:id "ask-user-creation-dialog-content"}
          "The user doesn't exist in database. Create new user?"]]
        [dialog-actions
         [button {:variant "outlined" :color "error"
                  :on-click #(rf/dispatch [::rfe/open-user-subscribe-alert false])} "Cancel"]
         [button {:variant "contained" :color "success"
                  :on-click #(rf/dispatch [::rfe/open-user-subscribe-form true])} "OK"]]]])))

