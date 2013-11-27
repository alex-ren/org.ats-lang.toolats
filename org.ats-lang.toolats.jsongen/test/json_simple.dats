
staload "json_simple.sats"

staload JS = "jansson/SATS/jansson.sats"

staload _ = "prelude/DATS/array0.dats"

%{^
int ORInt(int x, int y)
{
    return x | y;
}

%}

(* ****** ****** *)
macdef isnz = $JS.JSONptr_isnot_null

local

fun json_simple_2_jansson (jv: jsonVal): $JS.JSONptr1 =
case+ jv of
| JSONnul () => $JS.json_null ()
| JSONint (v) => let
  extern castfn jsimple_int2json_int (x: json_int): $JS.json_int
  val ji = $JS.json_integer (jsimple_int2json_int (v)) 
  val () = assertloc (isnz (ji))
in
  ji
end
| JSONbool (b) => if b then $JS.json_true () else $JS.json_false ()
| JSONstring (str) => let
  val jstr = $JS.json_string (str)
  val () = assertloc (isnz (jstr))
in
  jstr
end
| JSONdouble (d) => let
  val jd = $JS.json_real (d)
  val () = assertloc (isnz (jd))
in
  jd
end
| JSONarray (arr) => let
  val len = array0_size (arr)
  val jarr = $JS.json_array ()
  val () = assertloc (isnz (jarr))

  fun loop (jarr: !($JS.JSONptr1), arr: array0 (jsonVal), ind: size_t, n: size_t): void =
    if (ind < n) then let
      val v = arr[ind]
      val jv = json_simple_2_jansson (v)
      val err = $JS.json_array_append_new (jarr, jv)
      val () = assertloc (err = 0)
    in
      loop (jarr, arr, ind + 1, n)
    end else ()

  val () = loop (jarr, arr, 0, len)
in
  jarr
end
| JSONobject (xs) => let
  val jobj = $JS.json_object ()
  val () = assertloc (isnz (jobj))

  fun loop (jobj: !($JS.JSONptr1), xs: labjsonValist): void =
  case+ xs of
  | list0_cons (labv, xs) => let
    val jv = json_simple_2_jansson (labv.1)
    val err = $JS.json_object_set_new (jobj, labv.0, jv)
    val () = assertloc (err = 0)
  in
    loop (jobj, xs)
  end
  | list0_nil () => ()
  
  val () = loop (jobj, xs)
in
  jobj
end  // [end of fun json_simple_2_jansson]

in

extern fun JSON_INDENT (x: int): int = "mac#JSON_INDENT"
extern fun ORInt (x: int, y: int): int = "ORInt"

implement json_dump_output (jv, out) = let
  val jsonptr = json_simple_2_jansson (jv)
  val err = $JS.json_dumpf (jsonptr, out, 
      ORInt($JS.JSON_ENCODE_ANY, JSON_INDENT (2)))
  val () = $JS.json_decref (jsonptr)
in end

implement json_dump_string (jv) = let
  val jsonptr = json_simple_2_jansson (jv)
  val strptr = $JS.json_dumps (jsonptr,
      ORInt($JS.JSON_ENCODE_ANY, JSON_INDENT (2)))
  val () = assertloc (strptr_isnot_null (strptr))
  val () = $JS.json_decref (jsonptr)
in
  string_of_strptr (strptr)
end

end

implement jsonize_int (x) = JSONint (llint_of_int(x))
implement jsonize_lint (x) = let
  extern castfn llint_of_lint (x: lint): llint
in
  JSONint (llint_of_lint(x))
end

implement jsonize_double (x) = JSONdouble (x)

implement jsonize_bool (x) = JSONbool (x)

implement jsonize_char (x) = let
  val str_buf = string_singleton (x)
  val str = string_of_strbuf (str_buf)
in
  JSONstring (str)
end

implement jsonize_string (x) = JSONstring (x)

implement jsonize_ref () = JSONstring ("ref is not supported")
















