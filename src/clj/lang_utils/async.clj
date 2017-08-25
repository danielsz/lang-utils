(ns lang-utils.async
  (:require [clojure.core.async :refer [chan thread >!! take!]]))

(defmacro with-async [body]
  `(let [c# (chan)]
    (take! c# identity)
    (thread (>!! c# ~body))))

(defmacro with-asyncp [body]
  `(let [c# (chan)
         p# (promise)]
     (thread (take! c# (fn [v#] (deliver p# v#)))
             @p#)
     (thread (>!! c# ~body))))
