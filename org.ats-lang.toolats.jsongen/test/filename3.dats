
staload F2 = "filename2.sats"

staload "filename3.sats"

local
assume T3 = '{x = int, y = $F2.T2y}

in

implement T3_new (x, y) = '{x=x, y=y}

implement T3_getx (v) = v.x

implement T3_gety (v) = v.y

end

