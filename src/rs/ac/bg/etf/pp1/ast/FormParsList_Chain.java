// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class FormParsList_Chain extends FormParsList {

    private FormPar FormPar;
    private AdditionalFormPars AdditionalFormPars;

    public FormParsList_Chain (FormPar FormPar, AdditionalFormPars AdditionalFormPars) {
        this.FormPar=FormPar;
        if(FormPar!=null) FormPar.setParent(this);
        this.AdditionalFormPars=AdditionalFormPars;
        if(AdditionalFormPars!=null) AdditionalFormPars.setParent(this);
    }

    public FormPar getFormPar() {
        return FormPar;
    }

    public void setFormPar(FormPar FormPar) {
        this.FormPar=FormPar;
    }

    public AdditionalFormPars getAdditionalFormPars() {
        return AdditionalFormPars;
    }

    public void setAdditionalFormPars(AdditionalFormPars AdditionalFormPars) {
        this.AdditionalFormPars=AdditionalFormPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPar!=null) FormPar.accept(visitor);
        if(AdditionalFormPars!=null) AdditionalFormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPar!=null) FormPar.traverseTopDown(visitor);
        if(AdditionalFormPars!=null) AdditionalFormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPar!=null) FormPar.traverseBottomUp(visitor);
        if(AdditionalFormPars!=null) AdditionalFormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsList_Chain(\n");

        if(FormPar!=null)
            buffer.append(FormPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalFormPars!=null)
            buffer.append(AdditionalFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsList_Chain]");
        return buffer.toString();
    }
}
