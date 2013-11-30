package org.ats_lang.toolats.jsongen.tree;

public class TypePair {
    // The "func" is used for abstract type with "@xxx_get = ttt".
    public enum Kind {member, func};
    
    public Kind m_kind;
    public String m_id;
    public IATSType m_ty;
    public String m_qid;  // This is the quantifier for the function.
    
    public TypePair(String id, IATSType ty) {
        m_kind = Kind.member;
        m_id = id;
        m_ty = ty;
        m_qid = null;
    }
    
    public TypePair(Kind k, String qid, String id, IATSType ty) {
        m_kind = k;
        m_id = id;
        m_ty = ty;
        m_qid = qid;
    }
    
    public boolean isConcrete() {
        return Kind.func == m_kind;
    }
}
