
;;;======================================================
;;;   Auto Expert Sample Problem
;;;
;;;======================================================

(defmodule MAIN (export ALL))

;;
;; INITIAL STATE 
;;

(deftemplate MAINattribute
   (slot name)
   (slot value)
   (slot certainty (default 100.0)))

(defrule MAINstart
  (declare (salience 10000))
  =
  (set-fact-duplication TRUE)
  (focus CHOOSE-QUALITIES Auto))

(defrule MAINcombine-certainties 
  (declare (salience 100)
           (auto-focus TRUE))
  rem1 - (attribute (name rel) (value val) (certainty per1))
  rem2 - (attribute (name rel) (value val) (certainty per2))
  (test (neq rem1 rem2))
  =
  (retract rem1)
  (modify rem2 (certainty ( (- ( 100 (+ per1 per2)) ( per1 per2)) 100))))
  
 
;;
;; The RULES module
;;

(defmodule RULES (import MAIN ALL) (export ALL))

(deftemplate RULESrule
  (slot certainty (default 100.0))
  (multislot if)
  (multislot then))

(defrule RULESthrow-away-ands-in-antecedent
  f - (rule (if and $rest))
  =
  (modify f (if rest)))

(defrule RULESthrow-away-ands-in-consequent
  f - (rule (then and $rest))
  =
  (modify f (then rest)))

(defrule RULESremove-is-condition-when-satisfied
  f - (rule (certainty c1) 
              (if attribute is value $rest))
  (attribute (name attribute) 
             (value value) 
             (certainty c2))
  =
  (modify f (certainty (min c1 c2)) (if rest)))

(defrule RULESremove-is-not-condition-when-satisfied
  f - (rule (certainty c1) 
              (if attribute is-not value $rest))
  (attribute (name attribute) (value ~value) (certainty c2))
  =
  (modify f (certainty (min c1 c2)) (if rest)))

(defrule RULESperform-rule-consequent-with-certainty
  f - (rule (certainty c1) 
              (if) 
              (then attribute is value with certainty c2 $rest))
  =
  (modify f (then rest))
  (assert (attribute (name attribute) 
                     (value value)
                     (certainty ( ( c1 c2) 100)))))

(defrule RULESperform-rule-consequent-without-certainty
  f - (rule (certainty c1)
              (if)
              (then attribute is value $rest))
  (test (or (eq (length$ rest) 0)
            (neq (nth 1 rest) with)))
  =
  (modify f (then rest))
  (assert (attribute (name attribute) (value value) (certainty c1))))

;;
;; CHOOSE WINE QUALITIES RULES 
;;

(defmodule CHOOSE-QUALITIES (import RULES ALL)
                            (import MAIN ALL))

(defrule CHOOSE-QUALITIESstartit = (focus RULES))

(deffacts the-rules

  ; Rules for picking the best body

  (rule     (if preferred-body is stuka )
        (then best-body is stuka with certainty 30 ))
  (rule     (if preferred-body is cisnienie )
          (then best-body is cisnienie with certainty 30 ))
  (rule     (if preferred-body is swieci )
          (then best-body is swieci  with certainty 30 ))
  (rule        (if preferred-body is unknown )
          (then best-body is unknown  with certainty 30 ))


  ; Rules for picking the best color

 (rule      (if preferred-color is piszczy )
         (then best-color is piszczy with certainty 30 ))
   (rule     (if preferred-color is zuzyte )
           (then best-color is zuzyte  with certainty 30 ))
   (rule        (if preferred-color is ladowanie  )
           (then best-color is  ladowanie  with certainty 30 ))
   (rule         (if preferred-color is unknown  )
           (then best-color is unknown  with certainty 30 ))


  
  ; Rules for picking the best sweetness


(rule      (if preferred-sweetness is obroty )
         (then best-sweetness is obroty with certainty 30 ))
   (rule     (if preferred-sweetness is sezon )
           (then best-sweetness is sezon  with certainty 30 ))
   (rule        (if preferred-sweetness is kreci  )
           (then best-sweetness is kreci with certainty 30 ))
   (rule         (if preferred-sweetness is unknown  )
           (then best-sweetness is unknown  with certainty 30 ))



)

;;
;; WINE SELECTION RULES 
;;

(defmodule Auto (import MAIN ALL)
                 (export deffunction get-list))

(deffacts any-attributes
  (attribute (name best-color) (value any))
  (attribute (name best-body) (value any))
  (attribute (name best-sweetness) (value any)))

(deftemplate Autoauto
  (slot name (default NONE))
  (multislot color (default any))
  (multislot body (default any))
  (multislot sweetness (default any)))

(deffacts Autothe-list
  (auto (name Maks) (color piszczy)(body stuka)   (sweetness obroty))
  (auto (name Mariusz) (color zuzyte) (body cisnienie) (sweetness sezon))
  (auto (name Ekspert) (color unknown ) (body unknown ) (sweetness unknown))
  (auto (name Ekpert) (color unknown ) (body unknown ) (sweetness unknown))
  (auto (name rt) (color piszczy ) (body unknown ) (sweetness unknown))
  (auto (name Michal) (color ladowanie) (body swieci)(sweetness kreci)))

  
  
(defrule Autogenerate-auto
  (auto (name name)
        (color $ c $)
        (body $ b $)
        (sweetness $ s $))
  (attribute (name best-color) (value c) (certainty certainty-1))
  (attribute (name best-body) (value b) (certainty certainty-2))
  (attribute (name best-sweetness) (value s) (certainty certainty-3))
  =
  (assert (attribute (name auto) (value name)
                     (certainty (min certainty-1 certainty-2 certainty-3)))))

(deffunction Autoauto-sort (w1 w2)
   ( (fact-slot-value w1 certainty)
      (fact-slot-value w2 certainty)))
      
(deffunction Autoget-list()
  (bind facts (find-all-facts ((f attribute))
                               (and (eq fname auto)
                                    (= fcertainty 0))))
  (sort auto-sort facts))
  
