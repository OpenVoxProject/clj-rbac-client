(def i18n-version "1.0.3")
(def jackson-version "2.20.1")

(defproject org.openvoxproject/rbac-client "1.2.5-SNAPSHOT"
  :description "Tools for interacting with PE RBAC"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}


  ;; Generally, try to keep version pins in :managed-dependencies and the libraries
  ;; this project actually uses in :dependencies, inheriting the version from
  ;; :managed-dependencies. This prevents endless version conflicts due to deps of deps.
  ;; Renovate should keep the versions largely in sync between projects.
  :managed-dependencies [[org.clojure/clojure "1.12.4"]
                         [org.clojure/tools.nrepl "0.2.13"]

                         [cheshire "5.13.0"]
                         [com.fasterxml.jackson.core/jackson-core ~jackson-version]
                         [com.fasterxml.jackson.core/jackson-databind ~jackson-version]
                         [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor ~jackson-version]
                         [com.fasterxml.jackson.dataformat/jackson-dataformat-smile ~jackson-version]
                         [com.fasterxml.jackson.module/jackson-module-afterburner ~jackson-version]
                         ;; For some reason, this one doesn't follow the same versioning convention
                         ;; for 2.20 as the others. Set this back to ~jackson-version if it does
                         ;; for the next release.
                         [com.fasterxml.jackson.core/jackson-annotations "2.20"]
                         [commons-codec "1.20.0"]
                         [commons-io "2.21.0"]
                         [org.openvoxproject/ring-middleware "2.1.3"]
                         [org.openvoxproject/http-client "2.2.3"]
                         [org.openvoxproject/i18n ~i18n-version]
                         [org.bouncycastle/bcpkix-jdk18on "1.83"]
                         [org.openvoxproject/kitchensink "3.5.5"]
                         [org.openvoxproject/kitchensink "3.5.5" :classifier "test"]
                         [org.openvoxproject/trapperkeeper "4.3.2"]
                         [org.openvoxproject/trapperkeeper "4.3.2" :classifier "test"]
                         [org.openvoxproject/trapperkeeper-webserver-jetty10 "1.1.4"]
                         [org.openvoxproject/trapperkeeper-webserver-jetty10 "1.1.4" :classifier "test"]
                         [ring/ring-core "1.14.2"]
                         [ring/ring-codec "1.3.0"]
                         [ring/ring-json "0.5.1"]
                         [slingshot "0.12.2"]]

  :dependencies [[org.clojure/clojure]
                 [org.openvoxproject/http-client]
                 [org.openvoxproject/i18n]
                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/ring-middleware]
                 [org.openvoxproject/trapperkeeper]
                 [ring/ring-core]
                 [ring/ring-json]
                 [slingshot]]

  :pedantic? :abort
  :profiles {:dev {:dependencies [[org.bouncycastle/bcpkix-jdk18on]
                                  [org.openvoxproject/kitchensink :classifier "test"]
                                  [org.openvoxproject/trapperkeeper :classifier "test"]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty10]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty10 :classifier "test"]
                                  ; transitive dependency
                                  [org.clojure/tools.nrepl]]}
             :testutils {:source-paths ^:replace  ["test"]}}

  :plugins [[org.openvoxproject/i18n ~i18n-version]]

  :classifiers  [["test" :testutils]]

  :test-paths ["test"]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]])
