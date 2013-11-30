
staload F1 = "filename1.sats"

staload "json_simple.sats"


dynload "json_simple.dats"

dynload "filename2.dats"
dynload "filename3.dats"

dynload "test_cvt.dats"
dynload "test_cvt_impl.dats"

dynload "test_cvt_aux.dats"

staload "test_cvt.sats"

implement main () = let
  val xs1 = list_cons (1, list_cons (2, list_nil))
  val xs2 = list_cons (3, list_cons (4, list_nil))
  val xs = list_cons (xs1, list_cons (xs2, list_nil))

  val t1  = $F1.cons1 ("aa", xs)
  val jv = jsonize_T1 (t1)
  val str = json_dump_string (jv)
  val () = println! str

  val xs = list_cons (1, list_cons (2, list_cons (3, list_nil)))
  val jv = jsonize_T5 (xs)
  val str = json_dump_string (jv)
  val () = println! str

  val record = '{x = 3, y = "ok"}
  val jv = jsonize_T6 (record)
  val str = json_dump_string (jv)
  val () = println! str

in
end
  


