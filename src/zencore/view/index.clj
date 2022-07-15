(ns zencore.view.index
  (:require [zencore.base :as base]
            [hiccup.core :refer [html]]))

(defn jenis [ids] (base/get-jenis-from-config ids))

(defn beranda
  [session]
  (base/basehtml (html [:a {:href "/reset"} "Reset"]
                       [:ul
                        (for [i [0 1 2]]
                          [:li [:a {:href (:url (jenis i))} (:text (jenis i))] (str " Total Skor: " (:total ((:user session) i)))])]) 
                 session))

#_{:clj-kondo/ignore [:unused-binding]}
(defn index [{session :session}]
  (let [session (if (:user session)
                  session
                  base/bulk-session)]
   (do
    (println session)
    (beranda session))))