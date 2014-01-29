(* ****** ****** *)
//
#include "share/atspre_define.hats"
#include "share/atspre_staload.hats"
//
(* ****** ****** *)

fun fact1 (x: int): int =
if x > 1 then x * fact1 (x - 1)
else "1"

val _ = print ("Hello, world!\n")

implement main0 () = ()



