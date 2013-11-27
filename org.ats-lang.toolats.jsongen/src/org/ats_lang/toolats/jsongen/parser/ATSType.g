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
    : literal? (x=type_create{typelst.add($x.tydef);})+ EOF {spec = new ATSTypeSpec($literal.content, typelst);};

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
    | optiontype {ty = $optiontype.ty;}
    | recordtype {ty = $recordtype.ty;}
    | basetype {ty = $basetype.ty;}
    | nametype {ty = $nametype.ty;}
    | reftype {ty = $reftype.ty;}
    ;
    
datatype returns [DataTypeDef tydef]
@init {
    List<TypeCons> tyLst = new ArrayList<TypeCons>();
    
}
    : Datatype typeid Assign (Bar type_cons{tyLst.add($type_cons.tyCons);})+ {tydef = new DataTypeDef($typeid.tyid, tyLst);}
    ;
    
type_cons returns [TypeCons tyCons]
    : ID (Of LParen? atstypelst RParen?)? {return new TypeCons($ID.text, $atstypelst.tyLst);}
    ;

atstypelst returns [List<IATSType> tyLst]
@init {
  tyLst = new ArrayList<IATSType>();
}
    : (x=atstype {tyLst.add($x.ty);} (Comma y=atstype{tyLst.add($y.ty);})*)?
    ;

listtype returns [ListType ty] 
    : List atstype {ty = new ListType($atstype.ty);}
    | List LParen atstype RParen {ty = new ListType($atstype.ty);}
    ;

optiontype returns [OptionType ty] 
    : Option LParen atstype RParen {ty = new OptionType($atstype.ty);}
    ;

recordtype returns [RecordType ty]
    : '\''? LBrace typepairlst RBrace {ty = new RecordType(RecordType.Kind.boxed, $typepairlst.typLst);}
    | '@' LBrace typepairlst RBrace {ty = new RecordType(RecordType.Kind.flat, $typepairlst.typLst);}
    ;
    
typepairlst returns [List<TypePair> typLst]
@init {
  typLst = new ArrayList<TypePair>();
}
    : x=typepair{typLst.add($x.typ);} (Comma y=typepair {typLst.add($y.typ);})*
    ;
    
typepair returns [TypePair typ]
    : ID Assign atstype {typ = new TypePair($ID.text, $atstype.ty);}
    | '@' (QID Dot)? ID Assign atstype {typ = new TypePair(TypePair.Kind.func, $QID.text, $ID.text, $atstype.ty);}
    ;

nametype returns [IATSType ty]
    : typeid {ty = new NameType($typeid.tyid);}
    ;
    
abstype returns [AbsTypeDef tydef]
    : Abstract typeid (Assign ('\''|'@')? LBrace typepairlst RBrace)?  {return new AbsTypeDef($typeid.tyid, $typepairlst.typLst);}
    ;

typeid returns [TypeId tyid]
@init {
List<TypeId> tyidLst = new ArrayList<TypeId>();
}
    : (QID Dot)? ID (LParen (tid=typeid {tyidLst.add($tid.tyid);})* RParen)? {tyid = new TypeId($QID.text, $ID.text, tyidLst);}
    ;

basetype returns [BaseType ty]
    : Int {ty = BaseType.cInt;}
    | Double {ty = BaseType.cDouble;}
    | String {ty = BaseType.cString;}
    | Bool {ty = BaseType.cBool;}
    | Char {ty = BaseType.cChar;}
    | LInt {ty = BaseType.cLInt;}
    ;
    
reftype returns [RefType ty]
    : Ref LParen (atstype) RParen {ty = new RefType($atstype.ty);}
    ;

typedef returns [NameTypeDef tydef]
    : Typedef typeid Assign atstype {tydef = new NameTypeDef($typeid.tyid, $atstype.ty);}
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
Option    : 'Option';
Abstract  : 'abstype';
Typedef   : 'typedef';

Int       : 'int';
Double    : 'double';
String    : 'string';
Bool      : 'bool';
Char      : 'char';
LInt      : 'lint';

Of        : 'of';
Ref       : 'ref';

Dot       : '.';

LBeg      : '%{' {mmode = true;};
LEnd      : '%}' {mmode = false;};

ID  : (('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*) | ('_' ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'| '_')*) 
    ;
    
QID : '$' ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    |   '(*'  ( ~'*'| '*'+ (~(')' | '*')))*  '*)' {$channel=HIDDEN;}
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
