package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class DataTypeDef implements IATSTypeDef {

    public TypeId m_id;
    public List<TypeCons> m_consLst;
    
    public DataTypeDef(TypeId id, List<TypeCons> consLst) {
        m_id = id;
        m_consLst = consLst;
    }

    @Override
    public ST generateImpl(STGroup stg) {
        // data_type_impl_st (tyid_name, data_mem_lst) ::= <<
        ST st = stg.getInstanceOf("data_type_impl_st");
        st.add("tyid_name", m_id.getFullNameRep(stg));
        
        for (TypeCons tc: m_consLst) {
            // data_mem_st (qid, arrname, cons_name, paralst, len, data_mem_item_lst) ::= <<
            ST stDataMem = stg.getInstanceOf("data_mem_st");
            stDataMem.add("qid", m_id.getQId());
            String arrname = "__arr";
            stDataMem.add("arrname", arrname);
            stDataMem.add("cons_name", tc.m_consId);
            stDataMem.add("len", tc.m_tyLst.size() + 1);
            
            String para = "__e";
            int count = 1;
            for (IATSType ty: tc.m_tyLst) {
                // data_mem_item_st (arrname, ind, trans) ::= <<
                ST stDataMemItem = stg.getInstanceOf("data_mem_item_st");
                stDataMemItem.add("arrname", arrname);
                stDataMemItem.add("ind", count);
                String paranow = para + count;
                
                ST stTrans = ty.generate(stg, paranow);
                stDataMemItem.add("trans", stTrans);
                
                stDataMem.add("paralst", paranow);
                stDataMem.add("data_mem_item_lst", stDataMemItem);
                
                count++; 
            }
            
            st.add("data_mem_lst", stDataMem);
        }
        
        return st;        
    }

    @Override
    public ST generateDec(STGroup stg) {
        // data_type_dec_st (tyid_name, tyid) ::= <<
        ST st = stg.getInstanceOf("data_type_dec_st");
        st.add("tyid_name", m_id.getFullNameRep(stg));
        st.add("tyid", m_id.getFullNameForType(stg));
        return st;
    }


}
