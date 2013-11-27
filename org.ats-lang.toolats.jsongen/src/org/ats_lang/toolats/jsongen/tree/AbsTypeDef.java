package org.ats_lang.toolats.jsongen.tree;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class AbsTypeDef implements IATSTypeDef {
    public TypeId m_id;
    public List<TypePair> m_typLst;
    
    public AbsTypeDef(TypeId id, List<TypePair> typLst) {
        m_id = id;
        m_typLst = typLst;
    }

    @Override
    public ST generateDec(STGroup stg) {
        // abs_type_dec_st (tyid_name, tyid) ::= <<
        ST st = stg.getInstanceOf("abs_type_dec_st");
        st.add("tyid_name", m_id.getFullNameRep(stg));
        st.add("tyid", m_id.getFullNameForType(stg));
        return st;
    }

    @Override
    public ST generateImpl(STGroup stg) {
        if (hasDef()) {
            // abs_type_impl_st (para, tyid, abs_mem_lst) ::= <<
            ST st = stg.getInstanceOf("abs_type_impl_st");
            String paraWhole = "x";
            st.add("para", paraWhole);
            st.add("tyid", m_id.getFullNameRep(stg));
            
            for (TypePair tp: m_typLst) {
                ST stMem = null;
                if (tp.isConcrete()) {
                    // abs_memeber_func_st (qid, id, arg) ::= <<
                    stMem = stg.getInstanceOf("abs_memeber_func_st");
                    stMem.add("qid", tp.m_qid);
                    stMem.add("id", tp.m_id);
                    stMem.add("arg", paraWhole);  
                } else {
                    // abs_getv_st (tyid, arg, name) ::= <<
                    stMem = stg.getInstanceOf("abs_getv_st");
                    stMem.add("tyid", m_id.getFullNameRep(stg));
                    stMem.add("arg", paraWhole);
                    stMem.add("name", tp.m_id);
                }
                
                String para = "__v";
                ST stTrans = tp.m_ty.generate(stg, para);
                
                // abs_member_st (para, mem_name, getv, trans) ::= <<
                ST stAbsMem = stg.getInstanceOf("abs_member_st");
                stAbsMem.add("para", para);
                stAbsMem.add("getv", stMem);
                stAbsMem.add("mem_name", tp.m_id);
                stAbsMem.add("trans", stTrans);
                
                st.add("abs_mem_lst", stAbsMem);
            }
            
            return st;
        } else {
            return null;
        }

    }
    
    public boolean hasDef() {
        if (null != m_typLst) {
            return true;
        } else {
            return false;
        }
    }

    public List<ST> generateMemDec(STGroup stg) {
        List<ST> stLst = new ArrayList<ST>();
        if (hasDef()) {
            for (TypePair tp: m_typLst) {
                if (!tp.isConcrete()) {
                    // abs_getv_dec_st (qid, tyid_name, tyid, name, frettyid) ::= <<
                    ST stGetV = stg.getInstanceOf("abs_getv_dec_st");
                    stGetV.add("tyid_name", m_id.getFullNameRep(stg));
                    stGetV.add("tyid", m_id.getFullNameForType(stg));
                    stGetV.add("name", tp.m_id);
                    stGetV.add("frettyid", tp.m_ty.getTypeString(stg));
                    
                    stLst.add(stGetV);
                }

            }
        }
        
        return stLst;        
    }


    public ST generateSimpleImpl(STGroup stg) {
        if (!hasDef()) {
            // abs_type_simple_impl_st (tyid_name) ::= <<
            ST st = stg.getInstanceOf("abs_type_simple_impl_st");
            st.add("tyid_name", m_id.getFullNameRep(stg));
            return st;
        } else {
            return null;
        }
    }
    
}














