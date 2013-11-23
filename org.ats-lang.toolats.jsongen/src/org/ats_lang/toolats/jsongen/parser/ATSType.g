grammar ATSType;

options {
  language = Java;
}

@header {
package org.ats_lang.toolats.jsongen.parser;
import org.ats_lang.toolats.jsongen.tree.*;
}

@lexer::header {
package  org.ats_lang.toolats.jsongen.parser;
}

@lexer::members {
  private boolean mmode = false;
}

rule returns [ATSTypeSpec spec]
@init {
    List<IATSTypeDef> typelst = new ArrayList<IATSTypeDef>();
}
    : literal (x=type_create{typelst.add($x.tydef);})+ EOF {spec = new ATSTypeSpec($literal.content, typelst);};

literal returns [String content]
@init { final StringBuilder buf = new StringBuilder(); }
    : LBeg (MISC { buf.append($MISC.text); })* LEnd {content = buf.toString();}
    ;
    // (MISC {buf.append($MISC.text);})
type_create returns [IATSTypeDef tydef]
    : datatype {tydef = $datatype.tydef;}
    | abstype {tydef = $abstype.tydef;}
    | typedef {tydef = $typedef.tydef;}
    ;
     
atstype returns [IATSType ty]
    : listtype {ty = $listtype.ty;}
    | recordtype {ty = $recordtype.ty;}
    | basetype {ty = $basetype.ty;}
    | nametype {ty = $nametype.ty;}
    ;
    
datatype returns [DataTypeDef tydef]
@init {
    List<TypeCons> tyLst = new ArrayList<TypeCons>();
    
}
    : Datatype ID Assign (Bar type_cons{tyLst.add($type_cons.tyCons);})+ {tydef = new DataTypeDef($ID.text, tyLst);}
    ;
    
type_cons returns [TypeCons tyCons]
    : ID LParen atstypelst RParen {return new TypeCons($ID.text, $atstypelst.tyLst);}
    ;

atstypelst returns [List<IATSType> tyLst]
@init {
  tyLst = new ArrayList<IATSType>();
}
    : (x=atstype {tyLst.add($x.ty);} (Comma y=atstype{tyLst.add($y.ty);})*)?
    ;

listtype returns [ListType ty] 
    : List LParen atstype RParen {ty = new ListType($atstype.ty);}
    ;

recordtype returns [RecordType ty]
    : LBrace typepairlst RBrace {return new RecordType($typepairlst.typLst);}
    ;
    
typepairlst returns [List<TypePair> typLst]
@init {
  typLst = new ArrayList<TypePair>();
}
    : x=typepair{typLst.add($x.typ);} (Comma y=typepair {typLst.add($y.typ);})*
    ;
    
typepair returns [TypePair typ]
    : ID Assign atstype {typ = new TypePair($ID.text, $atstype.ty);}
    ;

nametype returns [IATSType ty]
    : ID {ty = new NameType($ID.text);}
    ;
    
abstype returns [AbsTypeDef tydef]
    : Abstract ID LBrace typepairlst RBrace {return new AbsTypeDef($ID.text, $typepairlst.typLst);}
    ;
    
basetype returns [BaseType ty]
    : Int {ty = BaseType.cInt;}
    | Double {ty = BaseType.cDouble;}
    | String {ty = BaseType.cString;}
    ;

typedef returns [NameTypeDef tydef]
    : Typedef ID Assign atstype
    ;
    
// ===================================================== 


Datatype  : 'datatype';
Assign    : '=';
Bar       : '|';
LParen    : '(';
RParen    : ')';
Comma     : ',';
LBrace    : '{';
RBrace    : '}';

List      : 'List';
Abstract  : 'abstype';
Typedef   : 'typedef';

Int       : 'int';
Double    : 'double';
String    : 'string';


LBeg      : '%{' {mmode = true;};
LEnd      : '%}' {mmode = false;};

ID  : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_' | '$')*
    ;


COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    |   '#if(0)' ( options {greedy=false;} : . )* '#endif' {$channel=HIDDEN;}  // todo 
    |   '#define' ( options {greedy=false;} : . )* ('\n') {$channel=HIDDEN;}  // todo
    ;

WS  : ( ' '
      | '\t'
      | '\r'
      | '\n'
      ) {$channel=HIDDEN;}
      ;

MISC
    : {mmode}?=> (~'%')+ | '%' (~('}'|'%'))?
    ;
