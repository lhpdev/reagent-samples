
(ns newproject.about
  (:require
   [reagent.core :as reagent :refer [atom]]))

(defn about-page []
   (fn [] [:div
           [:h1 "About"]
           [:p "This is just the about page"]]))
