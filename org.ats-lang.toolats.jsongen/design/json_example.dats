

staload "./../SATS/json_simple.sats"
staload _(*anon*) = "prelude/DATS/list.dats"
staload _(*anon*) = "prelude/DATS/array0.dats"

fun jsonize_int (x: int): jsonVal = JSONint (llint_of_int(x))

fun jsonize_double (x: double): jsonVal = JSONdouble (x)

fun jsonize_string (x: string): jsonVal = JSONstring (x)

fun jsonize_list (x: List (int)): jsonVal = 
let
  val len = list_length (x)
  val arr = array0_make_elt (size_of_int (len), JSONnul ())

  fun loop (arr: array0 (jsonVal), xs: List (int), n: int): void =
  case+ xs of
  | list_nil () => ()
  | list_cons (x, xs) => let
    val jx = jsonize_int (x)
    val () = arr[n] := jx
  in
    loop (arr, xs, n + 1)
  end
  val () = loop (arr, x, 0)
in
  JSONarray (arr)
end

fun jsonize_record (x: '{e1 = int, e2 = List (int)}): jsonVal = 
let
  val jp_lst = list0_nil ()

  val name = "e2"
  val value = jsonize_anon (x.e2) where {
    fun jsonize_anon (x: List (int)): jsonVal = let
      val len = list_length (x)
      val arr = array0_make_elt (size_of_int (len), JSONnul ())
    
      fun loop (arr: array0 (jsonVal), xs: List (int), n: int): void =
      case+ xs of
      | list_nil () => ()
      | list_cons (x, xs) => let
        val jx = jsonize_int (x)
        val () = arr[n] := jx
      in
        loop (arr, xs, n + 1)
      end
      val () = loop (arr, x, 0)
    in
      JSONarray (arr)
    end
  }
  val p = '(name, value)
  val jp_lst = list0_cons (p, jp_lst)

  val name = "e1"
  val value = jsonize_int (x.e1)
  val p = '(name, value)
  val jp_lst = list0_cons (p, jp_lst)

  val ret = JSONobject (jp_lst)
in
  ret
end

datatype Foo =
| cons1 of (int, int)
| cons2 of (int)

fun jsonize_Foo (x: Foo): jsonVal =
case+ x of
| cons1 (e1, e2) => let
  val arr = array0_make_elt (3, JSONnul ())
  val () = arr[0] := JSONstring ("cons1")
  val () = arr[1] := jsonize_int (e1)
  val () = arr[2] := jsonize_int (e2)
in
  JSONarray (arr)
end
| cons2 (e1) => let
  val arr = array0_make_elt (2, JSONnul ())
  val () = arr[0] := JSONstring ("cons2")
  val () = arr[1] := jsonize_int (e1)
in
  JSONarray (arr)
end


