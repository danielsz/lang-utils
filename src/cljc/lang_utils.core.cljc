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
  "traverse a map to find a key"
  [coll val]
  (let [sequence (tree-seq map? vals coll)]
    (not-empty (filter #(and (map? %) (contains? % val)) sequence))))
