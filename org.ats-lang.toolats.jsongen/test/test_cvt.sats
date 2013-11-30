
staload F1 = "filename1.sats"
staload F2 = "filename2.sats"
staload F3 = "filename3.sats"


staload "json_simple.sats"

staload _(*anon*) = "prelude/DATS/list.dats"
staload _(* anon *) = "prelude/DATS/array0.dats"

typedef T5 = List int
typedef T6 = '{x=int, y=string}




// ====== to be implemented ==========

fun todoT2_getx (x: $F2.T2): int  // abs member

fun todoT2_gety (x: $F2.T2): string  // abs member

// ====================================================


// ============ already implemented =============

fun jsonize_T1 (x: $F1.T1): jsonVal

fun jsonize_T2x (x: $F2.T2x): jsonVal

fun jsonize_T2y (x: $F2.T2y): jsonVal

fun jsonize_T2 (x: $F2.T2): jsonVal

fun jsonize_T3 (x: $F3.T3): jsonVal

fun jsonize_T4 (x: $F3.T4): jsonVal

fun jsonize_T5 (x: T5): jsonVal

fun jsonize_T6 (x: T6): jsonVal

