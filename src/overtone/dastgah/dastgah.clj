(ns overtone.dastgah.dastgah
  (:use [overtone.api] [clojure.string]))

(overtone.api/immigrate-overtone-api)

(defonce __PRINT-CONNECT-HELP__
  (when-not (server-connected?)
    (println "--> Please boot a server to start making noise:
    * (boot-server)             ; boot default server (honours config)
    * (boot-internal-server)    ; boot an internal server
    * (boot-external-server)    ; boot an external server
    * (connect-external-server) ; connect to an existing external server
")))

(defn dast [] (println "Salam"))

(boot-external-server)

(definst oo [freq 440] (saw freq))

(definst uu [freq 330] (saw freq))

(dast)


(defn swng []

  (doto (javax.swing.JFrame.)
        (.addKeyListener (proxy [java.awt.event.KeyListener] []

                                (keyPressed [e] (if (=(.getKeyCode e)java.awt.event.KeyEvent/VK_D) (uu 440) (oo 330))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_K) (oo 578) (uu 867)))

                                (keyReleased [e] (if (=(.getKeyCode e)java.awt.event.KeyEvent/VK_D) (kill uu) (oo 965))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_K) (kill oo) (uu 354)))

                                (keyTyped [e] (println (.getKeyChar e) " key typed"))))
        (.setFocusable true)
        (.setVisible true)))



