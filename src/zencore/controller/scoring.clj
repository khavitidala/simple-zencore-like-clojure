(ns zencore.controller.scoring
  (:require [ring.util.response :as response]
            [zencore.base :as base]))

(defn insert-ans 
  [info option type ids]
  (let
   [soal (base/get-model type)
    jawaban (:jawaban info)
    idx-soal (:idx-soal info)
    skor (:skor info)
    total (:total info)
    ins-jawaban (conj jawaban option)
    idx (first (take-last (- 8 ids) idx-soal))
    options (:options (:soal (soal idx)))]
    (if (= ((options option) 0) true) 
      {:idx-soal idx-soal :skor (+ skor 1) :jawaban ins-jawaban :total (+ total 1)}
      {:idx-soal idx-soal :skor skor :jawaban ins-jawaban :total total}) 
    )
  )

(defn store-data
  [params session type ids]
  (let 
   [choice (Integer/parseInt (params "choice"))
    user (:user session)
    info (user type)] 
    (if (params "choice")
        (if (:user session)
          (assoc session :user (assoc user type (insert-ans info choice type ids)))
          session)
        session)))

(defn index
  [{session :session params :form-params qparams :query-params}]
  (let [param (base/query-param-check qparams)
        [type ids] param
        session (store-data params session type ids)
        num-soal (base/get-num-soal type)] 
    (do
      (println session)
     (if (= (+ ids 1) num-soal)
        (-> (response/redirect (str "/bahas?type=" type))
            (assoc :session session))
        (-> (response/redirect (str "/soal?type=" type "&ids=" (+ ids 1)))
            (assoc :session session))))))

(defn query-param-check [params]
  (if (params "type")
    (Integer/parseInt (params "type"))
    0))

(defn back
  [{session :session params :query-params}]
  (let [type (query-param-check params)
        user (:user session)
        jenis-soal (user type)
        session (assoc session :user (assoc user type {:idx-soal (take (base/get-num-soal type) (shuffle (base/generate-idx-soal type))) :skor 0 :jawaban [] :total (:total jenis-soal)}))
        ;; session__ (assoc session_ :user (assoc user type (assoc jenis-soal :idx-soal (take (base/get-num-soal type) (shuffle (base/generate-idx-soal type))))))
        ]
    (-> (response/redirect "/")
        (assoc :session session)))
  )