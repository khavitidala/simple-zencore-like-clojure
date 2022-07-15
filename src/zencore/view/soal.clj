(ns zencore.view.soal
  (:require [zencore.base :as base] 
            [hiccup.core :refer [html]]
            [hiccup.form :as f]))

(defn index [{session :session params :query-params}]
  (let [param (base/query-param-check params)
        [type ids] param
        ;; session (base/soft-reset session type ids)
        soal (base/get-model type)
        user (:user session)
        jenis-soal (user type)
        idx-soal (:idx-soal jenis-soal)
        idx (first (take-last (- 8 ids) idx-soal))] 
    (base/basehtml (html [:p (:text (base/get-jenis-from-config type))] 
                         [:p (:soal-text (:soal (soal idx)))]
                         [:p]
                         (f/form-to [:post (str "/soal?type=" type "&ids=" ids)]
                                    (for [i (:options (:soal (soal idx)))] 
                                      [:p (f/radio-button :choice false (i 2)) (base/options (i 2)) ". " (i 1)])
                                    (f/submit-button :submit))
                         ) session)))