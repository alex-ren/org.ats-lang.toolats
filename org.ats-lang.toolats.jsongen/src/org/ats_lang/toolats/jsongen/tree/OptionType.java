package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class OptionType implements IATSType {

    public IATSType m_ty;
    
    public OptionType(IATSType ty) {
        m_ty = ty;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        // trans_option_st (arg, para, elety_trans) ::= <<
        ST st = stg.getInstanceOf("trans_option_st");
        st.add("arg", arg);
        
        String para = "__v";
        st.add("para", para);
        
        ST stEleTrans = m_ty.generate(stg, para);
        
        st.add("elety_trans", stEleTrans);
        
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = stg.getInstanceOf("option_type_st");
        st.add("elety", m_ty.getTypeString(stg));
        
        return st;
    }
    
    @Override
    public Object accept(IATSTypeVisitor visitor) {
        return visitor.visit(this);
    }
}


