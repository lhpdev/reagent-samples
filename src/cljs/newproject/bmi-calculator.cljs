(ns newproject.bmi-calculator
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

(def bmi-data (reagent/atom {:height 180 :weight 80}))

(defn calc-bmi []
  (let [{:keys [height weight bmi] :as data} @bmi-data
        h (/ height 100)]
    (if (nil? bmi)
      (assoc data :bmi (/ weight (* h h)))
      (assoc data :weight (* bmi h h)))))

(defn slider [param value min max]
  [:input {:type "range" :value value :min min :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        (swap! bmi-data assoc param (.. e -target -value))
                        (when (not= param :bmi)
                          (swap! bmi-data assoc :bmi nil)))}])

(defn bmi-component []
  (let [{:keys [weight height bmi]} (calc-bmi)
        [color diagnose] (cond
                          (< bmi 18.5) ["orange" " underweight"]
                          (< bmi 25) ["inherit" " normal"]
                          (< bmi 30) ["orange" " overweight"]
                          :else ["red" " obese"])]
  [:div {:style {:padding "1rem" :margin "0.1rem" :background-color "#e8e8e8"}}
    [:h3 "BMI calculator"]
    [:div
      "Height: " (int height) "cm"
      [slider :height height 100 220]]
    [:div
      "Weight: " (int weight) "kg"
      [slider :weight weight 30 150]]
    [:div
      "BMI: " (int bmi) ""
      [:span {:style {:color color}} diagnose]
      [slider :bmi bmi 10 50]]]))

;; ---------------------
;; Sample Page rendering

(defn bmi-calculator-page []
 (fn []
   [:span.main
     [:h1 "BMI Calculator"]
     [:ul
       [:li
           [bmi-component]]
    [:a {:href (path-for :index)} "Back"]]]))
