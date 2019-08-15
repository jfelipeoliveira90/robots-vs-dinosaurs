(defproject robots-vs-dinosaurs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.0"]

                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.7.1"]

                 [korma "0.4.3"]
                 [org.postgresql/postgresql "42.2.6"]

                 [dire "0.5.4"]
                 [camel-snake-kebab "0.4.0"]
                 [org.clojure/tools.logging "0.4.1"]]

  :profiles {:user {:dependencies [[ring/ring-mock "0.4.0"]]}}

  :resource-paths ["resources"]

  :plugins [[com.github.metaphor/lein-flyway "6.0.0"]
            [lein-cloverage "1.1.1"]]

  :flyway {:driver   "org.postgresql.Driver"
           :url      "jdbc:postgresql://localhost:5432/robots_vs_dinosaurs"
           :user     "postgres"
           :password "postgres"}

  :repl-options {:init-ns robots-vs-dinosaurs.server})
