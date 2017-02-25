(define (problem unknown)
(:domain unknown)
(:objects 
          a
          b
          c
          d
          e
          f
          g
          jack
          bulldozer)
(:init 
       (mobile jack)
       (road a b)
       (road b a)
       (at jack a)
       (road e b)
       (bridge d b)
       (road g d)
       (person jack)
       (road b e)
       (road d g)
       (bridge c f)
       (road d f)
       (road f d)
       (road e a)
       (bridge f c)
       (at bulldozer e)
       (road a e)
       (road b c)
       (road f g)
       (road c b)
       (road g f)
       (road a c)
       (road c e)
       (vehicle bulldozer)
       (bridge b d))
(:goal (and
           (at bulldozer g)
           (at jack a)
           (exists (
?something) (and
                    (mobile ?something))))))
