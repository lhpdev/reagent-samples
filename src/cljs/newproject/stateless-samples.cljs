(ns newproject.stateless-samples
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reitit.frontend :as reitit]))

(def router
 (reitit/router
   [["/" :index]]))

(defn path-for [route & [params]]
 (if params
   (:path (reitit/match-by-name router route params))
   (:path (reitit/match-by-name router route))))

;; ---------------------
;; Sample 1

(defn sample-item []
 (fn []
   [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 1"]
    [:p "I am a component sample 1!"]
    [:p.someclass
     "I have " [:strong "bold"]
     [:span {:style {:color "red"}} " and red "] "text."]]))

;; ---------------------
;; Sample 2

(defn wrapper []
 (fn []
   [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 2"]
     [:p "I include simple-item 1."]
     [sample-item]]))

;; ---------------------
;; Sample 3

(defn hello-component [name]
 [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 3"]
   [:p "Hello, " name "!"]])

(defn say-hello []
 [hello-component "world"])

;; ---------------------
;; Sample 4

(defn lister [items]
 [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
 [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 4"]
   [:p ]"Here is a list:"
   [lister (range 3)]])

;; ---------------------
;; Sample Page rendering

(defn stateless-samples-page []
 (fn []
   [:span.main
     [:h1 {:style {:font-size "1.5rem"}} "The stateless samples of this project:"]
     [:ul
       [:li
           [sample-item]]
       [:li
           [wrapper]]
       [:li
           [say-hello]]
       [:li
           [lister-user]]]
    [:a {:href (path-for :index)} "Back"]]))
