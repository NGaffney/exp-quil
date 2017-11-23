(ns exp-quil.square-tiles
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [clojure.core.matrix :as mat]))

; I want to make a square of smaller squares and tile it.

(defn int->kw [x]
  "Takes and integer and returns it as a keyword"
  (-> x str keyword))

(def int-kw-seq
  (map int->kw (range)))

(def tst [[1 2] [3 4]])

(defn matrix-element [coll index-1 index-2]
  (nth (nth coll index-1) index-2))

(for [x [0 1]
      y [0 1]]
  {:x x :y y :val (matrix-element tst x y)})

(mat/to-nested-vectors (mat/array [[1 2] [3 4]]))

(defn setup []
  (q/frame-rate 60)
  (q/backgroud 255))

(def grid-size 20)

(def state {:matrix (vec
                      (repeatedly (* grid-size grid-size) #(rand-int 2)))})



(q/defsketch square-tiles
  :host "host"
  :size [200 200]
  :setup setup
  :draw draw)
