package org.ats_lang.toolats.jsongen.tree;


import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public interface IATSTypeDef {
    ST generateImpl(STGroup stg);
    
    ST generateDec(STGroup stg);

}
