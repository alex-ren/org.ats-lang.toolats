package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class RecordType implements IATSType {

    public List<TypePair> m_typLst;
    
    public RecordType(List<TypePair> typLst) {
        m_typLst = typLst;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        // trans_record_st (rec_mem_lst) ::= <<
        ST st = stg.getInstanceOf("trans_record_st");
        
        for (TypePair tp: m_typLst) {
            // record_qname_st (arg, name) ::= <<
            ST stQName = stg.getInstanceOf("record_qname_st");
            stQName.add("arg", arg);
            stQName.add("name", tp.m_id);
            
            String para = "__v";
            ST stTrans = tp.m_ty.generate(stg, para);
            
            // record_member_st (para, mem_name, qname, trans) ::= <<
            ST stRecMem = stg.getInstanceOf("record_member_st");
            stRecMem.add("para", para);
            stRecMem.add("qname", stQName);
            stRecMem.add("mem_name", tp.m_id);
            stRecMem.add("trans", stTrans);
            
            st.add("rec_mem_lst", stRecMem);
        }
        
        return st;
        
    }

    @Override
    public ST getTypeString(STGroup stg) {
        // record_type_st (tplst) ::= <<
        ST st = stg.getInstanceOf("record_type_st");
        for (TypePair tp: m_typLst) {
            // record_pair_st (name, ty) ::= <<
            ST stPair = stg.getInstanceOf("record_pair_st");
            stPair.add("name", tp.m_id);
            stPair.add("ty", tp.m_ty.getTypeString(stg));
            
            st.add("tplst", stPair);
        }
        
        return st;
    }
}













