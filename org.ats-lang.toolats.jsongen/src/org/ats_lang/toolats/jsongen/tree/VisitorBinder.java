package org.ats_lang.toolats.jsongen.tree;

import java.util.HashMap;
import java.util.Map;

public class VisitorBinder implements IATSTypeVisitor {
    
    private Map<String, TypeId> m_map;
    
    private ATSTypeSpec m_spec;
    
    public VisitorBinder(ATSTypeSpec spec) {
        m_map = new HashMap<String, TypeId>();
        m_spec = spec;
    }
    
    public void bindName() {
        for (IATSTypeDef tyDef: m_spec.m_tyLst) {
            TypeId cTid = tyDef.getTypeId();
            TypeId tid = m_map.get(cTid.getShortName());
            if (null != tid) {
                throw new Error("duplicated type definition: " + tid);
            }
            m_map.put(cTid.getShortName(), cTid);
        }
        
        for (IATSTypeDef tyDef: m_spec.m_tyLst) {
            tyDef.accept(this);
        }
    }

    private void updateTypeId(IATSType ty) {
        if (ty instanceof NameType) {
            NameType nt = (NameType)ty;
            TypeId oriTid = nt.m_id;
            TypeId newTid = m_map.get(oriTid.getShortName());
            if (null == newTid) {
                throw new Error("type " + oriTid.toString() + " not found");
            } else {
                nt.updateTypeId(newTid);
            }
        }
    }
    
    @Override
    public Object visit(AbsTypeDef node) {
        for (TypePair tp: node.m_typLst){
            updateTypeId(tp.m_ty);
        }
        
        return null;
    }

    @Override
    public Object visit(NameTypeDef node) {
        updateTypeId(node.m_ty);
        return null;
    }

    @Override
    public Object visit(DataTypeDef node) {
        for (TypeCons tyCons: node.m_consLst) {
            for (IATSType ty: tyCons.m_tyLst) {
                updateTypeId(ty);
            }
        }
        
        return null;
    }

    @Override
    public Object visit(BaseType node) {
        return null;
    }

    @Override
    public Object visit(ListType node) {
        updateTypeId(node.m_ty);
        return null;
    }

    @Override
    public Object visit(NameType node) {
        updateTypeId(node);
        return null;
    }

    @Override
    public Object visit(OptionType node) {
        updateTypeId(node.m_ty);
        return null;
    }

    @Override
    public Object visit(RecordType node) {
        for (TypePair tp: node.m_typLst){
            updateTypeId(tp.m_ty);
        }
        
        return null;
    }

    @Override
    public Object visit(RefType node) {
        throw new Error("not supported");
    }

}
