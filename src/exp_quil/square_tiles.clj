(ns exp-quil.square-tiles
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [clojure.core.matrix :as mat]))

;; I want to make a square of smaller squares and tile it.
;; Define constants

(def tile-size
  "The dimensions of the tiles in pixels."
  30)

(def grid-size
  "The dimensions of the picture in pixels"
  [300 300])

;; build the thing
(defn build-matrix
  "Build a matrix of width w and height h by applying a function f (f wi hi).
  wi and hi are each value in (range w/h)."
  [f w h]
  (mat/matrix
   (for [wi (range w)]
     (for [hi (range h)]
       (f wi hi)))))

; draw the thing

(defn setup []
  ; (q/frame-rate 60)
  (q/background 255))

(defn do-matrix-indexed [f m]
  (do (mat/emap-indexed f m)))

(defn build-element
  [x s]
  (-> (/ x s)
    float
    Math/floor
    int
    (mod 2)))

(def tst3
  (let [[x y] grid-size
        elem-fn #(build-element % tile-size)]
    (build-matrix
      (fn [& args]
        (apply bit-xor
          (map elem-fn args)))
      x y)))

(defn greyerise
  "Transform a value in the range 0 1 to an RGB grayscale image."
  [x]
  (q/color (* 255 (- 1 x))))

(defn draw-fn []
  (let [img (q/create-image
              (first grid-size)
              (second grid-size)
              :rgb)]
    (do-matrix-indexed
      (fn [idx i]
        (let [[x y] idx]
          (q/set-pixel img x y (greyerise i))))
      tst3)
    (q/image img 0 0)))

(q/defsketch square-tiles
  :setup setup
  :size grid-size
  :draw draw-fn)
