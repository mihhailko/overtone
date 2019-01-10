(ns overtone.dastgah.core
  (:use [seesaw core tree keymap])
  (:import [java.io File])
  (:require [overtone.dastgah.dastgah :as d]
            [overtone.dastgah.sampler :as s]))

(def data (atom {:slength 20
                 :snarlength 30}))

(def state (atom {:running? true}))

(def synth-keys ["Q" "W" "E" "R" "T" "Y"])

(def nar-synth-keys ["A" "S" "D" "F" "G" "H"])

(def f (s/ui))

(def freq-opts {:q 100 :w 300 :e 400 :r 500 :t 700 :y 800
                :a 200 :s 300 :d 500 :f 700 :g 900 :h 1000})

(d/definst broad-synth [freq (:freq @d/state)] (d/saw freq))

(d/definst nar-synth [freq (:nar-freq @d/state)] (d/saw freq))

(defn b [e] (d/ctl broad-synth :freq ((keyword (.getActionCommand e)) freq-opts)))

(defn c [e] (d/ctl nar-synth :freq ((keyword (.getActionCommand e)) freq-opts)))

(defn start-broad [e] (broad-synth))

(defn start-nar [e] (nar-synth))

(defn stop-synths [e] (d/stop))

(def map-broad-keys (map #(map-key f % b) synth-keys))

(def map-nar-keys (map #(map-key f % c) nar-synth-keys))

(defn read-each [stuff]
  (loop [stuff stuff]
    (first stuff)
    (if (not (empty? stuff))
      (recur (rest stuff))
      (println "done"))))

(defn broad-sample [interval]
  (future
    (broad-synth)
    (Thread/sleep interval)
    (d/kill broad-synth)))

(defn nar-sample [interval]
  (future
    (nar-synth)
    (Thread/sleep interval)
    (d/kill nar-synth)))

(def samples (atom [250 400 600]))

(defn next-sample [samples]
  (reset! samples (take (count @samples) (rest (cycle @samples)))))

(def stf (atom [1 2 3 4]))

(defn stuff [samples times]
  (future
    (loop [t times]
      (broad-sample (first @samples))
      (next-sample samples)
      (Thread/sleep 200)
      (println (str "times left: " t))
      (if (and (> t 1) (:running? @state))
        (recur (dec t))
        (println "done")))))

(defn nar-stuff [samples times]
  (future
    (loop [t times]
      (nar-sample (first (deref samples)))
      (next-sample samples)
      (Thread/sleep 200)
      (println (str "times left: " t))
      (if (and (> t 1) (:running? @state))
        (recur (dec t))
        (println "done")))))

(defn stopwatch [times]
  (future
    (loop [t times]
      (println "another second...")
      (Thread/sleep 1000)
      (if (> t 1)
        (recur (dec t))
        (println "done")))))

;;(stopwatch 60)

(defn run-sampler [e] (stuff samples (:slength @data)))

(def other-samples (atom [350 420 600 210]))

(defn run-another-sampler [e] (nar-stuff other-samples (:snarlength @data)))

(map-key f "1" start-broad)

(map-key f "2" start-nar)

(map-key f "F1" (read-each map-broad-keys))

(map-key f "F2" (read-each map-nar-keys))

(map-key f "F3" stop-synths)

(map-key f "F5" run-sampler)

(map-key f "F7" run-another-sampler)

(defn -main [& args]
  (-> f
    pack!
    show!))

;;(-main)





















