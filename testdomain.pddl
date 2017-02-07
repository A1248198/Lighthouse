(define (domain unknown)
(:predicates (= ?a ?b  )(at ?a ?b  )(bridge ?a ?b  )(driving ?a ?b  )(mobile ?a  )(person ?a  )(road ?a ?b  )(vehicle ?a  ))

(:action drive

   :parameters
    (?what
    ?from
    ?to
    )

   :precondition
    ( and
        (road ?from  ?to )
        (at ?what  ?from )
        (mobile ?what )
        ( not (= ?from  ?to ))
    )
   :effect
   (and
        ( not (at ?what  ?from ))
        (at ?what  ?to )
    )
)
(:action cross

   :parameters
    (?what
    ?from
    ?to
    )

   :precondition
    ( and
        (bridge ?from  ?to )
        (at ?what  ?from )
        (mobile ?what )
        ( not (= ?from  ?to ))
    )
   :effect
   (and
        ( not (at ?what  ?from ))
        (at ?what  ?to )
    )
)
(:action board

   :parameters
    (?who
    ?where
    ?what
    )

   :precondition
    ( and
        (at ?who  ?where )
        (person ?who )
        (vehicle ?what )
        (at ?what  ?where )
        ( not (= ?who  ?what ))
    )
   :effect
   (and
        ( not (at ?who  ?where ))
        ( not (mobile ?who ))
        (driving ?who  ?what )
        (mobile ?what )
    )
)
(:action disembark

   :parameters
    (?who
    ?where
    ?what
    )

   :precondition
    ( and
        (person ?who )
        (vehicle ?what )
        (driving ?who  ?what )
        (at ?what  ?where )
        ( not (= ?who  ?what ))
    )
   :effect
   (and
        ( not (driving ?who  ?what ))
        ( not (mobile ?what ))
        (at ?who  ?where )
        (mobile ?who )
    )
))