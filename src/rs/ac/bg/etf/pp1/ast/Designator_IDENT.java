// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Designator_IDENT extends Designator {

    private DesName DesName;

    public Designator_IDENT (DesName DesName) {
        this.DesName=DesName;
        if(DesName!=null) DesName.setParent(this);
    }

    public DesName getDesName() {
        return DesName;
    }

    public void setDesName(DesName DesName) {
        this.DesName=DesName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesName!=null) DesName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesName!=null) DesName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesName!=null) DesName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator_IDENT(\n");

        if(DesName!=null)
            buffer.append(DesName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_IDENT]");
        return buffer.toString();
    }
}
