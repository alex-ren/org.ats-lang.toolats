package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class RefType implements IATSType {
    public IATSType m_ty;
    
    public RefType(IATSType ty) {
        m_ty = ty;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        ST st = stg.getInstanceOf("jsonize_ref_st");
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = stg.getInstanceOf("ref_type_st");
        st.add("elety", m_ty.getTypeString(stg));
        
        return st;
    }

}
