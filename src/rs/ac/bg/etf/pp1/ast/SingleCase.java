// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class SingleCase implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private SwitchCaseCheck SwitchCaseCheck;
    private StatementList StatementList;

    public SingleCase (SwitchCaseCheck SwitchCaseCheck, StatementList StatementList) {
        this.SwitchCaseCheck=SwitchCaseCheck;
        if(SwitchCaseCheck!=null) SwitchCaseCheck.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public SwitchCaseCheck getSwitchCaseCheck() {
        return SwitchCaseCheck;
    }

    public void setSwitchCaseCheck(SwitchCaseCheck SwitchCaseCheck) {
        this.SwitchCaseCheck=SwitchCaseCheck;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SwitchCaseCheck!=null) SwitchCaseCheck.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SwitchCaseCheck!=null) SwitchCaseCheck.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SwitchCaseCheck!=null) SwitchCaseCheck.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleCase(\n");

        if(SwitchCaseCheck!=null)
            buffer.append(SwitchCaseCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleCase]");
        return buffer.toString();
    }
}
