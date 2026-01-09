(def kitchensink-version "3.5.4")
(def trapperkeeper-version "4.3.0")
(def trapperkeeper-webserver-jetty10-version "1.1.0")
(def i18n-version "1.0.2")
(def jackson-version "2.15.4")

(defproject org.openvoxproject/rbac-client "1.2.1-SNAPSHOT"
  :description "Tools for interacting with PE RBAC"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}


  ;; These are to enforce consistent versions across dependencies of dependencies,
  ;; and to avoid having to define versions in multiple places. If a component
  ;; defined under :dependencies ends up causing an error due to :pedantic? :abort,
  ;; because it is a dep of a dep with a different version, move it here.
  :managed-dependencies [[org.clojure/clojure "1.12.4"]

                         [ring/ring-core "1.8.2"]
                         [ring/ring-codec "1.3.0"]
                         [commons-io "2.21.0"]
                         [commons-codec "1.20.0"]
                         [cheshire "5.13.0"]

                         [com.fasterxml.jackson.core/jackson-core ~jackson-version]
                         [com.fasterxml.jackson.core/jackson-databind ~jackson-version]
                         [com.fasterxml.jackson.core/jackson-annotations ~jackson-version]
                         [com.fasterxml.jackson.module/jackson-module-afterburner ~jackson-version]
                         [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor ~jackson-version]
                         [com.fasterxml.jackson.dataformat/jackson-dataformat-smile ~jackson-version]
                         
                         [org.openvoxproject/kitchensink ~kitchensink-version]
                         [org.openvoxproject/kitchensink ~kitchensink-version :classifier "test"]
                         [org.openvoxproject/trapperkeeper ~trapperkeeper-version]
                         [org.openvoxproject/trapperkeeper ~trapperkeeper-version :classifier "test"]
                         [org.openvoxproject/trapperkeeper-webserver-jetty10 ~trapperkeeper-webserver-jetty10-version]
                         [org.openvoxproject/trapperkeeper-webserver-jetty10 ~trapperkeeper-webserver-jetty10-version :classifier "test"]]

  :dependencies [[org.clojure/clojure]
                 [ring/ring-core]
                 [ring/ring-json "0.5.1"]
                 [slingshot "0.12.2"]
                 [org.openvoxproject/ring-middleware "2.1.0"]
                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/http-client "2.2.0"]
                 [org.openvoxproject/trapperkeeper]
                 [org.openvoxproject/i18n ~i18n-version]]

  :pedantic? :abort
  :profiles {:dev {:dependencies [[org.openvoxproject/kitchensink :classifier "test"]
                                  [org.openvoxproject/trapperkeeper :classifier "test"]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty10]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty10 :classifier "test"]
                                  [org.bouncycastle/bcpkix-jdk18on "1.83"]
                                  ; transitive dependency
                                  [org.clojure/tools.nrepl "0.2.13"]]}
             :testutils {:source-paths ^:replace  ["test"]}}

  :plugins [[org.openvoxproject/i18n ~i18n-version]]

  :classifiers  [["test" :testutils]]

  :test-paths ["test"]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]])
