package org.ats_lang.toolats.jsongen.tree;

import java.net.URL;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class JsonGen {
    
    public String trans(ATSTypeSpec spec) {
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/jsongen/tree/jsonize.stg");
        STGroup stg = new STGroupFile(fileURL, "ascii", '<', '>');
        
        // type_spec_st (literal, func_dec_lst, func_impl_lst) ::= <<
        ST st = stg.getInstanceOf("type_spec_st");
        st.add("literal", spec.m_literal);
        
        for (IATSTypeDef tydef: spec.m_tyLst) {
            st.add("func_dec_lst", tydef.generateDec(stg));
            st.add("func_impl_lst", tydef.generateImpl(stg));
        }

        String ret = st.render();
        
        return ret;
    }

}
