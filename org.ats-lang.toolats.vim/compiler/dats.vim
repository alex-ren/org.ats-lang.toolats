" compiler/dats.vim: Vim compiler file for ATS-Postiats.
" Author: Zhiqiang Ren
" Last Modified: 01/28/2014

if exists("current_compiler")
    finish
endif
let current_compiler = "dats"

if exists(":CompilerSet") != 2
    command -nargs=* CompilerSet setlocal <args>
endif


"if !filereadable(expand("%:p:h")."/Makefile")
CompilerSet makeprg=patsopt\ -tc\ -d\ %
"endif

CompilerSet errorformat^=
        \%E%f:\ %*\\d(line=%l\\,\ offs=%c)\ --\ %*\\d(line=%*\\d\\,\ offs=%*\\d):\ error(%n):\ %m,
        \%E%f:\ %*\\d(line=%l\\,\ offs=%c)\ --\ %*\\d(line=%*\\d\\,\ offs=%*\\d):\ error(%tarsing):\ %m,
        \%W%f:\ %*\\d(line=%l\\,\ offs=%c)\ --\ %*\\d(line=%*\\d\\,\ offs=%*\\d):\ warning(%n):\ %m,
        \%+C%.%#actual%.%#,
        \%+C%.%#needed%.%#
        "\%-G%.%#
    
        


