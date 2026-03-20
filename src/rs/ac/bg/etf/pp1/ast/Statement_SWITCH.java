// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Statement_SWITCH extends Statement {

    private SwitchNonterm SwitchNonterm;
    private Expr Expr;
    private CaseList CaseList;

    public Statement_SWITCH (SwitchNonterm SwitchNonterm, Expr Expr, CaseList CaseList) {
        this.SwitchNonterm=SwitchNonterm;
        if(SwitchNonterm!=null) SwitchNonterm.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
    }

    public SwitchNonterm getSwitchNonterm() {
        return SwitchNonterm;
    }

    public void setSwitchNonterm(SwitchNonterm SwitchNonterm) {
        this.SwitchNonterm=SwitchNonterm;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchNonterm!=null) SwitchNonterm.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchNonterm!=null) SwitchNonterm.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchNonterm!=null) SwitchNonterm.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_SWITCH(\n");

        if(SwitchNonterm!=null)
            buffer.append(SwitchNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_SWITCH]");
        return buffer.toString();
    }
}
