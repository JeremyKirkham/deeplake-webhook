(ns source.core)

(defmulti process! (fn [e] (:datasource (meta e))))
