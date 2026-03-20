// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Statement_WHILE extends Statement {

    private WhileNonterm WhileNonterm;
    private WhileCondBegin WhileCondBegin;
    private Condition Condition;
    private Statement Statement;

    public Statement_WHILE (WhileNonterm WhileNonterm, WhileCondBegin WhileCondBegin, Condition Condition, Statement Statement) {
        this.WhileNonterm=WhileNonterm;
        if(WhileNonterm!=null) WhileNonterm.setParent(this);
        this.WhileCondBegin=WhileCondBegin;
        if(WhileCondBegin!=null) WhileCondBegin.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileNonterm getWhileNonterm() {
        return WhileNonterm;
    }

    public void setWhileNonterm(WhileNonterm WhileNonterm) {
        this.WhileNonterm=WhileNonterm;
    }

    public WhileCondBegin getWhileCondBegin() {
        return WhileCondBegin;
    }

    public void setWhileCondBegin(WhileCondBegin WhileCondBegin) {
        this.WhileCondBegin=WhileCondBegin;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
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
        if(WhileNonterm!=null) WhileNonterm.accept(visitor);
        if(WhileCondBegin!=null) WhileCondBegin.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileNonterm!=null) WhileNonterm.traverseTopDown(visitor);
        if(WhileCondBegin!=null) WhileCondBegin.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileNonterm!=null) WhileNonterm.traverseBottomUp(visitor);
        if(WhileCondBegin!=null) WhileCondBegin.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_WHILE(\n");

        if(WhileNonterm!=null)
            buffer.append(WhileNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileCondBegin!=null)
            buffer.append(WhileCondBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_WHILE]");
        return buffer.toString();
    }
}
