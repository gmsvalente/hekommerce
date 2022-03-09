(ns hekommerce.frontend.events.db
  (:require [re-frame.core :as rf ]
            [hekommerce.frontend.theme :refer [custom-theme]]))

;;; Dispatch the initial db content

(rf/reg-event-db
 ::init-db
 (fn [_]
   {:theme custom-theme
    :drawer {:is-open? false}
    :login-form {:loading? false
                 :slide true
                 :trying nil}
    :user {:is-logged? false
           :data nil}
    :dialogs {:user-subscribe-alert {:open? false}
              :user-subscribe-form {:open? false}}}))



