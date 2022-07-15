(defproject zencore "1.0.0"
  :description "A simple zencore-like app"
  :url "https://github.com/khavitidala/simple-zencore-like-clojure"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.3"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler zencore.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})