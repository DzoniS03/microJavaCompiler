// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private UnaryOp UnaryOp;
    private FactorOption FactorOption;

    public Factor (UnaryOp UnaryOp, FactorOption FactorOption) {
        this.UnaryOp=UnaryOp;
        if(UnaryOp!=null) UnaryOp.setParent(this);
        this.FactorOption=FactorOption;
        if(FactorOption!=null) FactorOption.setParent(this);
    }

    public UnaryOp getUnaryOp() {
        return UnaryOp;
    }

    public void setUnaryOp(UnaryOp UnaryOp) {
        this.UnaryOp=UnaryOp;
    }

    public FactorOption getFactorOption() {
        return FactorOption;
    }

    public void setFactorOption(FactorOption FactorOption) {
        this.FactorOption=FactorOption;
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
        if(UnaryOp!=null) UnaryOp.accept(visitor);
        if(FactorOption!=null) FactorOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(UnaryOp!=null) UnaryOp.traverseTopDown(visitor);
        if(FactorOption!=null) FactorOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(UnaryOp!=null) UnaryOp.traverseBottomUp(visitor);
        if(FactorOption!=null) FactorOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(UnaryOp!=null)
            buffer.append(UnaryOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorOption!=null)
            buffer.append(FactorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
