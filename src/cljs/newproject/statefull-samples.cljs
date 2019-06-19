(ns newproject.statefull-samples
  (:require
   [reagent.core :as reagent]
   [clerk.core :as clerk]
   [accountant.core :as accountant]
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

(def click-count (reagent/atom 0))

(defn counting-component []
  [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 1"]
   [:div "The atom " [:code "click-count"] " has value: "
     @click-count ". "
     [:input {:type "button" :value "Click me!"
              :on-click #(swap! click-count inc)}]]])

;; ---------------------
;; Sample 2

(defn timer-component []
  (let [seconds-elapsed (reagent/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 1000)
      [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 2"]
        [:p "Seconds Elapsed: " @seconds-elapsed]])))

;; --------------------
;; Sample 3

(defn atom-input [value]
  [:input {:type "text"
   :value @value
   :on-change #(reset! value (-> % .-target .-value))}])

(defn shared-state []
  (let [val (reagent/atom "foo")]
    (fn []
      [:div
        [:p "The value is now: " @val]
        [:p "Change it here: " [atom-input val]]])))

(defn sample-3 []
  [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}} [:strong "Sample 3"]
    [shared-state]])

;; ---------------------
;; Sample Page rendering

(defn statefull-samples-page []
 (fn []
   [:span.main
     [:h1 {:style {:font-size "1.5rem"}} "The statefull samples of this project:"]
     [:ul
       [:li
           [counting-component]]
       [:li
           [timer-component]]
       [:li
           [sample-3]]
    [:a {:href (path-for :index)} "Back"]]]))
