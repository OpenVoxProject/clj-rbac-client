(ns puppetlabs.rbac-client.test-core
  (:require [clojure.test :refer [deftest testing is]]
            [puppetlabs.rbac-client.core :as core]
            [puppetlabs.trapperkeeper.testutils.bootstrap :refer [with-app-with-config]]
            [puppetlabs.trapperkeeper.services.webserver.jetty10-service :refer [jetty10-service]]
            [puppetlabs.trapperkeeper.testutils.logging :refer [with-test-logging]]
            [puppetlabs.http.client.sync :refer [create-client]]
            [puppetlabs.rbac-client.test-server :as test-server]
            [slingshot.test]))

(def test-port 38924)

(deftest test-api-caller
  (with-test-logging
    (let [client (create-client {})
          handler (constantly {:status 200 :body "ok"})]
      (with-app-with-config app
        [jetty10-service (test-server/build-test-service handler "/")]
        {:webserver {:port test-port}}
        (is (= "ok"
               (:body (core/api-caller client (format "http://localhost:%s/" test-port) :get ""))))))

    (let [client (create-client {})
          handler (constantly {:status 500 :body "server error"})]
      (with-app-with-config app
        [jetty10-service (test-server/build-test-service handler "/")]
        {:webserver {:port test-port}}
        (is (= "server error" (:body (core/api-caller client (format "http://localhost:%s/" test-port) :get ""))))
        (is (thrown+? [:kind :puppetlabs.rbac-client/status-error]
                      (:body (core/api-caller client (format "http://localhost:%s/" test-port) :get "" {:status-errors true}))))))

    (let [client (create-client {})]
      (is (thrown+? [:kind :puppetlabs.rbac-client/connection-failure]
                    (:body (core/api-caller client (format "http://localhost:%s/" test-port) :get "")))))))

(deftest test-json-api-caller
  (with-test-logging
    (let [client (create-client {})
          handler (test-server/make-json-handler {:status 200
                                                  :body {:foo 1 :bar {:baz 2}}})]
      (with-app-with-config app
        [jetty10-service (test-server/build-test-service handler "/")]
        {:webserver {:port test-port}}
        (let [response (core/json-api-caller client (format "http://localhost:%s/foo" test-port) :get "/bar?p=1")]
          (is (= 200 (:status response)))
          (is (= 1 (get-in response [:body :foo])))
          (is (= "application/json" (get-in response [:body :_request :headers :accept])))
          (is (= "/foo/bar" (get-in response [:body :_request :uri])))
          (is (= "1" (get-in response [:body :_request :params :p]))))))

    (let [client (create-client {})
          handler (test-server/make-json-handler {:status 400
                                                  :body {:kind :invalid
                                                         :msg "oops"}})]
      (with-app-with-config app
        [jetty10-service (test-server/build-test-service handler "/")]
        {:webserver {:port test-port}}
        (is (thrown+? [:kind :invalid]
                      (core/json-api-caller client (format "http://localhost:%s/" test-port) :get "" {:throw-body true})))
        (is (thrown+? [:kind :puppetlabs.rbac-client/status-error]
                      (core/json-api-caller client (format "http://localhost:%s/" test-port) :get "" {:status-errors true})))))))
