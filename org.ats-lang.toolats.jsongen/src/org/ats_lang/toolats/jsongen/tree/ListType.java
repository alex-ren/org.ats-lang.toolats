package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class ListType implements IATSType {

    public IATSType m_ty;
    
    public ListType(IATSType ty) {
        m_ty = ty;
    }

    @Override
    public ST generate(STGroup stg, String arg) {
        // trans_list_st (arg, para, elety, elety_trans) ::= <<
        ST st = stg.getInstanceOf("trans_list_st");
        st.add("arg", arg);
        
        String para = "x";
        st.add("para", para);
        
        ST stEleTrans = m_ty.generate(stg, para);

        st.add("elety", m_ty.getTypeString(stg));
        st.add("elety_trans", stEleTrans);
        
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = stg.getInstanceOf("list_type_st");
        st.add("elety", m_ty.getTypeString(stg));
        
        return st;
    }
    
    @Override
    public Object accept(IATSTypeVisitor visitor) {
        return visitor.visit(this);
    }
}






