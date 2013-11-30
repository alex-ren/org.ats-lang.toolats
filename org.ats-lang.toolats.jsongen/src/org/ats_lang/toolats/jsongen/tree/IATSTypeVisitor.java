package org.ats_lang.toolats.jsongen.tree;

public interface IATSTypeVisitor {
    
    Object visit(AbsTypeDef node);
    Object visit(NameTypeDef node);
    Object visit(DataTypeDef node);
    
    Object visit(BaseType node);
    Object visit(ListType node);
    Object visit(NameType node);
    Object visit(OptionType node);
    Object visit(RecordType node);
    Object visit(RefType node);
    

}


