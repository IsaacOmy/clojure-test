(defproject clojure-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [environ "1.0.3"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [ring/ring-defaults "0.2.3"]
                 [aleph "0.4.3"]
                 [compojure "1.6.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [cheshire "5.7.1"]
                 [org.clojure/data.json "0.2.6"]]
  :plugins [[cider/cider-nrepl "0.15.0"]]
  :main clojure-test.core

  )
