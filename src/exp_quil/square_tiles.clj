(ns exp-quil.square-tiles
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [clojure.core.matrix :as mat]))

(map
  #(-> (/ % 10)
     float
     Math/floor
     int
     (mod 2))
  (range 50))

;; I want to make a square of smaller squares and tile it.
;; Define constants

(def tile-size
  "The dimensions of the tiles in pixels."
  30)

(def grid-size-tiles
  "The dimensions of the picture in tiles."
  [10 10])

(def grid-size
  "The dimensions of the picture in pixels"
  (map * grid-size-tiles (repeat tile-size)))

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
  (q/no-stroke)
  (q/background 255)
  (q/fill 0))

(def grid-size 20)

(defn do-matrix-indexed [f m]
  (do (mat/emap-indexed f m)))

(defn draw-matrix-element
  "Takes a matrix index (idx) and element (i) and draws it."
  [idx i]
  (let [[x y] idx]
    (println (str "x:" x ", y " y ", i:" i))))

(def tst2
  (let [[x y] grid-size-tiles]
    (build-matrix #(mod (+ %1 %2) 2) x y)))

(defn draw []
  (q/background 255)
  (do-matrix-indexed
    (fn [idx-tiles i]
      (let [idx (map (partial * tile-size) idx-tiles)
            [x y] idx]
        (q/fill (* 255 (- 1 i)))
        (q/rect x y tile-size tile-size)))
    tst2))

(q/defsketch square-tiles
  :setup setup
  :size grid-size
  :draw draw)
