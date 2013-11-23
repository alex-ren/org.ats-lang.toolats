package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class NameType implements IATSType {
    public String m_id;
    
    public NameType(String id) {
        m_id = id;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        // trans_name_st (arg, name) ::= <<
        ST st = stg.getInstanceOf("trans_name_st");
        st.add("arg", arg);
        st.add("name", m_id);
        
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = stg.getInstanceOf("name_type_st");
        st.add("name", m_id);
        
        return st;
    }

}
