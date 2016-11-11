(ns lang-utils.core)

(defn select-values
  "http://blog.jayfields.com/2011/01/clojure-select-keys-select-values-and.html"
  [map ks]
  (remove nil? (reduce #(conj %1 (map %2)) [] ks)))

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
  [pred coll]
  (when (seq coll)
    (let [x (first coll)]
      (or (and (pred x) x) (recur pred (next coll))))))

; (some# even? [1 2 3 4]) => 2

(defn find# [pred]
  (partial (fn [pred x] (and (pred x) x)) pred))

; (some (find# even?) [1 2 3 4]) =>2

(defn seek [pred coll]
  (reduce (fn [_ x] (when (pred x) (reduced x))) nil coll))

; (seek even? [1 2 3 4]) => 2
