// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListExtra_Chain extends VarDeclListExtra {

    private VarDeclListExtra VarDeclListExtra;
    private VarDeclList VarDeclList;

    public VarDeclListExtra_Chain (VarDeclListExtra VarDeclListExtra, VarDeclList VarDeclList) {
        this.VarDeclListExtra=VarDeclListExtra;
        if(VarDeclListExtra!=null) VarDeclListExtra.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public VarDeclListExtra getVarDeclListExtra() {
        return VarDeclListExtra;
    }

    public void setVarDeclListExtra(VarDeclListExtra VarDeclListExtra) {
        this.VarDeclListExtra=VarDeclListExtra;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListExtra!=null) VarDeclListExtra.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListExtra!=null) VarDeclListExtra.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListExtra!=null) VarDeclListExtra.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListExtra_Chain(\n");

        if(VarDeclListExtra!=null)
            buffer.append(VarDeclListExtra.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListExtra_Chain]");
        return buffer.toString();
    }
}
