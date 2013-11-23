package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class BaseType implements IATSType {

    public final static BaseType cInt = new BaseType();
    public final static BaseType cString = new BaseType();
    public final static BaseType cDouble = new BaseType();
    
    private BaseType() {}

    @Override
    public ST generate(STGroup stg, String arg) {
        ST st = null;
        if (this == cInt) {
            st = stg.getInstanceOf("jsonize_int_st");
        } else if (this == cString) {
            st = stg.getInstanceOf("jsonize_string_st");
        } else if (this == cDouble) {
            st = stg.getInstanceOf("jsonize_double_st");
        } else {
            throw new Error("not supported");
        }
        
        st.add("arg", arg);
        return st;
    }

    @Override
    public ST getTypeString(STGroup stg) {
        ST st = null;
        if (this == cInt) {
            st = stg.getInstanceOf("int_type_st");
        } else if (this == cString) {
            st = stg.getInstanceOf("string_type_st");
        } else if (this == cDouble) {
            st = stg.getInstanceOf("double_type_st");
        } else {
            throw new Error("not supported");
        }

        return st;
    }

}
