(ns zencore.view.bahas
  (:require [zencore.base :as base]
            [hiccup.core :refer [html]]
            [hiccup.form :as f]))

(defn query-param-check [params]
  (if (params "type")
    (Integer/parseInt (params "type"))
    0))

(defn mark
  [option]
  (if (option 0)
    [:b {:style "color:green"} (option 1)]
    [:span {:style "color:black"} (option 1)]))

(defn block-soal
  [idx ans soal]
  [:p (:soal-text (:soal (soal idx)))
   (for [i (:options (:soal (soal idx)))]
     (if (= (i 2) ans)
       [:p (base/options (i 2)) ". " [:b (mark i)]]
       [:p (base/options (i 2)) ". " (mark i)])
     )])

(defn index [{session :session params :query-params}]
  (let [type (query-param-check params)
        soal (base/get-model type)
        user (:user session)
        jenis-soal (user type)
        idx-soal (:idx-soal jenis-soal)
        skor (:skor jenis-soal)
        jawaban (:jawaban jenis-soal)
        ] 
     (base/basehtml (html [:p "Pembahasan"] 
                          [:p (str "Skor: " skor)]
                          ;; kalo pake looping malah error mulu gan ntahlah
                          (block-soal (first (take-last (- 8 0) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 1) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 2) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 3) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 4) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 5) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 6) idx-soal)) (last jawaban) soal)
                          (block-soal (first (take-last (- 8 7) idx-soal)) (last jawaban) soal)
                         [:p]
                          (f/form-to [:post (str "/?type=" type)]
                                     (f/submit-button :beranda))
                        ;;  [:a {:href "/"} "Kembali ke beranda"]
                         ) session)))