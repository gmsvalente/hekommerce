(ns hekommerce.common.spec
  (:require #?(:clj [clojure.spec.alpha :as spec]
               :cljs [cljs.spec.alpha :as spec])))


;;; contact
(spec/def :contact/email string?)
(spec/def :contact/phone string?)

(spec/def ::contact (spec/keys :req [:contact/email]
                               :opt [:contact/phone]))

;;; user
(spec/def :user/name string?)
(spec/def :user/id uuid?)
(spec/def :user/contact ::contact)

(spec/def ::user (spec/keys :req [:user/name :user/id]
                            :opt [:user/contact]))
;;; company
(spec/def :company/name string?)
(spec/def :company/id int?)
(spec/def :company/contact ::contact)
(spec/def ::company (spec/keys :req [:company/name
                                     :company/name]
                               :opt [:company/contact]))

;;; category
(spec/def :category/name string?)
(spec/def :category/id int?)
(spec/def :category/type string?)
(spec/def ::category (spec/keys :req [:category/id :category/name :category/type]))

;;; product
(spec/def :product/name string?)
(spec/def :product/id int?)
(spec/def :product/category ::category)
(spec/def :product/price double?)
(spec/def :product/image string?)
(spec/def :product/company ::company)

(spec/def ::product (spec/keys :req [:product/id
                                     :product/name
                                     :product/category
                                     :product/price]
                               :opt [:product/image]))





