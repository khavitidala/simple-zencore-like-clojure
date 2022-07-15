(ns zencore.controller.reset
  (:require [ring.util.response :as response]))

#_{:clj-kondo/ignore [:unused-binding]}
(defn delete-sess
  [{session :session}]
  (-> (response/redirect "/")
      (assoc :session {})))