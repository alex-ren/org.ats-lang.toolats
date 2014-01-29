" compiler/sats.vim: Vim compiler file for ATS-Postiats.
" Author: Zhiqiang Ren
" Last Modified: 01/28/2014

if exists("current_compiler")
    finish
endif
let current_compiler = "sats"

if exists(":CompilerSet") != 2
    command -nargs=* CompilerSet setlocal <args>
endif


"if !filereadable(expand("%:p:h")."/Makefile")
CompilerSet makeprg=patsopt\ -tc\ -s\ %
"endif

CompilerSet errorformat^=
        \%E%f:\ %*\\d(line=%l\\,\ offs=%c)\ --\ %*\\d(line=%*\\d\\,\ offs=%*\\d):\ error(%*\\S):\ %m,
        \%-G%.%#
    
        


