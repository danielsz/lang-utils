(set-env! :source-paths   #{"src/cljc"}
          :resource-paths   #{"src/cljc"})

(task-options!
 pom {:project 'org.danielsz/lang-utils
      :version "0.1.0-SNAPSHOT"
      :scm {:name "git"
            :url "https://github.com/danielsz/lang-utils"}})

(deftask build
  []
  (comp (pom) (jar) (install)))

(deftask push-release
  []
  (comp
   (build)
   (push :repo "clojars")))
