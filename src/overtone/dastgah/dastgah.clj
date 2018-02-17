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

(definst qq [freq 149] (saw freq))

(definst aa [freq 170] (saw freq)) ;; random

(definst zz [freq 196] (saw freq)) ;; random

(definst ww [freq 300] (saw freq))

(definst ss [freq 417] (saw freq)) ;; random

(definst xx [freq 467] (saw freq)) ;; random

(definst ee [freq 500] (saw freq))

(definst dd [freq 558] (saw freq)) ;; random

(definst cc [freq 608] (saw freq)) ;; random

(definst rr [freq 702] (saw freq))

(definst tt [freq 783] (saw freq))

(definst yy [freq 985] (saw freq))

(definst uu [freq 1200] (saw freq))


(def shoor {
             :q 149
             :w 300
             :e 500
             :r 702
             :t 783
             :y 985
             :u 1200
             })


(defn swng []

  (doto (javax.swing.JFrame.)
        (.addKeyListener (proxy [java.awt.event.KeyListener] []

                                (keyPressed [e] (if (=(.getKeyCode e)java.awt.event.KeyEvent/VK_Q) (qq))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_A) (aa))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_Z) (zz))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_W) (ww))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_S) (ss))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_X) (xx))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_E) (ee))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_D) (dd))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_C) (cc))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_R) (rr))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_T) (tt))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_Y) (yy))

                                            (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_U) (uu))

                                            )

                                (keyReleased [e] ;;(if (=(.getKeyCode e)java.awt.event.KeyEvent/VK_Q) (kill qq))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_A) (kill aa))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_Z) (kill zz))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_W) (kill ww))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_S) (kill ss))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_X) (kill xx))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_E) (kill ee))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_D) (kill dd))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_C) (kill cc))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_R) (kill rr))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_T) (kill tt))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_Y) (kill yy))

                                             (if (= (.getKeyCode e) java.awt.event.KeyEvent/VK_U) (kill uu))

                                             )

                                (keyTyped [e] (println (.getKeyChar e) " key typed"))))
        (.setFocusable true)
        (.setVisible true)))



