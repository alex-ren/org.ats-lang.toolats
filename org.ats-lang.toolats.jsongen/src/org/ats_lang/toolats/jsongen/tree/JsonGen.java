package org.ats_lang.toolats.jsongen.tree;

import java.net.URL;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class JsonGen {
    
    public String trans(String extra, ATSTypeSpec spec) {
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/jsongen/tree/jsonize.stg");
        STGroup stg = new STGroupFile(fileURL, "ascii", '<', '>');
        
        // type_spec_st (extra, literal, abs_mem_dec_lst, abs_whole_dec_lst, func_dec_lst, func_impl_lst) ::= <<
        ST st = stg.getInstanceOf("type_spec_st");
        st.add("literal", spec.m_literal);
        st.add("extra", extra);
        
        for (IATSTypeDef tydef: spec.m_tyLst) {
//            if (tydef instanceof AbsTypeDef) {
//                if (((AbsTypeDef)tydef).hasDef()) {
//                    List<ST> stExtDecLst = ((AbsTypeDef) tydef).generateMemDec(stg);
//                    for (ST stExtDec : stExtDecLst) {
//                        st.add("abs_mem_dec_lst", stExtDec);
//                    }
//                    
//                    ST stDec = tydef.generateDec(stg);
//                    if (null != stDec) {
//                        st.add("func_dec_lst", stDec);
//                    }
//
//                } else {
//                    ST stDec = tydef.generateDec(stg);
//                    if (null != stDec) {
//                        st.add("abs_whole_dec_lst", stDec);
//                    }
//                }
//                
//            } else {
//                ST stDec = tydef.generateDec(stg);
//                if (null != stDec) {
//                    st.add("func_dec_lst", stDec);
//                }
//                
//            }

            ST stImpl = tydef.generateImpl(stg);
            if (null != stImpl) {
                st.add("func_impl_lst", stImpl);
            }
            
        }

        String ret = st.render();
        
        return ret;
    }
    
    public String getSATS(String extra, ATSTypeSpec spec) {
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/jsongen/tree/jsonize.stg");
        STGroup stg = new STGroupFile(fileURL, "ascii", '<', '>');
        
        // type_spec_impl_sats_st (extra, literal, abs_mem_dec_lst, abs_whole_dec_lst) ::= <<
        ST st = stg.getInstanceOf("type_spec_impl_sats_st");
        st.add("literal", spec.m_literal);
        st.add("extra", extra);
        
        for (IATSTypeDef tydef: spec.m_tyLst) {
            if (tydef instanceof AbsTypeDef) {
                if (((AbsTypeDef) tydef).hasDef()) {
//                    if (((AbsTypeDef) tydef).m_id.getTyId().equals("s2cst")) {
//                        throw new Error("eeeeeeeeeeeeeee");
//                    }
                    List<ST> stExtDecLst = ((AbsTypeDef) tydef).generateMemDec(stg);
                    for (ST stExtDec : stExtDecLst) {
                        st.add("abs_mem_dec_lst", stExtDec);
                    }
                    
                    ST stDec = tydef.generateDec(stg);
                    st.add("func_dec_lst", stDec);
                } else {
                    st.add("abs_whole_dec_lst", tydef.generateDec(stg));
                }
            } else {
                ST stDec = tydef.generateDec(stg);
                if (null != stDec) {
                    st.add("func_dec_lst", stDec);
                }
            }
        }

        String ret = st.render();
        
        return ret;
    }
    
    public String getDATS(String extra, ATSTypeSpec spec) {
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/jsongen/tree/jsonize.stg");
        STGroup stg = new STGroupFile(fileURL, "ascii", '<', '>');
        
        // type_spec_impl_dats_st (extra, literal, ext_func_simple_impl_lst) ::= <<
        ST st = stg.getInstanceOf("type_spec_impl_dats_st");
        st.add("literal", spec.m_literal);
        
        st.add("extra", extra);
        
        for (IATSTypeDef tydef: spec.m_tyLst) {
            if (tydef instanceof AbsTypeDef) {
                if (((AbsTypeDef) tydef).hasDef()) {
                } else {
                    st.add("ext_func_simple_impl_lst", ((AbsTypeDef)tydef).generateSimpleImpl(stg));
                }
            }
        }

        String ret = st.render();
        
        return ret;
    }

}
