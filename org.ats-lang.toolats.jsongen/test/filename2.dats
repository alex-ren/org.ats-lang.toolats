
staload "filename2.sats"

local
assume T2 = '{x=int, y=string}

in

implement T2_new (x, y) = '{x=x, y=y}

implement T2_getx (v) = v.x

implement T2_gety (v) = v.y

end


