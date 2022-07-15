(defproject zencore "1.0.0"
  :description "A simple zencore-like app"
  :url "https://github.com/khavitidala/simple-zencore-like-clojure"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.3"]
                 [environ "0.5.0"]
                 [hiccup "1.0.5"]]
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler zencore.handler/app}
  :uberjar-name "zencore-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}
   :production {:env {:production true}}})