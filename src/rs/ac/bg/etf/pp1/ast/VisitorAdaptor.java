// generated with ast extension for cup
// version 0.8
// 20/2/2026 17:17:5


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(Mulop Mulop) { }
    public void visit(Constant Constant) { }
    public void visit(EnumType EnumType) { }
    public void visit(Relop Relop) { }
    public void visit(MulopFactorList MulopFactorList) { }
    public void visit(CondTermList CondTermList) { }
    public void visit(MethTypeName MethTypeName) { }
    public void visit(FactorOption FactorOption) { }
    public void visit(StatementList StatementList) { }
    public void visit(UnaryOp UnaryOp) { }
    public void visit(Addop Addop) { }
    public void visit(ElseStm ElseStm) { }
    public void visit(AdditionalConstDecl AdditionalConstDecl) { }
    public void visit(ForCondition ForCondition) { }
    public void visit(VarDeclListExtra VarDeclListExtra) { }
    public void visit(CondFactList CondFactList) { }
    public void visit(Designator Designator) { }
    public void visit(FormParsList FormParsList) { }
    public void visit(Condition Condition) { }
    public void visit(CaseList CaseList) { }
    public void visit(ConstVarEnumDeclList ConstVarEnumDeclList) { }
    public void visit(ActParsList ActParsList) { }
    public void visit(AdditionalEnumTypes AdditionalEnumTypes) { }
    public void visit(Expr Expr) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(AdditionalActPars AdditionalActPars) { }
    public void visit(AdditionalFormPars AdditionalFormPars) { }
    public void visit(ForDesignatorStm ForDesignatorStm) { }
    public void visit(Statement Statement) { }
    public void visit(VarDecl VarDecl) { }
    public void visit(CondFact CondFact) { }
    public void visit(FormPar FormPar) { }
    public void visit(MethodDeclList MethodDeclList) { }
    public void visit(AdditionalVarDecl AdditionalVarDecl) { }
    public void visit(AddopTermList AddopTermList) { }
    public void visit(Mulop_MOD Mulop_MOD) { visit(); }
    public void visit(Mulop_DIV Mulop_DIV) { visit(); }
    public void visit(Mulop_MUL Mulop_MUL) { visit(); }
    public void visit(Addop_MINUS Addop_MINUS) { visit(); }
    public void visit(Addop_PLUS Addop_PLUS) { visit(); }
    public void visit(Relop_LESSEREQUAL Relop_LESSEREQUAL) { visit(); }
    public void visit(Relop_LESSER Relop_LESSER) { visit(); }
    public void visit(Relop_GREATEREQUAL Relop_GREATEREQUAL) { visit(); }
    public void visit(Relop_GREATER Relop_GREATER) { visit(); }
    public void visit(Relop_NOTEQUAL Relop_NOTEQUAL) { visit(); }
    public void visit(Relop_EQUAL Relop_EQUAL) { visit(); }
    public void visit(AssignOp AssignOp) { visit(); }
    public void visit(DesName DesName) { visit(); }
    public void visit(DesArrayName DesArrayName) { visit(); }
    public void visit(Designator_Length Designator_Length) { visit(); }
    public void visit(Designator_Array Designator_Array) { visit(); }
    public void visit(Designator_Enum Designator_Enum) { visit(); }
    public void visit(Designator_IDENT Designator_IDENT) { visit(); }
    public void visit(FactorOption_Expr FactorOption_Expr) { visit(); }
    public void visit(FactorOption_NEW FactorOption_NEW) { visit(); }
    public void visit(FactorOption_BOOL FactorOption_BOOL) { visit(); }
    public void visit(FactorOption_CHAR FactorOption_CHAR) { visit(); }
    public void visit(FactorOption_NUM FactorOption_NUM) { visit(); }
    public void visit(FactorOption_Des FactorOption_Des) { visit(); }
    public void visit(FactorOption_Method FactorOption_Method) { visit(); }
    public void visit(UnaryOp_e UnaryOp_e) { visit(); }
    public void visit(UnaryOp_MINUS UnaryOp_MINUS) { visit(); }
    public void visit(Factor Factor) { visit(); }
    public void visit(MulopFactorList_SingleFactor MulopFactorList_SingleFactor) { visit(); }
    public void visit(MulopFactorList_Mulop MulopFactorList_Mulop) { visit(); }
    public void visit(Term Term) { visit(); }
    public void visit(AddopTermList_SingleTerm AddopTermList_SingleTerm) { visit(); }
    public void visit(AddopTermList_Addop AddopTermList_Addop) { visit(); }
    public void visit(SimpleExpr SimpleExpr) { visit(); }
    public void visit(TernaryColon TernaryColon) { visit(); }
    public void visit(Expr_Ternary_Condition Expr_Ternary_Condition) { visit(); }
    public void visit(Expr_Simple Expr_Simple) { visit(); }
    public void visit(CondFact_Relop CondFact_Relop) { visit(); }
    public void visit(CondFact_SimpleExpr CondFact_SimpleExpr) { visit(); }
    public void visit(CondFactList_Single CondFactList_Single) { visit(); }
    public void visit(CondFactList_AND CondFactList_AND) { visit(); }
    public void visit(CondTerm CondTerm) { visit(); }
    public void visit(CondTermList_Single CondTermList_Single) { visit(); }
    public void visit(CondTermList_OR CondTermList_OR) { visit(); }
    public void visit(Condition_error Condition_error) { visit(); }
    public void visit(Condition_OK Condition_OK) { visit(); }
    public void visit(ActPar ActPar) { visit(); }
    public void visit(AdditionalActPars_e AdditionalActPars_e) { visit(); }
    public void visit(AdditionalActPars_COMMA AdditionalActPars_COMMA) { visit(); }
    public void visit(ActParsStart ActParsStart) { visit(); }
    public void visit(ActParsList_e ActParsList_e) { visit(); }
    public void visit(ActParsList_Chain ActParsList_Chain) { visit(); }
    public void visit(DesignatorStatement_error DesignatorStatement_error) { visit(); }
    public void visit(DesignatorStatement_DEC DesignatorStatement_DEC) { visit(); }
    public void visit(DesignatorStatement_INC DesignatorStatement_INC) { visit(); }
    public void visit(DesignatorStatement_ActPars DesignatorStatement_ActPars) { visit(); }
    public void visit(DesignatorStatement_Assign DesignatorStatement_Assign) { visit(); }
    public void visit(ForCondition_e ForCondition_e) { visit(); }
    public void visit(ForCondition_exists ForCondition_exists) { visit(); }
    public void visit(ForDesignatorStm_e ForDesignatorStm_e) { visit(); }
    public void visit(ForDesignatorStm_exists ForDesignatorStm_exists) { visit(); }
    public void visit(SwitchCaseCheck SwitchCaseCheck) { visit(); }
    public void visit(SingleCase SingleCase) { visit(); }
    public void visit(CaseList_e CaseList_e) { visit(); }
    public void visit(CaseList_Chain CaseList_Chain) { visit(); }
    public void visit(Else Else) { visit(); }
    public void visit(ElseStm_nay ElseStm_nay) { visit(); }
    public void visit(ElseStm_yay ElseStm_yay) { visit(); }
    public void visit(Label Label) { visit(); }
    public void visit(ForBodyStart ForBodyStart) { visit(); }
    public void visit(ForIncrStart ForIncrStart) { visit(); }
    public void visit(ForCondStart ForCondStart) { visit(); }
    public void visit(ForEachBegin ForEachBegin) { visit(); }
    public void visit(Designator2 Designator2) { visit(); }
    public void visit(Designator1 Designator1) { visit(); }
    public void visit(ForeachNonterm ForeachNonterm) { visit(); }
    public void visit(DoNonterm DoNonterm) { visit(); }
    public void visit(WhileCondBegin WhileCondBegin) { visit(); }
    public void visit(WhileNonterm WhileNonterm) { visit(); }
    public void visit(ForNonterm ForNonterm) { visit(); }
    public void visit(SwitchNonterm SwitchNonterm) { visit(); }
    public void visit(Statement_SKIP Statement_SKIP) { visit(); }
    public void visit(Statement_GOTO Statement_GOTO) { visit(); }
    public void visit(Statement_LABEL Statement_LABEL) { visit(); }
    public void visit(Statement_FOREACH Statement_FOREACH) { visit(); }
    public void visit(Statement_DO_WHILE Statement_DO_WHILE) { visit(); }
    public void visit(Statement_WHILE Statement_WHILE) { visit(); }
    public void visit(Statement_Block Statement_Block) { visit(); }
    public void visit(Statement_FOR Statement_FOR) { visit(); }
    public void visit(Statement_SWITCH Statement_SWITCH) { visit(); }
    public void visit(Statement_PRINT_NUM Statement_PRINT_NUM) { visit(); }
    public void visit(Statement_PRINT Statement_PRINT) { visit(); }
    public void visit(Statement_READ Statement_READ) { visit(); }
    public void visit(Statement_RET_Expr Statement_RET_Expr) { visit(); }
    public void visit(Statement_RETURN Statement_RETURN) { visit(); }
    public void visit(Statement_CONTINUE Statement_CONTINUE) { visit(); }
    public void visit(Statement_BREAK Statement_BREAK) { visit(); }
    public void visit(Statement_IF Statement_IF) { visit(); }
    public void visit(Statement_DesStm Statement_DesStm) { visit(); }
    public void visit(StatementList_e StatementList_e) { visit(); }
    public void visit(StatementList_Chain StatementList_Chain) { visit(); }
    public void visit(Type Type) { visit(); }
    public void visit(FormPar_error FormPar_error) { visit(); }
    public void visit(FormPar_Array FormPar_Array) { visit(); }
    public void visit(FormPar_Type FormPar_Type) { visit(); }
    public void visit(AdditionalFormPars_e AdditionalFormPars_e) { visit(); }
    public void visit(AdditionalFormPars_COMMA AdditionalFormPars_COMMA) { visit(); }
    public void visit(FormParsList_e FormParsList_e) { visit(); }
    public void visit(FormParsList_Chain FormParsList_Chain) { visit(); }
    public void visit(VarDeclListExtra_e VarDeclListExtra_e) { visit(); }
    public void visit(VarDeclListExtra_Chain VarDeclListExtra_Chain) { visit(); }
    public void visit(MethTypeName_VOID MethTypeName_VOID) { visit(); }
    public void visit(MethTypeName_Type MethTypeName_Type) { visit(); }
    public void visit(MethodDecl MethodDecl) { visit(); }
    public void visit(MethodDeclList_e MethodDeclList_e) { visit(); }
    public void visit(MethodDeclList_Chain MethodDeclList_Chain) { visit(); }
    public void visit(EnumType_IDENT_NUM EnumType_IDENT_NUM) { visit(); }
    public void visit(EnumType_IDENT EnumType_IDENT) { visit(); }
    public void visit(AdditionalEnumTypes_e AdditionalEnumTypes_e) { visit(); }
    public void visit(AdditionalEnumTypes_COMMA AdditionalEnumTypes_COMMA) { visit(); }
    public void visit(EnumDecl EnumDecl) { visit(); }
    public void visit(VarDecl_error VarDecl_error) { visit(); }
    public void visit(VarDecl_Array VarDecl_Array) { visit(); }
    public void visit(VarDecl_IDENT VarDecl_IDENT) { visit(); }
    public void visit(AdditionalVarDecl_e AdditionalVarDecl_e) { visit(); }
    public void visit(AdditionalVarDecl_COMMA AdditionalVarDecl_COMMA) { visit(); }
    public void visit(VarDeclList VarDeclList) { visit(); }
    public void visit(Constant_BOOL Constant_BOOL) { visit(); }
    public void visit(Constant_CHAR Constant_CHAR) { visit(); }
    public void visit(Constant_NUMBER Constant_NUMBER) { visit(); }
    public void visit(AdditionalConstDecl_e AdditionalConstDecl_e) { visit(); }
    public void visit(AdditionalConstDecl_Chain AdditionalConstDecl_Chain) { visit(); }
    public void visit(ConstDecl ConstDecl) { visit(); }
    public void visit(ConstDeclList ConstDeclList) { visit(); }
    public void visit(ConstVarEnumDeclList_e ConstVarEnumDeclList_e) { visit(); }
    public void visit(ConstVarEnumDeclList_Enum ConstVarEnumDeclList_Enum) { visit(); }
    public void visit(ConstVarEnumDeclList_Var ConstVarEnumDeclList_Var) { visit(); }
    public void visit(ConstVarEnumDeclList_Const ConstVarEnumDeclList_Const) { visit(); }
    public void visit(ProgName ProgName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}
