(ns coloured-balls.core
  (:use [rosado.processing]
        [rosado.processing.applet])
  (:gen-class))

;; here's a function which will be called by Processing's (PApplet)
;; draw method every frame. Place your code here. If you eval it
;; interactively, you can redefine it while the applet is running and
;; see effects immediately

(defn draw-ball [ball]
	(fill (:red ball) (:green ball) (:blue ball))
	(ellipse (:x ball) (:y ball) (:radius ball) (:radius ball)))

(def our-red (rand-int 256))
(def our-blue (rand-int 256))
(def our-green (rand-int 256))
(def our-radius (rand-int 70))

(defn make-ball [x y vel-x vel-y]
	{:x x :y y :red our-red :blue our-blue :green our-green :radius (+ 1 our-radius) :velocity-x vel-x :velocity-y vel-y})

(def our-ball (make-ball 200 200 2 2))

(defn move-ball [the-ball-were-using]
  (let [new-vel-x (if (< (:x the-ball-were-using) 400) (:velocity-x the-ball-were-using) (- (:velocity-x the-ball-were-using)))
        new-vel-y (if (< (:y the-ball-were-using) 400) (:velocity-y the-ball-were-using) (- (:velocity-y the-ball-were-using)))]
  (make-ball (+ new-vel-x (:x the-ball-were-using)) (+ new-vel-y (:y the-ball-were-using)) new-vel-x new-vel-y
  )))

(def sequence-of-balls (atom (iterate move-ball our-ball)))

(defn draw
  "Example usage of with-translation and with-rotation."
  []
  (background 0 0 0)
  (draw-ball (first @sequence-of-balls)
           )
  (swap! sequence-of-balls rest)
 )

(defn setup []
  "Runs once."
  (smooth)
  (no-stroke)
  (fill 226)
  (framerate 60))

;; Now we just need to define an applet:

(defapplet balls :title "Coloured balls"
  :setup setup :draw draw :size [400 400])

(defn -main [& args]
 (run balls))

