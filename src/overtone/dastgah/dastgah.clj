(ns overtone.dastgah.dastgah
  (:use [clojure.string]
        [overtone.api]))

(defn start [] (immigrate-overtone-api))

(start)

 ;;--> Please boot a server to start making noise:
 ;;   * (boot-server)             ; boot default server (honours config)
 ;;   * (boot-internal-server)    ; boot an internal server
 ;;   * (boot-external-server)    ; boot an external server
 ;;   * (connect-external-server) ; connect to an existing external server

(boot-external-server)

(def shoor {
            :q 149
            :w 300
            :e 500
            :r 702
            :t 783
            :y 985
            :u 1200
            })


(def state (atom {:freq 249
                  :nar-freq 354}))





