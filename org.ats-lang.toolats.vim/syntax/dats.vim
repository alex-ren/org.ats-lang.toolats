" Vim syntax file
" Language: ATS
" Maintainer: Zhiqiang Ren (alex.ren2006 AT gmail DOT com)
" Latest Revision: 05/23/2014

if exists("b:current_syntax")
  finish
endif


" ATS-Postiats is case sensitive.
syn case match


" syn keyword atsKeyWordDyn fun prfun val
" syn keyword atsKeyWordDyn datatype
" syn keyword atsKeyWordSta datasort absort
" 
" syn region atsString start=/"/ skip=/\\"/ end=/"/

" lowercase identifier - the standard way to match
""" syn match    datsLCIdentifier /\<\(\l\|_\)\(\w\|'\)*\>/

syn match    datsKeyChar    "|"

" Errors
syn match    datsBraceErr   "}"
syn match    datsBrackErr   "\]"
syn match    datsParenErr   ")"
syn match    datsCommentErr "\*)"
syn match    datsThenErr    "\<then\>"

" Error-highlighting of "end" without synchronization:
" as keyword or as error (default)
if exists("dats_noend_error")
  syn match    datsKeyword    "\<end\>"
else
  syn match    datsEndErr     "\<end\>"
endif

" Some convenient clusters
syn cluster  datsAllErrs contains=datsBraceErr,datsBrackErr,datsParenErr,datsCommentErr,datsEndErr,datsThenErr

syn cluster  datsAENoParen contains=datsBraceErr,datsBrackErr,datsCommentErr,datsEndErr,datsThenErr

syn cluster  datsContained contains=datsTodo,datsPreDef,datsModParam,datsModParam1,datsPreMPRestr,datsMPRestr,datsMPRestr1,datsMPRestr2,datsMPRestr3,datsModRHS,datsFuncWith,datsFuncStruct,datsModTypeRestr,datsModTRWith,datsWith,datsWithRest,datsModType,datsFullMod


" Enclosing delimiters
syn region   datsEncl transparent matchgroup=datsKeyword start="(" matchgroup=datsKeyword end=")" contains=ALLBUT,@datsContained,datsParenErr
syn region   datsEncl transparent matchgroup=datsKeyword start="{" matchgroup=datsKeyword end="}"  contains=ALLBUT,@datsContained,datsBraceErr
syn region   datsEncl transparent matchgroup=datsKeyword start="\[" matchgroup=datsKeyword end="\]" contains=ALLBUT,@datsContained,datsBrackErr
syn region   datsEncl transparent matchgroup=datsKeyword start="#\[" matchgroup=datsKeyword end="\]" contains=ALLBUT,@datsContained,datsBrackErr


" Comments
syn region   datsComment start="(\*" end="\*)" contains=datsComment,datsTodo
syn region   datsCComment start="/\*" end="\*/" contains=datsTodo
syn match   datsCComment "//.*$" contains=datsTodo
syn region   datsCComment start="////" end="pattern impossible"
syn keyword  datsTodo contained TODO FIXME XXX


" let
syn region   datsEnd matchgroup=datsKeyword start="\<let\>" matchgroup=datsKeyword end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

" local
syn region   datsEnd matchgroup=datsKeyword start="\<local\>" matchgroup=datsKeyword end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

" abstype
syn region   datsNone matchgroup=datsKeyword start="\<abstype\>" matchgroup=datsKeyword end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

" begin
syn region   datsEnd matchgroup=datsKeyword start="\<begin\>" matchgroup=datsKeyword end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

" if
syn region   datsNone matchgroup=datsKeyword start="\<if\>" matchgroup=datsKeyword end="\<then\>" contains=ALLBUT,@datsContained,datsThenErr


"" Modules

" "struct"
syn region   datsStruct matchgroup=datsModule start="\<struct\>" matchgroup=datsModule end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

" "sig"
syn region   datsSig matchgroup=datsModule start="\<sig\>" matchgroup=datsModule end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr,datsModule
syn region   datsModSpec matchgroup=datsKeyword start="\<structure\>" matchgroup=datsModule end="\<\u\(\w\|'\)*\>" contained contains=@datsAllErrs,datsComment skipwhite skipempty nextgroup=datsModTRWith,datsMPRestr

" "open"
syn region   datsNone matchgroup=datsKeyword start="\<open\>" matchgroup=datsModule end="\<\u\(\w\|'\)*\(\.\u\(\w\|'\)*\)*\>" contains=@datsAllErrs,datsComment

" "structure" - somewhat complicated stuff ;-)
syn region   datsModule matchgroup=datsKeyword start="\<\(structure\|functor\)\>" matchgroup=datsModule end="\<\u\(\w\|'\)*\>" contains=@datsAllErrs,datsComment skipwhite skipempty nextgroup=datsPreDef
syn region   datsPreDef start="."me=e-1 matchgroup=datsKeyword end="\l\|="me=e-1 contained contains=@datsAllErrs,datsComment,datsModParam,datsModTypeRestr,datsModTRWith nextgroup=datsModPreRHS
syn region   datsModParam start="([^*]" end=")" contained contains=@datsAENoParen,datsModParam1
syn match    datsModParam1 "\<\u\(\w\|'\)*\>" contained skipwhite skipempty nextgroup=datsPreMPRestr

syn region   datsPreMPRestr start="."me=e-1 end=")"me=e-1 contained contains=@datsAllErrs,datsComment,datsMPRestr,datsModTypeRestr

syn region   datsMPRestr start=":" end="."me=e-1 contained contains=@datsComment skipwhite skipempty nextgroup=datsMPRestr1,datsMPRestr2,datsMPRestr3
syn region   datsMPRestr1 matchgroup=datsModule start="\ssig\s\=" matchgroup=datsModule end="\<end\>" contained contains=ALLBUT,@datsContained,datsEndErr,datsModule
syn region   datsMPRestr2 start="\sfunctor\(\s\|(\)\="me=e-1 matchgroup=datsKeyword end="->" contained contains=@datsAllErrs,datsComment,datsModParam skipwhite skipempty nextgroup=datsFuncWith
syn match    datsMPRestr3 "\w\(\w\|'\)*\(\.\w\(\w\|'\)*\)*" contained
syn match    datsModPreRHS "=" contained skipwhite skipempty nextgroup=datsModParam,datsFullMod
syn region   datsModRHS start="." end=".\w\|([^*]"me=e-2 contained contains=datsComment skipwhite skipempty nextgroup=datsModParam,datsFullMod
syn match    datsFullMod "\<\u\(\w\|'\)*\(\.\u\(\w\|'\)*\)*" contained skipwhite skipempty nextgroup=datsFuncWith

syn region   datsFuncWith start="([^*]"me=e-1 end=")" contained contains=datsComment,datsWith,datsFuncStruct
syn region   datsFuncStruct matchgroup=datsModule start="[^a-zA-Z]struct\>"hs=s+1 matchgroup=datsModule end="\<end\>" contains=ALLBUT,@datsContained,datsEndErr

syn match    datsModTypeRestr "\<\w\(\w\|'\)*\(\.\w\(\w\|'\)*\)*\>" contained
syn region   datsModTRWith start=":\s*("hs=s+1 end=")" contained contains=@datsAENoParen,datsWith
syn match    datsWith "\<\(\u\(\w\|'\)*\.\)*\w\(\w\|'\)*\>" contained skipwhite skipempty nextgroup=datsWithRest
syn region   datsWithRest start="[^)]" end=")"me=e-1 contained contains=ALLBUT,@datsContained

" "signature"
syn region   datsKeyword start="\<signature\>" matchgroup=datsModule end="\<\w\(\w\|'\)*\>" contains=datsComment skipwhite skipempty nextgroup=datsMTDef
syn match    datsMTDef "=\s*\w\(\w\|'\)*\>"hs=s+1,me=s

syn keyword  datsKeyword  and andalso case
syn keyword  datsKeyword  datatype else eqtype
syn keyword  datsKeyword  exception fn fun handle
syn keyword  datsKeyword  in infix infixl infixr
syn keyword  datsKeyword  match nonfix of orelse
syn keyword  datsKeyword  raise handle type
syn keyword  datsKeyword  val where while with withtype

syn keyword  datsType     bool char exn int list option
syn keyword  datsType     real string unit

syn keyword  datsOperator div mod not or quot rem

syn keyword  datsBoolean      true false
syn match    datsConstructor  "(\s*)"
syn match    datsConstructor  "\[\s*\]"
syn match    datsConstructor  "#\[\s*\]"
syn match    datsConstructor  "\u\(\w\|'\)*\>"

" Module prefix
syn match    datsModPath      "\u\(\w\|'\)*\."he=e-1

syn match    datsCharacter    +#"\\""\|#"."\|#"\\\d\d\d"+
syn match    datsCharErr      +#"\\\d\d"\|#"\\\d"+
syn region   datsString       start=+"+ skip=+\\\\\|\\"+ end=+"+

syn match    datsFunDef       "=>"
syn match    datsRefAssign    ":="
syn match    datsTopStop      ";;"
syn match    datsOperator     "\^"
syn match    datsOperator     "::"
syn match    datsAnyVar       "\<_\>"
syn match    datsKeyChar      "!"
syn match    datsKeyChar      ";"
syn match    datsKeyChar      "\*"
syn match    datsKeyChar      "="

syn match    datsNumber	      "\<-\=\d\+\>"
syn match    datsNumber	      "\<-\=0[x|X]\x\+\>"
syn match    datsReal	      "\<-\=\d\+\.\d*\([eE][-+]\=\d\+\)\=[fl]\=\>"

" Synchronization
syn sync minlines=20
syn sync maxlines=500

syn sync match datsEndSync     grouphere  datsEnd     "\<begin\>"
syn sync match datsEndSync     groupthere datsEnd     "\<end\>"
syn sync match datsStructSync  grouphere  datsStruct  "\<struct\>"
syn sync match datsStructSync  groupthere datsStruct  "\<end\>"
syn sync match datsSigSync     grouphere  datsSig     "\<sig\>"
syn sync match datsSigSync     groupthere datsSig     "\<end\>"

" Define the default highlighting.
" For version 5.7 and earlier: only when not done already
" For version 5.8 and later: only when an item doesn't have highlighting yet
if version >= 508 || !exists("did_dats_syntax_inits")
  if version < 508
    let did_dats_syntax_inits = 1
    command -nargs=+ HiLink hi link <args>
  else
    command -nargs=+ HiLink hi def link <args>
  endif

  HiLink datsBraceErr	 Error
  HiLink datsBrackErr	 Error
  HiLink datsParenErr	 Error

  HiLink datsCommentErr	 Error

  HiLink datsEndErr	 Error
  HiLink datsThenErr	 Error

  HiLink datsCharErr	 Error

  HiLink datsComment	 Comment
  HiLink datsCComment	 Comment

  HiLink datsModPath	 Include
  HiLink datsModule	 Include
  HiLink datsModParam1	 Include
  HiLink datsModType	 Include
  HiLink datsMPRestr3	 Include
  HiLink datsFullMod	 Include
  HiLink datsModTypeRestr Include
  HiLink datsWith	 Include
  HiLink datsMTDef	 Include

  HiLink datsConstructor  Constant

  HiLink datsModPreRHS	 Keyword
  HiLink datsMPRestr2	 Keyword
  HiLink datsKeyword	 Keyword
  HiLink datsFunDef	 Keyword
  HiLink datsRefAssign	 Keyword
  HiLink datsKeyChar	 Keyword
  HiLink datsAnyVar	 Keyword
  HiLink datsTopStop	 Keyword
  HiLink datsOperator	 Keyword

  HiLink datsBoolean	 Boolean
  HiLink datsCharacter	 Character
  HiLink datsNumber	 Number
  HiLink datsReal	 Float
  HiLink datsString	 String
  HiLink datsType	 Type
  HiLink datsTodo	 Todo
  HiLink datsEncl	 Keyword

  delcommand HiLink
endif

let b:current_syntax = "dats"

" vim: ts=8

" 
" " lowercase identifier - the standard way to match
" " syn match    datsLCIdentifier /\<\(\a\|_\)\(\w\|'\)*\>/
" 
" syn region atsCool start=/while\s*(/ end=/)/ contains=atsNest
" 
" syn region atsNest start=/(/ end=/)/ contained
" 
" syn match atsIf /^if.*/
" 
" """"""""""""""""""""""""
" syntax include @ATSC runtime! syntax/c.vim
" syntax region ATSCC start=/^=head/ end=/^=cut/ contains=@ATSC
" 
" """"""""""""""""""""""""
" 
" 
" 
" 
" syn keyword celTodo KKK LLL
" 
" syn match   javaScriptLineComment      "\/\/.*" contains=@Spell,javaScriptCommentTodo
" syn region  javaScriptComment        start="/\*"  end="\*/" contains=@Spell,javaScriptCommentTodo
" 
" hi def link celTodo        Todo
" hi def link celComment     Comment
" 
" hi def link atsCool Type
" hi def link atsNest Statement
" hi def link atsIf Statement
" 
" " let b:current_syntax = "dats"
" 
