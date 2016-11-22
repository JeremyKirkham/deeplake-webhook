(ns source.github.core
  [:use source.core])

(defmethod process! "github"
  [event]
  "github multimethod!")
