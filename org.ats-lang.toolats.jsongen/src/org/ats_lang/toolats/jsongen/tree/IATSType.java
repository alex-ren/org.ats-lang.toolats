package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public interface IATSType {
    ST generate(STGroup stg, String arg);
    
    ST getTypeString(STGroup stg);
}
