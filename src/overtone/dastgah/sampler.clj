(ns overtone.dastgah.sampler)

(def sampler (atom {:start-time nil}))

(defn start-time [] (swap! sampler assoc-in [:start-time] (.(new java.util.Date) (getTime))))

(def steps (atom [1 2 3 4 5]))

(defn next-step [] (reset! steps (take 5 (rest (cycle (deref steps))))))

(defn next-when-time-ends [] (if (> (- (. (new java.util.Date) (getTime)) 1000) (:start-time @sampler))
                    (do (next-step) (start-time))
                    nil))

(defn systime [] (System/currentTimeMillis))

(defn time-diff [tme] (- (systime) tme))

(def intervals [2000 4000 6000 8000])

(:start-time @sampler)

(start-time)

(defn playing? [] (some #(= false %) (map #(>= (time-diff (:start-time @sampler)) %) intervals)))

(playing?)



(def clock (atom {:updated false
                  :data nil}))

(defn run-clock []
  (do
    (swap! clock assoc :running? true)
    (println "clock active")))

(deref clock)

(defn print-time []
  (eternal-printer))

(print-time)

(defn unrun []
  (if (> (time-diff (:start-time @sampler)) 10000)
    (swap! clock assoc :running? false)
    (str "stuff printed yay")))

(> (time-diff (:start-time @sampler)) 10000)

(unrun)

(repeatedly 10 unrun)

(run-clock)

(deref clock)

(start-time)





