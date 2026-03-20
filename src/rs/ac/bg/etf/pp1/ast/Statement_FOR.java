// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class Statement_FOR extends Statement {

    private ForNonterm ForNonterm;
    private ForDesignatorStm ForDesignatorStm;
    private ForCondStart ForCondStart;
    private ForCondition ForCondition;
    private ForIncrStart ForIncrStart;
    private ForDesignatorStm ForDesignatorStm1;
    private ForBodyStart ForBodyStart;
    private Statement Statement;

    public Statement_FOR (ForNonterm ForNonterm, ForDesignatorStm ForDesignatorStm, ForCondStart ForCondStart, ForCondition ForCondition, ForIncrStart ForIncrStart, ForDesignatorStm ForDesignatorStm1, ForBodyStart ForBodyStart, Statement Statement) {
        this.ForNonterm=ForNonterm;
        if(ForNonterm!=null) ForNonterm.setParent(this);
        this.ForDesignatorStm=ForDesignatorStm;
        if(ForDesignatorStm!=null) ForDesignatorStm.setParent(this);
        this.ForCondStart=ForCondStart;
        if(ForCondStart!=null) ForCondStart.setParent(this);
        this.ForCondition=ForCondition;
        if(ForCondition!=null) ForCondition.setParent(this);
        this.ForIncrStart=ForIncrStart;
        if(ForIncrStart!=null) ForIncrStart.setParent(this);
        this.ForDesignatorStm1=ForDesignatorStm1;
        if(ForDesignatorStm1!=null) ForDesignatorStm1.setParent(this);
        this.ForBodyStart=ForBodyStart;
        if(ForBodyStart!=null) ForBodyStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForNonterm getForNonterm() {
        return ForNonterm;
    }

    public void setForNonterm(ForNonterm ForNonterm) {
        this.ForNonterm=ForNonterm;
    }

    public ForDesignatorStm getForDesignatorStm() {
        return ForDesignatorStm;
    }

    public void setForDesignatorStm(ForDesignatorStm ForDesignatorStm) {
        this.ForDesignatorStm=ForDesignatorStm;
    }

    public ForCondStart getForCondStart() {
        return ForCondStart;
    }

    public void setForCondStart(ForCondStart ForCondStart) {
        this.ForCondStart=ForCondStart;
    }

    public ForCondition getForCondition() {
        return ForCondition;
    }

    public void setForCondition(ForCondition ForCondition) {
        this.ForCondition=ForCondition;
    }

    public ForIncrStart getForIncrStart() {
        return ForIncrStart;
    }

    public void setForIncrStart(ForIncrStart ForIncrStart) {
        this.ForIncrStart=ForIncrStart;
    }

    public ForDesignatorStm getForDesignatorStm1() {
        return ForDesignatorStm1;
    }

    public void setForDesignatorStm1(ForDesignatorStm ForDesignatorStm1) {
        this.ForDesignatorStm1=ForDesignatorStm1;
    }

    public ForBodyStart getForBodyStart() {
        return ForBodyStart;
    }

    public void setForBodyStart(ForBodyStart ForBodyStart) {
        this.ForBodyStart=ForBodyStart;
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
        if(ForNonterm!=null) ForNonterm.accept(visitor);
        if(ForDesignatorStm!=null) ForDesignatorStm.accept(visitor);
        if(ForCondStart!=null) ForCondStart.accept(visitor);
        if(ForCondition!=null) ForCondition.accept(visitor);
        if(ForIncrStart!=null) ForIncrStart.accept(visitor);
        if(ForDesignatorStm1!=null) ForDesignatorStm1.accept(visitor);
        if(ForBodyStart!=null) ForBodyStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForNonterm!=null) ForNonterm.traverseTopDown(visitor);
        if(ForDesignatorStm!=null) ForDesignatorStm.traverseTopDown(visitor);
        if(ForCondStart!=null) ForCondStart.traverseTopDown(visitor);
        if(ForCondition!=null) ForCondition.traverseTopDown(visitor);
        if(ForIncrStart!=null) ForIncrStart.traverseTopDown(visitor);
        if(ForDesignatorStm1!=null) ForDesignatorStm1.traverseTopDown(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForNonterm!=null) ForNonterm.traverseBottomUp(visitor);
        if(ForDesignatorStm!=null) ForDesignatorStm.traverseBottomUp(visitor);
        if(ForCondStart!=null) ForCondStart.traverseBottomUp(visitor);
        if(ForCondition!=null) ForCondition.traverseBottomUp(visitor);
        if(ForIncrStart!=null) ForIncrStart.traverseBottomUp(visitor);
        if(ForDesignatorStm1!=null) ForDesignatorStm1.traverseBottomUp(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_FOR(\n");

        if(ForNonterm!=null)
            buffer.append(ForNonterm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStm!=null)
            buffer.append(ForDesignatorStm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondStart!=null)
            buffer.append(ForCondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondition!=null)
            buffer.append(ForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForIncrStart!=null)
            buffer.append(ForIncrStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStm1!=null)
            buffer.append(ForDesignatorStm1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBodyStart!=null)
            buffer.append(ForBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_FOR]");
        return buffer.toString();
    }
}
