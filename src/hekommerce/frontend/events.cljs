(ns hekommerce.frontend.events
  (:require [re-frame.core :as rf]
            [hekommerce.frontend.dispatches.db :as dispatch-db]
            [hekommerce.frontend.dispatches.theme :as dispatch-theme]
            [hekommerce.frontend.dispatches.ui :as dispatch-ui]
            [hekommerce.frontend.dispatches.http :as dispatch-http]))

(defn set-initial-db
  "Set the initial re-frame app-db"
  []
  (rf/dispatch-sync [::dispatch-db/init-db]))


(defn toggle-ui-mode
  "Changes the dark/light mode of the UI"
  []
  (rf/dispatch [::dispatch-theme/toggle-theme-mode]))

(defn toggle-drawer
  "Toggles the menu drawer"
  []
  (rf/dispatch [::dispatch-ui/toggle-drawer]))

(defn set-drawer
  "Set the menu drawer to state"
  [state]
  (rf/dispatch [::dispatch-ui/set-drawer state]))

(defn close-drawer
  "Close the menu drawer"
  []
  (set-drawer false))

(defn open-drawer
  "Open the menu drawer"
  []
  (set-drawer true))



(defn toggle-login-slide
  "Toggles the login slide"
  []
  (rf/dispatch [::dispatch-ui/toggle-login-slide]))

(defn set-login-slide
  "Sets the login slide to show slide(state=true) or logged-in user(state=false)"
  [state]
  (rf/dispatch [::dispatch-ui/set-login-slide state]))

(defn login-slide->login
  "Show login slide as login"
  []
  (set-login-slide true))

(defn login-slide->user
  "Show login slide as login"
  []
  (set-login-slide false))

(defn fetch-user
  "Fetch the user via http api get"
  [user]
  (rf/dispatch [::dispatch-http/fetch-user user]))

(defn create-user
  "Create user via http api post"
  [data]
  (rf/dispatch [::dispatch-http/create-user data]))

(defn set-login-loading
  "Sets the login button to a loading state"
  [state]
  (rf/dispatch [::dispatch-ui/set-login-loading state]))


(defn set-user-subscribe-form
  "Sets the user subscribe form open/closed"
  [state]
  (rf/dispatch [::dispatch-ui/open-user-subscribe-form state]))

(defn open-user-subscribe-form
  "Opens the user subscribe form"
  []
  (set-user-subscribe-form true))

(defn close-user-subscribe-form
  "Opens the user subscribe form"
  []
  (set-user-subscribe-form false))

(defn set-user-subscribe-alert
  "Sets the user subscribe form open/closed"
  [state]
  (rf/dispatch [::dispatch-ui/open-user-subscribe-alert state]))

(defn open-user-subscribe-alert
  "Opens the user subscribe form"
  []
  (set-user-subscribe-alert true))

(defn close-user-subscribe-alert
  "Opens the user subscribe form"
  []
  (set-user-subscribe-alert false))

