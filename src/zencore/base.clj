(ns zencore.base
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(defn load-data
  [filename]
  (edn/read-string (slurp (io/resource filename))))

(def config 
  {:jenis [{:text "Matematika" :numsoal 8 :url "/soal?type=0&ids=0" :model "model/math.edn"}
           {:text "Verbal Logic" :numsoal 8 :url "/soal?type=1&ids=0" :model "model/verbal.edn"}
           {:text "Bahasa Inggris" :numsoal 8 :url "/soal?type=2&ids=0" :model "model/english.edn"}]})

(def options ["A" "B" "C" "D" "E" "F" "G" "H" "I" "J" "K" "L"])

(def get-jenis-from-config
  (-> config
      :jenis))

(defn get-num-soal [tipe]
  (-> tipe
      get-jenis-from-config
      :numsoal))

(defn get-model [tipe]
  (-> tipe
      get-jenis-from-config
      :model
      load-data))

(defn get-len-of-type [type]
  (count (get-model type)))

; random generation of unique series of random numbers from 0 to n-1 
(defn unique-random-numbers [n]
  (let [a-set (set (take n (repeatedly #(rand-int n))))]
    (concat a-set (set/difference (set (take n (range)))
                                  a-set))))

(defn generate-idx-soal [tipe]
  (-> tipe
      get-len-of-type
      unique-random-numbers
      ))

(def bulk-session
  {:user [{:idx-soal (take (get-num-soal 0) (shuffle (generate-idx-soal 0))) :skor 0 :jawaban [] :total 0} 
          {:idx-soal (take (get-num-soal 1) (shuffle (generate-idx-soal 1))) :skor 0 :jawaban [] :total 0}
          {:idx-soal (take (get-num-soal 2) (shuffle (generate-idx-soal 2))) :skor 0 :jawaban [] :total 0}]})

(defn soft-reset
  [session type ids]
  (if (:user session)
    (let [session bulk-session
          user (:user session)
          jenis-soal (user type)
          skor (:skor jenis-soal)
          total (:total jenis-soal)]
      (if (= ids 0)
        (assoc session :user (assoc user type {:idx-soal (take (get-num-soal type) (shuffle (generate-idx-soal type))),
                                               :skor skor, :jawaban [], :total total}))
        session))
    session))

(defn query-param-check [params]
  (if (params "type")
    (if (params "ids")
      [(Integer/parseInt (params "type")) (Integer/parseInt (params "ids"))]
      [(Integer/parseInt (params "type")) 0])
    [0 0]))

(defn basehtml
  [body session]
  {:body body
   :headers {"Content-Type" "text/html"}
   :status 200
   :session session})