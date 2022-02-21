(ns hekommerce.common.spec
  (:require #?(:clj [clojure.spec.alpha :as spec]
               :clj [clojure.string]
               :cljs [cljs.spec.alpha :as spec])))

;;; UI login

(spec/def ::login-regex #(first (re-matches #"^[a-zA-Z]([a-zA-Z0-9-._]+){2}[a-zA-Z0-9]$" %)))

(spec/def ::login (spec/and string? ::login-regex))



;;; contact
(spec/def :contact/email string?)
(spec/def :contact/phone string?)

(spec/def ::contact (spec/keys :req [:contact/email]
                               :opt [:contact/phone]))

;;; category
(spec/def :category/name string?)
(spec/def :category/id int?)
(spec/def :category/type string?)
(spec/def ::category (spec/keys :req [:category/id :category/name :category/type]))

;;; company
(spec/def :company/name string?)
(spec/def :company/id int?)
(spec/def :company/contact ::contact)

(spec/def ::company (spec/keys :req [:company/name
                                     :company/name]
                               :opt [:company/contact]))


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


;;; user
(spec/def :user/name string?)
(spec/def :user/login ::login)
(spec/def :user/id uuid?)
(spec/def :user/contact ::contact)
(spec/def :user/products (spec/coll-of ::product))

(spec/def ::user (spec/keys :req [:user/name :user/login :user/id]
                            :opt [:user/contact]))

