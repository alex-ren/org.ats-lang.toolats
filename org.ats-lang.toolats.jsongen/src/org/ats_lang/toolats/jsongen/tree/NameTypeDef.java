package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class NameTypeDef implements IATSTypeDef {
    public TypeId m_id;
    public IATSType m_ty;
    
    public NameTypeDef(TypeId id, IATSType ty) {
        m_id = id;
        m_ty = ty;
    }

    @Override
    public ST generateImpl(STGroup stg) {
        // name_type_impl_st (para, tyid_name, trans) ::= <<
        ST st = stg.getInstanceOf("name_type_impl_st");
        
        String para = "x";
        st.add("para", para);
        st.add("tyid_name", m_id.getFullNameRep(stg));
        
        ST stTrans = m_ty.generate(stg, para);
        st.add("trans", stTrans);
        
        return st;
    }

    @Override
    public ST generateDec(STGroup stg) {
        // name_type_dec_st (tyid_name, tyid) ::= <<
        ST st = stg.getInstanceOf("name_type_dec_st");
        st.add("tyid", m_id.getFullNameForType(stg));
        st.add("tyid_name", m_id.getFullNameRep(stg));
        
        return st;
    }

}
