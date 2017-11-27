(set-env! :source-paths   #{"src/cljc" "src/clj"}
          :resource-paths   #{"src/cljc" "src/clj"}
          :dependencies '[[org.clojure/core.async "0.3.443"]])

(task-options!
 push {:repo-map {:url "https://clojars.org/repo/"}}
 pom {:project 'org.danielsz/lang-utils
      :version "0.1.1-SNAPSHOT"
      :scm {:name "git"
            :url "https://github.com/danielsz/lang-utils"}})

(deftask build
  []
  (comp (pom) (jar) (install)))

(deftask push-release
  []
  (comp
   (build)
   (push)))
