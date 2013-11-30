package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class NameType implements IATSType {
    public TypeId m_id;
    
    public NameType(TypeId id) {
        m_id = id;
    }
    
    public void updateTypeId(TypeId tid) {
        m_id = tid;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        // trans_name_st (arg, name) ::= <<
        ST st = stg.getInstanceOf("trans_name_st");
        st.add("arg", arg);
        st.add("name", m_id.getFullNameRep(stg));
        
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = stg.getInstanceOf("name_type_st");
        st.add("name", m_id.getFullNameForType(stg));
        
        return st;
    }
    
    @Override
    public Object accept(IATSTypeVisitor visitor) {
        return visitor.visit(this);
    }

}
