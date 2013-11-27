
staload F1 = "filename1.sats"
staload F2 = "filename2.sats"
staload F3 = "filename3.sats"


staload "json_simple.sats"

staload _(*anon*) = "prelude/DATS/list.dats"
staload _(* anon *) = "prelude/DATS/array0.dats"




staload _ = "prelude/DATS/list0.dats"

staload "test_cvt.sats"

// ====== to be implemented ==========



// ====== implemented below ==========


// ====== implementation ==========

implement jsonize_T1 (x) =
case+ x of
| $F1.cons1 (__e1,__e2) => let
  val __arr = array0_make_elt (3, JSONnul ())
  val () = __arr[0] := JSONstring ("cons1")
  val () = __arr[1] := jsonize_T2y(__e1)
  val () = __arr[2] := let
    val __len = list_length (__e2)

    val __arr = array0_make_elt (size_of_int (__len), JSONnul ())

    fun loop (arr: array0 (jsonVal), xs: List (List ($F2.T2x)), n: int): void =
    case+ xs of
    | list_nil () => ()
    | list_cons (x, xs) => let
      val jx = 
        let
          val __len = list_length (x)

          val __arr = array0_make_elt (size_of_int (__len), JSONnul ())

          fun loop (arr: array0 (jsonVal), xs: List ($F2.T2x), n: int): void =
          case+ xs of
          | list_nil () => ()
          | list_cons (x, xs) => let
            val jx = 
              jsonize_T2x(x)
            val () = arr[n] := jx
          in
            loop (arr, xs, n + 1)
          end
          val () = loop (__arr, x, 0)
        in
          JSONarray (__arr)
        end
      val () = arr[n] := jx
    in
      loop (arr, xs, n + 1)
    end
    val () = loop (__arr, __e2, 0)
  in
    JSONarray (__arr)
  end
in
  JSONarray (__arr)
end
| $F1.cons2 (__e1,__e2) => let
  val __arr = array0_make_elt (3, JSONnul ())
  val () = __arr[0] := JSONstring ("cons2")
  val () = __arr[1] := jsonize_T2y(__e1)
  val () = __arr[2] := let
    val __jp_lst = list0_nil ()

    val __name = "a1"
    val __v = __e2.a1
    val __value = jsonize_T2x(__v)
    val __p = '(__name, __value)
    val __jp_lst = list0_cons (__p, __jp_lst)

    val __name = "a2"
    val __v = __e2.a2
    val __value = jsonize_T2y(__v)
    val __p = '(__name, __value)
    val __jp_lst = list0_cons (__p, __jp_lst)


    val __jp_lst = list0_reverse(__jp_lst)
    val __ret = JSONobject (__jp_lst)
  in
    __ret
  end
in
  JSONarray (__arr)
end
| $F1.cons3 (__e1) => let
  val __arr = array0_make_elt (2, JSONnul ())
  val () = __arr[0] := JSONstring ("cons3")
  val () = __arr[1] := jsonize_int (__e1)
in
  JSONarray (__arr)
end

implement jsonize_T2x(x) = 
  jsonize_int (x)

implement jsonize_T2y(x) = 
  jsonize_string (x)

implement jsonize_T2 (x) =
let
  val __jp_lst = list0_nil ()

  val __name = "_getx"
  val __v = todoT2_getx(x)
  val __value = jsonize_int (__v)
  val __p = '(__name, __value)
  val __jp_lst = list0_cons (__p, __jp_lst)
  val __name = "_gety"
  val __v = todoT2_gety(x)
  val __value = jsonize_string (__v)
  val __p = '(__name, __value)
  val __jp_lst = list0_cons (__p, __jp_lst)

  val __jp_lst = list0_reverse(__jp_lst)
  val __ret = JSONobject (__jp_lst)
in
  __ret
end

implement jsonize_T3 (x) =
let
  val __jp_lst = list0_nil ()

  val __name = "T3_getx"
  val __v = $F3.T3_getx(x)
  val __value = jsonize_int (__v)
  val __p = '(__name, __value)
  val __jp_lst = list0_cons (__p, __jp_lst)
  val __name = "T3_gety"
  val __v = $F3.T3_gety(x)
  val __value = jsonize_T2y(__v)
  val __p = '(__name, __value)
  val __jp_lst = list0_cons (__p, __jp_lst)

  val __jp_lst = list0_reverse(__jp_lst)
  val __ret = JSONobject (__jp_lst)
in
  __ret
end