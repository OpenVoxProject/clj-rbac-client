(defproject org.openvoxproject/rbac-client "1.1.6-SNAPSHOT"
  :description "Tools for interacting with PE RBAC"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :parent-project {:coords [org.openvoxproject/clj-parent "7.4.1-SNAPSHOT"]
                   :inherit [:managed-dependencies]}

  :dependencies [[org.clojure/clojure]
                 [ring/ring-core]
                 [ring/ring-json]
                 [org.openvoxproject/ring-middleware]
                 [slingshot]
                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/http-client]
                 [org.openvoxproject/trapperkeeper]
                 [org.openvoxproject/i18n]]

  :pedantic? :abort
  :profiles {:dev {:dependencies [[org.openvoxproject/kitchensink :classifier "test"]
                                  [org.openvoxproject/trapperkeeper :classifier "test"]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty9]
                                  [org.openvoxproject/trapperkeeper-webserver-jetty9 :classifier "test"]
                                  [org.bouncycastle/bcpkix-jdk15on]
                                  ; transitive dependency
                                  [org.clojure/tools.nrepl "0.2.13"]]}
             :testutils {:source-paths ^:replace  ["test"]}}

  :plugins [[lein-parent "0.3.7"]
            [org.openvoxproject/i18n "0.8.0"]]

  :classifiers  [["test" :testutils]]

  :test-paths ["test"]

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]])
