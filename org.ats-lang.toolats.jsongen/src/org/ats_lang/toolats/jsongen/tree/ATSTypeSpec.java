package org.ats_lang.toolats.jsongen.tree;

import java.util.List;

public class ATSTypeSpec {
    public List<IATSTypeDef> m_tyLst;
    public String m_literal;
    
    public ATSTypeSpec(String literal, List<IATSTypeDef> tyLst) {
        m_tyLst = tyLst;
        m_literal = literal;
    }

}
