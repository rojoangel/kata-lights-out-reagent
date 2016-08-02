(ns kata-lights-out.core
    (:require [reagent.core :as reagent :refer [atom]]))

;; -------------------------
;; Views

(def lights (atom [[1 1 1][1 1 1][1 1 1]]))

(defn neighbors [[x0 y0]]
  (for [x (range 3)
    y (range 3) :when (or (and (= y0 y) (= 1 (Math/abs (- x0 x))))
                          (and (= x0 x) (= 1 (Math/abs (- y0 y))))
                          )]
    [x y])
)

(defn flip-light [light]
  (if (= light 0) 1 0)
)

(defn flip [lights pos]
  (update-in lights pos flip-light)
)

(defn flip-neighbors [pos lights]
  (->> pos
       neighbors
       (cons pos)
       (reduce flip lights))
)

(defn light-component [position-x position-y light]
  [:button {:on-click #(swap! lights (partial flip-neighbors [position-x position-y]))} light]
)

(defn row-component [position-x row]
  [:div (map-indexed (partial light-component position-x) row)]
)

(defn home-page []
  [:div [:h2 "kata-lights-out"]
  (map-indexed row-component @lights)
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))


