package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

public class TypeCons {
    public String m_consId;
    public List<IATSType> m_tyLst;
    
    public TypeCons(String consId, List<IATSType> tyLst) {
        m_consId = consId;
        m_tyLst = tyLst;
    }
}