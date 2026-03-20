// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Statement_FOREACH extends Statement {

    private ForeachNonterm ForeachNonterm;
    private ForEachBegin ForEachBegin;
    private Designator1 Designator1;
    private Designator2 Designator2;
    private Statement Statement;

    public Statement_FOREACH (ForeachNonterm ForeachNonterm, ForEachBegin ForEachBegin, Designator1 Designator1, Designator2 Designator2, Statement Statement) {
        this.ForeachNonterm=ForeachNonterm;
        if(ForeachNonterm!=null) ForeachNonterm.setParent(this);
        this.ForEachBegin=ForEachBegin;
        if(ForEachBegin!=null) ForEachBegin.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.Designator2=Designator2;
        if(Designator2!=null) Designator2.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForeachNonterm getForeachNonterm() {
        return ForeachNonterm;
    }

    public void setForeachNonterm(ForeachNonterm ForeachNonterm) {
        this.ForeachNonterm=ForeachNonterm;
    }

    public ForEachBegin getForEachBegin() {
        return ForEachBegin;
    }

    public void setForEachBegin(ForEachBegin ForEachBegin) {
        this.ForEachBegin=ForEachBegin;
    }

    public Designator1 getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator1 Designator1) {
        this.Designator1=Designator1;
    }

    public Designator2 getDesignator2() {
        return Designator2;
    }

    public void setDesignator2(Designator2 Designator2) {
        this.Designator2=Designator2;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForeachNonterm!=null) ForeachNonterm.accept(visitor);
        if(ForEachBegin!=null) ForEachBegin.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(Designator2!=null) Designator2.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachNonterm!=null) ForeachNonterm.traverseTopDown(visitor);
        if(ForEachBegin!=null) ForEachBegin.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(Designator2!=null) Designator2.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachNonterm!=null) ForeachNonterm.traverseBottomUp(visitor);
        if(ForEachBegin!=null) ForEachBegin.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(Designator2!=null) Designator2.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_FOREACH(\n");

        if(ForeachNonterm!=null)
            buffer.append(ForeachNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachBegin!=null)
            buffer.append(ForEachBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator2!=null)
            buffer.append(Designator2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_FOREACH]");
        return buffer.toString();
    }
}
