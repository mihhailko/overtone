(ns overtone.dastgah.sampler)

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout))

(def sampler (atom {:interval nil
                    :samples []
                    :running false}))

(defn add-sample [smp] (swap! sampler update-in [:samples] conj smp))

(defn rest-and-vec [sth] (-> sth rest vec))

(defn del-sample [] (swap! sampler update-in [:samples] #(rest-and-vec %)))

(defn bck [rts]
  (let [ratas (rts @sampler)
        butlast-of (butlast ratas)
        last-of (last ratas)
        updte (conj butlast-of last-of)]
    (swap! sampler assoc rts (vec updte))))

(defn fwd [rts]
  (let [ratas (rts @sampler)
        rest-of (vec (rest ratas))
        first-of (first ratas)
        updte (conj rest-of first-of)]
    (swap! sampler assoc rts updte)))

(defn fwd-and-get-first [] (do
                             (fwd :samples)
                             (first (:samples @sampler))))

(defn bck-and-get-first [] (do
                             (bck :samples)
                             (first (:samples @sampler))))

(defn del-and-get-first []
  (do
    (del-sample)
    (first (:samples @sampler))))

(defn ui []
  (let [frame (JFrame. "Simple Sampler")
        first-sample-label (JLabel. (first (:samples @sampler)))
        new-sample-field (JTextField.)
        fwd-button (JButton. "Forward")
        bck-button (JButton. "Back")
        new-button (JButton. "Add")
        del-button (JButton. "Delete")]
    (.addActionListener
     fwd-button
     (reify ActionListener
       (actionPerformed
           [_ evt]
         (.setText first-sample-label (fwd-and-get-first))
         )))
    (.addActionListener
     bck-button
     (reify ActionListener
       (actionPerformed
           [_ evt]
         (.setText first-sample-label (bck-and-get-first))
         )))
    (.addActionListener
     new-button
     (reify ActionListener
       (actionPerformed
           [_ evt]
         (add-sample (.getText new-sample-field))
         )))
    (.addActionListener
     del-button
     (reify ActionListener
       (actionPerformed
           [_ evt]
         (.setText first-sample-label (del-and-get-first)))))
    (doto frame
      (.setLayout (GridLayout. 2 2 3 3))
      (.add first-sample-label)
      (.add fwd-button)
      (.add bck-button)
      (.add new-sample-field)
      (.add new-button)
      (.add del-button)
      (.setSize 300 80)
      (.setVisible true)
      )))












