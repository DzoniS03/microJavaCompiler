// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public class AdditionalEnumTypes_COMMA extends AdditionalEnumTypes {

    private EnumType EnumType;
    private AdditionalEnumTypes AdditionalEnumTypes;

    public AdditionalEnumTypes_COMMA (EnumType EnumType, AdditionalEnumTypes AdditionalEnumTypes) {
        this.EnumType=EnumType;
        if(EnumType!=null) EnumType.setParent(this);
        this.AdditionalEnumTypes=AdditionalEnumTypes;
        if(AdditionalEnumTypes!=null) AdditionalEnumTypes.setParent(this);
    }

    public EnumType getEnumType() {
        return EnumType;
    }

    public void setEnumType(EnumType EnumType) {
        this.EnumType=EnumType;
    }

    public AdditionalEnumTypes getAdditionalEnumTypes() {
        return AdditionalEnumTypes;
    }

    public void setAdditionalEnumTypes(AdditionalEnumTypes AdditionalEnumTypes) {
        this.AdditionalEnumTypes=AdditionalEnumTypes;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumType!=null) EnumType.accept(visitor);
        if(AdditionalEnumTypes!=null) AdditionalEnumTypes.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumType!=null) EnumType.traverseTopDown(visitor);
        if(AdditionalEnumTypes!=null) AdditionalEnumTypes.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumType!=null) EnumType.traverseBottomUp(visitor);
        if(AdditionalEnumTypes!=null) AdditionalEnumTypes.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditionalEnumTypes_COMMA(\n");

        if(EnumType!=null)
            buffer.append(EnumType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalEnumTypes!=null)
            buffer.append(AdditionalEnumTypes.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditionalEnumTypes_COMMA]");
        return buffer.toString();
    }
}
