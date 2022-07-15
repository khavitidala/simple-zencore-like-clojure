(ns zencore.handler
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [zencore.view.index :as index]
            [zencore.view.soal :as soal]
            [zencore.view.bahas :as bahas]
            [zencore.controller.reset :as reset]
            [zencore.controller.scoring :as scoring]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes app-routes 
  ;; (GET "/" [] "Halo")
  (GET "/" [] index/index)
  (POST "/" [] scoring/back)
  (POST "/soal" [] scoring/index)
  (GET "/soal" [] soal/index)
  (GET "/bahas" [] bahas/index)
  (GET "/reset" [] reset/delete-sess)
  (route/not-found "Not Found"))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(def app
  (-> app-routes
      (wrap-defaults (assoc api-defaults :anti-forgery true))
      (wrap-session {:cookie-attrs {:max-age (* 3600 24)}})
      wrap-params))