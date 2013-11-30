package org.ats_lang.toolats.jsongen.tree;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class BaseType implements IATSType {

    public final static BaseType cInt = new BaseType();
    public final static BaseType cString = new BaseType();
    public final static BaseType cDouble = new BaseType();
    public final static BaseType cBool = new BaseType();
    public final static BaseType cChar = new BaseType();
    public final static BaseType cLInt = new BaseType();
    
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
        } else if (this == cBool) {
            st = stg.getInstanceOf("jsonize_bool_st");
        } else if (this == cChar) {
            st = stg.getInstanceOf("jsonize_char_st");
        } else if (this == cLInt) {
            st = stg.getInstanceOf("jsonize_lint_st");
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
        } else if (this == cBool) {
            st = stg.getInstanceOf("bool_type_st");
        } else if (this == cChar) {
            st = stg.getInstanceOf("char_type_st");
        } else if (this == cLInt) {
            st = stg.getInstanceOf("lint_type_st");
        } else {
            throw new Error("not supported");
        }

        return st;
    }
    
    @Override
    public Object accept(IATSTypeVisitor visitor) {
        return visitor.visit(this);
    }

}
