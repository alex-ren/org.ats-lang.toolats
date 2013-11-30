package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class TypeId {
    private String m_qid;
    private String m_tyid;
    private List<TypeId> m_tyidLst;
    
    public String getShortName() {
        String ret = m_tyid;
        
        for (TypeId tid: m_tyidLst) {
            ret += "_" + tid.getShortName();
        }
        return ret;
    }
    
    public String getQId() {
        return m_qid;
    }
    
    public TypeId(String qid, String tyid, List<TypeId> tyidLst) {
        m_qid = qid;
        m_tyid = tyid;
        m_tyidLst = tyidLst;
        
    }
    
    public ST getFullNameRep(STGroup stg) {
        // type_id_fullname_rep_st (id, idlst) ::= <<
        ST st = stg.getInstanceOf("type_id_fullname_rep_st");
        st.add("id", m_tyid);
        for (TypeId tid: m_tyidLst) {
            st.add("idlst", tid.getFullNameRep(stg));
        }
        return st;
        
    }
    
    public ST getFullNameForType(STGroup stg) {
        // type_id_fullname_type_st (qid, id, idlst) ::= <<
        ST st = stg.getInstanceOf("type_id_fullname_type_st");
        st.add("qid", m_qid);
        st.add("id", m_tyid);
        for (TypeId tid: m_tyidLst) {
            st.add("idlst", tid.getFullNameForType(stg));
        }
        return st;
    }
    
    @Override
    public String toString() {
        if (null != m_qid) {
            return m_qid + "." + m_tyid;
        } else {
            return m_tyid;
        }
        
    }

}
