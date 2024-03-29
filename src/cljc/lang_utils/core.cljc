(ns lang-utils.core)

(defn select-values
  "http://blog.jayfields.com/2011/01/clojure-select-keys-select-values-and.html"
  [map ks]
  (remove nil? (reduce #(conj %1 (map %2)) [] ks)))

(defn select-keys#
  "https://stackoverflow.com/questions/38893968/how-to-select-keys-in-nested-maps-in-clojure"
  [m paths]
  (into {} (map (fn [p] [(peek p) (get-in m p)])) paths))

(def select-nested-keys select-keys#)

(defn apply-values 
  "http://blog.jayfields.com/2011/01/clojure-select-keys-select-values-and.html"
  [map f & ks]
  (apply f (select-values map ks)))

(defn slice
  "Divide coll into n approximately equal slices."
  [n coll]
  (loop [num n, slices [], items (vec coll)]
    (if (empty? items)
      slices
      (let [size (Math/ceil (/ (count items) num))]
        (recur (dec num) (conj slices (subvec items 0 size)) (subvec items size))))))

(defn contains+?
  "Version of clojure.core/contains? that works with nested maps"
  [coll val]
  (let [sequence (tree-seq map? vals coll)]
    (some #(and (map? %) (contains? % val)) sequence)))

(defn some#
  "Usage: (some# even? [1 2 3 4]) => 2"
  [pred coll]
  (when (seq coll)
    (let [x (first coll)]
      (or (and (pred x) x) (recur pred (next coll))))))

(defn find#
  "Usage: (some (find# even?) [1 2 3 4]) =>2"
  [pred]
  (partial (fn [pred x] (and (pred x) x)) pred))

(defn seek
  "Returns first item from coll for which (pred item) returns true.
   Returns nil if no such item is present, or the not-found value if supplied.
  
  Usage: (seek even? [1 2 3 4]) => 2"
  ([pred coll] (seek pred coll nil))
  ([pred coll not-found]
    (reduce (fn [_ x]
              (if (pred x)
                (reduced x)
                not-found))
            not-found coll)))

(defn find-nested
  [m k]
  (->> (tree-seq map? vals m)
       (filter map?)
       (some k)))

(defn contains?-any
  "Returns true if map m contains any key defined in sequence xs"
  [m xs]
  (some #(contains? m %) xs))

(def contains-any? contains?-any)

(defn find-any
  "Returns first key found in map m supplied by sequence of keys xs"
  [m xs]
  (some #(when (contains? m %) %) xs))

