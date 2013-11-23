package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class NameTypeDef implements IATSTypeDef {
    public String m_id;
    public IATSType m_ty;
    
    public NameTypeDef(String id, IATSType ty) {
        m_id = id;
        m_ty = ty;
    }

    @Override
    public ST generateImpl(STGroup stg) {
        // name_type_imple_st (para, tyid, trans) ::= <<
        ST st = stg.getInstanceOf("name_type_def_st");
        
        String para = "x";
        st.add("para", para);
        st.add("tyid", m_id);
        
        ST stTrans = m_ty.generate(stg, para);
        st.add("trans", stTrans);
        
        return st;
    }

    @Override
    public ST generateDec(STGroup stg) {
        // name_type_dec_st (tyid) ::= <<
        ST st = stg.getInstanceOf("name_type_dec_st");
        st.add("tyid", m_id);
        
        return st;
    }

}
