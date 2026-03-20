// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class AdditionalVarDecl_COMMA extends AdditionalVarDecl {

    private VarDecl VarDecl;
    private AdditionalVarDecl AdditionalVarDecl;

    public AdditionalVarDecl_COMMA (VarDecl VarDecl, AdditionalVarDecl AdditionalVarDecl) {
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
        this.AdditionalVarDecl=AdditionalVarDecl;
        if(AdditionalVarDecl!=null) AdditionalVarDecl.setParent(this);
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public AdditionalVarDecl getAdditionalVarDecl() {
        return AdditionalVarDecl;
    }

    public void setAdditionalVarDecl(AdditionalVarDecl AdditionalVarDecl) {
        this.AdditionalVarDecl=AdditionalVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecl!=null) VarDecl.accept(visitor);
        if(AdditionalVarDecl!=null) AdditionalVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
        if(AdditionalVarDecl!=null) AdditionalVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        if(AdditionalVarDecl!=null) AdditionalVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditionalVarDecl_COMMA(\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalVarDecl!=null)
            buffer.append(AdditionalVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditionalVarDecl_COMMA]");
        return buffer.toString();
    }
}
