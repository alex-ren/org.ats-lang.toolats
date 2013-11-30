package org.ats_lang.toolats.jsongen.tree;


import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public interface IATSTypeDef {
    // implement xxx (v) = ...
    ST generateImpl(STGroup stg);
    
    // fun xxx (v: ttt): ttt
    ST generateDec(STGroup stg);
    
    TypeId getTypeId();
    
    Object accept(IATSTypeVisitor visitor);

}
