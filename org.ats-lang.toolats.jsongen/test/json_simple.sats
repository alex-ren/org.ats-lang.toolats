




datatype jsonVal =
| JSONnul of ()
| JSONint of (json_int)
| JSONbool of (bool)
| JSONstring of (string)
| JSONdouble of (double)
| JSONarray of (array0 (jsonVal))
| JSONobject of (labjsonValist)

where
json_int = llint
and
labjsonVal = '(string, jsonVal)
and
labjsonValist = list0 (labjsonVal)

fun json_dump_string (jv: jsonVal): string

fun json_dump_output (jv: jsonVal, out: FILEref): void

fun jsonize_int (x: int): jsonVal

fun jsonize_lint (x: lint): jsonVal

fun jsonize_double (x: double): jsonVal

fun jsonize_string (x: string): jsonVal

fun jsonize_bool (x: bool): jsonVal

fun jsonize_char (x: char): jsonVal

fun jsonize_ref (): jsonVal

