(ns exp-quil.square-tiles
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [clojure.core.matrix :as mat]))

; I want to make a square of smaller squares and tile it.

; build the thing
(defn build-matrix
  "Build a matrix of width w and height h by applying a function f (f wi hi).
  wi and hi are each value in (range w/h)."
  [f w h]
  (mat/matrix
   (for [wi (range w)]
     (for [hi (range h)]
       (f wi hi)))))

(build-matrix #(mod (+ %1 %2) 2) 3 3)

; draw the thing

(mat/emap-indexed #(str %1 " " %2) tst)

(mat/select tst 0 0)

(defn setup []
  (q/frame-rate 60)
  (q/backgroud 255))

(def grid-size 20)


(q/defsketch square-tiles
  :host "host"
  :size [200 200]
  :setup setup
  :draw draw)
