package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class AbsTypeDef implements IATSTypeDef {
    public String m_id;
    public List<TypePair> m_typLst;
    
    public AbsTypeDef(String id, List<TypePair> typLst) {
        m_id = id;
        m_typLst = typLst;
    }

    @Override
    public ST generateImpl(STGroup stg) {
        // abs_type_impl_st (para, tyid, abs_mem_lst) ::= <<
        ST st = stg.getInstanceOf("abs_type_impl_st");
        String paraWhole = "x";
        st.add("para", paraWhole);
        st.add("tyid", m_id);
        
        for (TypePair tp: m_typLst) {
            // abs_getv_st (tyid, arg, name) ::= <<
            ST stGetV = stg.getInstanceOf("abs_getv_st");
            stGetV.add("tyid", m_id);
            stGetV.add("arg", paraWhole);
            stGetV.add("name", tp.m_id);
            
            String para = "__v";
            ST stTrans = tp.m_ty.generate(stg, para);
            
            // abs_member_st (para, mem_name, getv, trans) ::= <<
            ST stAbsMem = stg.getInstanceOf("abs_member_st");
            stAbsMem.add("para", para);
            stAbsMem.add("getv", stGetV);
            stAbsMem.add("mem_name", tp.m_id);
            stAbsMem.add("trans", stTrans);
            
            st.add("abs_mem_lst", stAbsMem);
        }
        
        return st;
    }

    @Override
    public ST generateDec(STGroup stg) {
        // abs_type_dec_st (tyid, ty) ::= <<
        ST st = stg.getInstanceOf("abs_type_dec_st");
        st.add("tyid", m_id);
        return st;
    }
    
}
