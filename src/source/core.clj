(ns source.core)

; Dispatch handler for the processing of events.
(defmulti process! (fn [e] (:datasource (meta e))))

; Dispatch handler for returning the events event.
(defmulti type? (fn [e] (:datasource (meta e))))

; Dispatch handler for returning the event action.
(defmulti action? (fn [e] (:datasource (meta e))))

; Dispatch handler for returning the event user.
(defmulti user? (fn [e] (:datasource (meta e))))
