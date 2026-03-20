// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class AdditionalActPars_COMMA extends AdditionalActPars {

    private ActPar ActPar;
    private AdditionalActPars AdditionalActPars;

    public AdditionalActPars_COMMA (ActPar ActPar, AdditionalActPars AdditionalActPars) {
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.AdditionalActPars=AdditionalActPars;
        if(AdditionalActPars!=null) AdditionalActPars.setParent(this);
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public AdditionalActPars getAdditionalActPars() {
        return AdditionalActPars;
    }

    public void setAdditionalActPars(AdditionalActPars AdditionalActPars) {
        this.AdditionalActPars=AdditionalActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPar!=null) ActPar.accept(visitor);
        if(AdditionalActPars!=null) AdditionalActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(AdditionalActPars!=null) AdditionalActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(AdditionalActPars!=null) AdditionalActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditionalActPars_COMMA(\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalActPars!=null)
            buffer.append(AdditionalActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditionalActPars_COMMA]");
        return buffer.toString();
    }
}
