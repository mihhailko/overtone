(ns overtone.dastgah.core
  (:use [seesaw core tree keymap])
  (:import [java.io File]
           [javax.swing.filechooser FileSystemView])
  (:require [overtone.dastgah.dastgah :as d]))

(def tree-model
  (simple-tree-model
   #(.isDirectory %)
   (fn [f] (filter #(.isDirectory %) (.listFiles f)))
   (File. "/")))

(def chooser (javax.swing.JFileChooser.))

(defn render-file-item [renderer {:keys [value]}]
  (config! renderer
           :text (.getName value)
           :icon (.getIcon chooser value)))

(defn make-frame []
  (frame :title "File Explorer" :width 600 :height 500
		 :on-close :dispose
         :content
         (border-panel :border 5 :hgap 5 :vgap 5
                       :north (label :id :current-dir :text "Location")
                       :center (left-right-split
                                (scrollable (tree
                                             :id :tree
                                             :model tree-model
                                             :renderer render-file-item))
                                (scrollable (listbox
                                             :id :list
                                             :renderer render-file-item))
                                :divider-location 1/3)
                       :south (label :id :status :text "Ready"))))

(def synth-keys ["Q" "W" "E" "R" "T" "Y"])

(def nar-synth-keys ["A" "S" "D" "F" "G" "H"])

(def f (make-frame))

(def freq-opts {:q 100 :w 200 :e 300 :r 400 :t 500 :y 600
                :a 130 :s 220 :d 340 :f 430 :g 580 :h 620})

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


(defn samplfn [n]
  n)

(def samples-old (atom [(samplfn "1") (samplfn "2")]))

(def samples (atom [250 400 600]))

(defn next-sample [samples]
  (reset! samples (take (count @samples) (rest (cycle @samples)))))

(def stf (atom [1 2 3 4]))

(def state (atom {:running? true}))


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

(defn run-sampler [e] (stuff samples 20))

(def other-samples (atom [350 420 600 210]))

(defn run-another-sampler [e] (nar-stuff other-samples 30))

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

(-main)





















