
staload F2 = "filename2.sats"

typedef T2x = $F2.T2x
typedef T2y = $F2.T2y


datatype T1 =
| cons1 of (T2y, List (List (T2x)))
| cons2 of (T2y, '{a1=T2x, a2 = $F2.T2y})
| cons3 of (int)

