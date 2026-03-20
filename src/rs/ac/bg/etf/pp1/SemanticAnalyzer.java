package rs.ac.bg.etf.pp1;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj activeProgram;
	private Struct activeType;
	private int activeConst;
	private Struct activeConstType;
	private Struct boolType = Tab.find("bool").getType();
	private Obj activeMethod = null;
	private Obj mainMethod;
	private List<Obj> pendingEnumConstants = new ArrayList<>();
	private int nextEnumVal = 0;
	private boolean hasReturn;
	private int loopCount = 0;
	private int switchCount = 0;
	int nVars;
	
	private List<Struct> getFormalParams(Obj meth) {
		List<Struct> fpList = new ArrayList<>();
		for (Obj local : meth.getLocalSymbols()) {
			if (local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
				fpList.add(local.getType());
			}
		}
		if (fpList.isEmpty()) {
			for (Obj local : meth.getLocalSymbols()) {
				if (local.getKind() == Obj.Var) {
					fpList.add(local.getType());
				}
			}
		}
		return fpList;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean isErrorDetected() {
		return errorDetected;
	}
	
	/* ////////////////////////////////////////////////////////////////////////////// */
	
	@Override
	public void visit(ProgName programName) {
		activeProgram = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(activeProgram);
		Tab.closeScope();
		activeProgram = null;
		
		if (mainMethod == null || mainMethod.getLevel() > 0) { 
			report_error("Program nema ispravnu main metodu", program);
		}
	}
	
	// DEKLARACIJA KONSTANTI
	
	@Override
	public void visit(ConstDecl constDecl) {
		Obj constObj = Tab.find(constDecl.getI1());
		if (constObj == Tab.noObj) {
			if (activeConstType.assignableTo(activeType)) {
				constObj = Tab.insert(Obj.Con, constDecl.getI1(), activeType);
				constObj.setAdr(activeConst);
			} else {
				report_error("Neispravna dodela konstanti: " + constDecl.getI1(), constDecl);
			}
		} else {
			report_error("Visestruka definicija konstante: " + constDecl.getI1(), constDecl);
		}
		
	}
	
	@Override
	public void visit(Constant_NUMBER const_num) {
		activeConst = const_num.getN1();
		activeConstType = Tab.intType;
	}
	
	@Override
	public void visit(Constant_CHAR const_char) {
		activeConst = const_char.getC1();
		activeConstType = Tab.charType;
	}
	
	@Override
	public void visit(Constant_BOOL const_bool) {
		activeConst = const_bool.getB1();
		activeConstType = boolType;
	}
	
	// DEKLARACIJA VARIJABLI
	
	@Override
	public void visit(VarDecl_IDENT varDecl_ident) {
		Obj varObj = null;
		if (activeMethod == null) {
			varObj = Tab.find(varDecl_ident.getI1());
		} else {
			varObj = Tab.currentScope().findSymbol(varDecl_ident.getI1());
		}
		if (varObj == Tab.noObj || varObj == null) {
			varObj = Tab.insert(Obj.Var, varDecl_ident.getI1(), activeType);
		} else {
			report_error("Visestruka deklaracija promenljive: " + varDecl_ident.getI1(), varDecl_ident);
		}
		
	}
	
	@Override
	public void visit(VarDecl_Array varDecl_array) {
		Obj varObj = null;
		if (activeMethod == null) {
			varObj = Tab.find(varDecl_array.getI1());
		} else {
			varObj = Tab.currentScope().findSymbol(varDecl_array.getI1());
		}
		if (varObj == Tab.noObj || varObj == null) {
			varObj = Tab.insert(Obj.Var, varDecl_array.getI1(), new Struct(Struct.Array, activeType));
		} else {
			report_error("Visestruka deklaracija nizovske promenljive: " + varDecl_array.getI1(), varDecl_array);
		}
	}
	
	// DEKLARACIJA METODA
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Tab.chainLocalSymbols(activeMethod);
		Tab.closeScope();
		
		if (activeMethod.getType() != Tab.noType && !hasReturn) {
			report_error("Ne postoji return unutar funkcije " + activeMethod.getName(), methodDecl);
		}
		
		if (!labels.containsAll(gotos)) {
			report_error("Neispravna goto naredba u metodi " + activeMethod.getName(), methodDecl);
		}
		
		labels = null;
		gotos = null;
		hasReturn = false;
		activeMethod = null;
	}
	
	@Override
	public void visit(MethTypeName_VOID methTypeName_void) {
		Obj exists = Tab.currentScope().findSymbol(methTypeName_void.getI1());
		if (exists != null) {
			report_error("Visestruka upotreba imena unutar opsega: " + methTypeName_void.getI1(), methTypeName_void);
			activeMethod = Tab.noObj;
		} else {
			activeMethod = Tab.insert(Obj.Meth, methTypeName_void.getI1(), Tab.noType);
			methTypeName_void.obj = activeMethod;
		}
		Tab.openScope();
		if (methTypeName_void.getI1().equalsIgnoreCase("main")) mainMethod = activeMethod;
		labels = new HashSet<String>();
		gotos = new HashSet<String>();
	}
	
	@Override
	public void visit(MethTypeName_Type methTypeName_type) {
		Obj exists = Tab.currentScope().findSymbol(methTypeName_type.getI2());
		if (exists != null) {
			report_error("Visestruka upotreba imena unutar opsega: " + methTypeName_type.getI2(), methTypeName_type);
			activeMethod = Tab.noObj;
		} else {
			activeMethod = Tab.insert(Obj.Meth, methTypeName_type.getI2(), activeType);
			methTypeName_type.obj = activeMethod;
		}
		Tab.openScope();
		labels = new HashSet<String>();
		gotos = new HashSet<String>();
	}
	
	/*
	 * ovakvi visitori dopustaju ponavljanje imena metoda unutar istog scope
	@Override
	public void visit(MethTypeName_VOID methTypeName_void) {
		activeMethod = Tab.insert(Obj.Meth, methTypeName_void.getI1(), Tab.noType);
		Tab.openScope();
		if (methTypeName_void.getI1().equalsIgnoreCase("main")) mainMethod = activeMethod;
	}
	
	@Override
	public void visit(MethTypeName_Type methTypeName_type) {
		activeMethod = Tab.insert(Obj.Meth, methTypeName_type.getI2(), activeType);
		Tab.openScope();
	}*/	
	
	// DEKLARACIJA FORMALNIH PARAMETARA
	
	@Override
	public void visit(FormPar_Type formPar_type) {
		Obj parObj = null;
		if (activeMethod == null) {
			report_error("Greska: FormPar_Type", formPar_type);
		} else {
			parObj = Tab.currentScope().findSymbol(formPar_type.getI2());
		}
		if (parObj == Tab.noObj || parObj == null) {
			parObj = Tab.insert(Obj.Var, formPar_type.getI2(), activeType);
			activeMethod.setLevel(activeMethod.getLevel() + 1);
			parObj.setFpPos(1);
		} else {
			report_error("Visestruka deklaracija formalnog parametra: " + formPar_type.getI2(), formPar_type);
		}
	}
	
	@Override
	public void visit(FormPar_Array formPar_array) {
		Obj parObj = null;
		if (activeMethod == null) {
			report_error("Greska: FormPar_Type", formPar_array);
		} else {
			parObj = Tab.currentScope().findSymbol(formPar_array.getI2());
		}
		if (parObj == Tab.noObj || parObj == null) {
			parObj = Tab.insert(Obj.Var, formPar_array.getI2(), new Struct(Struct.Array, activeType));
			activeMethod.setLevel(activeMethod.getLevel() + 1);
			parObj.setFpPos(1);
		} else {
			report_error("Visestruka deklaracija formalnog parametra: " + formPar_array.getI2(), formPar_array);
		}
	}
	
	// TIPOVI
	
	@Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getI1());
		if (typeObj == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			activeType = Tab.noType;
			type.struct = activeType;
		} else if (typeObj.getKind() != Obj.Type) {
			report_error("Pogresan tip podatka: " + type.getI1(), type);
			activeType = Tab.noType;
			type.struct = activeType;
		} else {
			activeType = typeObj.getType();
			if (activeType.getKind() == Struct.Enum) {
				activeType = Tab.intType;
			}
			type.struct = activeType;
		}
	}
	
	// DEKLARACIJA ENUMA
	
	@Override
	public void visit(EnumType_IDENT enumTupe_ident) {
		Obj enumConst = new Obj(Obj.Con, enumTupe_ident.getI1(), Tab.intType);
		enumConst.setAdr(nextEnumVal);
		pendingEnumConstants.add(enumConst);
		nextEnumVal++;
	}
	
	@Override
	public void visit(EnumType_IDENT_NUM enumType_ident_num) {
		Obj enumConst = new Obj(Obj.Con, enumType_ident_num.getI1(), Tab.intType);
		int value = enumType_ident_num.getN2();
		enumConst.setAdr(value);
		pendingEnumConstants.add(enumConst);
		nextEnumVal = value + 1;
	}
	
	@Override
	public void visit(EnumDecl enumDecl) {
		Obj enumObj = Tab.find(enumDecl.getI1());
		if (enumObj != Tab.noObj) {
			report_error("Visestruka definicija nabrajanja: " + enumDecl.getI1(), enumDecl);
			pendingEnumConstants.clear();
			nextEnumVal = 0;
			return;
		}
		
		Struct enumType = new Struct(Struct.Enum);
		enumObj = Tab.insert(Obj.Type, enumDecl.getI1(), enumType);
		
		Tab.openScope();
		for (Obj constant : pendingEnumConstants) {
			Obj existing = Tab.currentScope().findSymbol(constant.getName());
			if (existing != null) {
				report_error("Visestruka definicija enum konstante: " + constant.getName(), enumDecl);
			} else {
				Tab.insert(constant.getKind(), constant.getName(), constant.getType()).setAdr(constant.getAdr());
			}
		}
		
		Tab.chainLocalSymbols(enumType);
		Tab.closeScope();
	
		pendingEnumConstants.clear();
		nextEnumVal = 0;
	}
	
	// FactorOptions
	
	@Override
	public void visit(FactorOption_NUM factorOption_num) {
		factorOption_num.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorOption_CHAR factorOption_char) {
		factorOption_char.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorOption_BOOL factorOption_bool) {
		factorOption_bool.struct = boolType;
	}
	
	@Override
	public void visit(FactorOption_Des factorOption_des) {
		factorOption_des.struct = factorOption_des.getDesignator().obj.getType();
	}
	
	@Override
	public void visit(FactorOption_NEW factorOption_new) {
		if (factorOption_new.getExpr().struct.equals(Tab.intType)) {
			factorOption_new.struct = new Struct(Struct.Array, activeType);
		} else {
			report_error("Zadata velicina niza nije int tipa", factorOption_new);
			factorOption_new.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(FactorOption_Expr factorOption_expr) {
		factorOption_expr.struct = factorOption_expr.getExpr().struct;
	}
	
	@Override
	public void visit(FactorOption_Method factorOption_method) {
		String name = factorOption_method.getDesignator().obj.getName();
		if (factorOption_method.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Neispravan poziv metode " + name, factorOption_method);
			factorOption_method.struct = Tab.noType;
		} else {
			factorOption_method.struct = factorOption_method.getDesignator().obj.getType();

			List<Struct> fpList = getFormalParams(factorOption_method.getDesignator().obj);
			ActParsCounter parsCounter = new ActParsCounter();
			factorOption_method.getActParsList().traverseBottomUp(parsCounter);
			List<Struct> apList = parsCounter.topActParsList;
//			report_info("BROJ FP " + fpList.size(), designatorStatement_ActPars);
//			report_info("BROJ AP " + apList.size(), designatorStatement_ActPars);
			
			if (fpList.size() != apList.size()) {
				report_error("Neispravan broj parametara prilikom poziva metode " + name, factorOption_method);
			} else {
				for (int i = 0; i < fpList.size(); i++) {
					Struct aps = apList.get(i);
					Struct fps = fpList.get(i);
					if(!aps.assignableTo(fps)) {
						report_error("Nekompatiblini tipovi prilikom poziva metode " + name, factorOption_method);
					}
				}
			}
		}
	}
	
	// Factor
	
	@Override
	public void visit(Factor factor) {
		if (factor.getUnaryOp() instanceof UnaryOp_MINUS) {
			if (factor.getFactorOption().struct.equals(Tab.intType)) {
				factor.struct = Tab.intType;
			} else {
				report_error("Negirana je vrednost koja nije int", factor);
				factor.struct = Tab.noType;
			}
		} else {
			factor.struct = factor.getFactorOption().struct;
		}
	}
	
	// Designator
	
	@Override
	public void visit(Designator_IDENT designator_ident) {
		Obj identObj = Tab.find(designator_ident.getDesName().getI1());
		if (identObj == Tab.noObj) {
			report_error("Iskoriscena je nedefinisana promenljiva: " + designator_ident.getDesName().getI1(), designator_ident);
			designator_ident.obj = Tab.noObj;
		} else if (identObj.getKind() != Obj.Con && identObj.getKind() != Obj.Var && identObj.getKind() != Obj.Meth) {
			report_error("Iskoriscena je neadekvatna promenljiva: " + designator_ident.getDesName().getI1(), designator_ident);
			designator_ident.obj = Tab.noObj;
		} else {
			designator_ident.obj = identObj;
			if (designator_ident.obj.getKind() == Obj.Con) {
				report_info("Pristup simbolickoj konstanti " + designator_ident.obj.getName()
				+ " [Kind: " + designator_ident.obj.getKind() + ", "
				+ "Type: " + designator_ident.obj.getType().getKind() + ", "
				+ "Adr: " + designator_ident.obj.getAdr() + ", "
				+ "Lvl: " + designator_ident.obj.getLevel() + "]", designator_ident);
			}
			if (designator_ident.obj.getKind() == Obj.Var) {
				Obj varObj = Tab.currentScope().findSymbol(designator_ident.obj.getName());
				if (varObj == Tab.noObj || varObj == null) {
					report_info("Pristup globalnoj promenljivoj " + designator_ident.obj.getName()
					+ " [Kind: " + designator_ident.obj.getKind() + ", "
					+ "Type: " + designator_ident.obj.getType().getKind() + ", "
					+ "Adr: " + designator_ident.obj.getAdr() + ", "
					+ "Lvl: " + designator_ident.obj.getLevel() + "]", designator_ident);
				} else if (designator_ident.obj.getFpPos() > 0) {
					report_info("Koriscenje formalnog argumenta funkcije " + designator_ident.obj.getName()
					+ " [Kind: " + designator_ident.obj.getKind() + ", "
					+ "Type: " + designator_ident.obj.getType().getKind() + ", "
					+ "Adr: " + designator_ident.obj.getAdr() + ", "
					+ "Lvl: " + designator_ident.obj.getLevel() + "]", designator_ident);
				} else {
					report_info("Pristup lokalnoj promenljivoj " + designator_ident.obj.getName()
					+ " [Kind: " + designator_ident.obj.getKind() + ", "
					+ "Type: " + designator_ident.obj.getType().getKind() + ", "
					+ "Adr: " + designator_ident.obj.getAdr() + ", "
					+ "Lvl: " + designator_ident.obj.getLevel() + "]", designator_ident);
				}
			}
			if (designator_ident.obj.getKind() == Obj.Meth) {
				report_info("Poziv globalne funkcije " + designator_ident.obj.getName()
				+ " [Kind: " + designator_ident.obj.getKind() + ", "
				+ "Type: " + designator_ident.obj.getType().getKind() + ", "
				+ "Adr: " + designator_ident.obj.getAdr() + ", "
				+ "Lvl: " + designator_ident.obj.getLevel() + "]", designator_ident);
			}
		}
	}
	
	@Override
	public void visit(DesArrayName desArrayName) {
		Obj desArrObj = Tab.find(desArrayName.getI1());
		if (desArrObj == Tab.noObj) {
			report_error("Iskoriscena je nedefinisana promenljiva niza " + desArrayName.getI1(), desArrayName);
			desArrayName.obj = Tab.noObj;
		} else if (desArrObj.getKind() != Obj.Var || desArrObj.getType().getKind() != Struct.Array) {
			report_error("Iskoriscena je neadekvatna promenljiva niza " + desArrayName.getI1(), desArrayName);
			desArrayName.obj = Tab.noObj;
		} else {
			desArrayName.obj = desArrObj;
		}
	}
	
	@Override
	public void visit(DesName desName) {
		Obj desArrObj = Tab.find(desName.getI1());
		if (desArrObj.getKind() != Obj.Var || desArrObj.getType().getKind() != Struct.Array) {
			desName.obj = Tab.noObj;
		} else {
			desName.obj = desArrObj;
		}
	}
	
	@Override
	public void visit(Designator_Array designator_array) {
		Obj arrElemObj = designator_array.getDesArrayName().obj;
		if (arrElemObj == Tab.noObj) {
			designator_array.obj = Tab.noObj;
		} else if (!designator_array.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje vrednoscu koja nije int tipa", designator_array);
			designator_array.obj = Tab.noObj;
		} else {
			designator_array.obj = new Obj(Obj.Elem, arrElemObj.getName() + "[x]", arrElemObj.getType().getElemType());
			report_info("Pristup elementu niza " + arrElemObj.getName()
				+ " [Kind: " + designator_array.obj.getKind() + ", "
				+ "Type: " + designator_array.obj.getType().getKind() + "]", designator_array);
		}
	}
	
	@Override
	public void visit(Designator_Enum designator_enum) {
		Obj enumTypeObj = Tab.find(designator_enum.getDesName().getI1());
		if (enumTypeObj == Tab.noObj || enumTypeObj.getKind() != Obj.Type
				|| enumTypeObj.getType().getKind() != Struct.Enum) {
			report_error("Nepostojeci enum tip " + designator_enum.getDesName().getI1(), designator_enum);
			designator_enum.obj = Tab.noObj;
			return;
		}
		Obj enumConst = null;
		for (Obj member : enumTypeObj.getType().getMembers()) {
			if (member.getName().equals(designator_enum.getI2())) {
				enumConst = member;
				break;
			}
		}
		if (enumConst == null) {
			report_error("Nepostojeca enum konstanta " + designator_enum.getI2() + " u enumu " + designator_enum.getDesName().getI1(), designator_enum);
			designator_enum.obj = Tab.noObj;
		} else {
			designator_enum.obj = enumConst;
		}
	}

	@Override
	public void visit(Designator_Length designator_length) {
		Obj arrObj = Tab.find(designator_length.getDesName().getI1());
		if (arrObj == Tab.noObj) {
			report_error("Nepostojeca nizovska promenljiva " + designator_length.getDesName().getI1(), designator_length);
			designator_length.obj = Tab.noObj;
		} else if (arrObj.getKind() != Obj.Var || arrObj.getType().getKind() != Struct.Array) {
			report_error("Operator length primenjen nad ne-nizovskom promenljivom " + designator_length.getDesName().getI1(), designator_length);
			designator_length.obj = Tab.noObj;
		} else {
			designator_length.obj = new Obj(Obj.Con, designator_length.getDesName().getI1() + ".length", Tab.intType);
			// designator_length.getDesName().obj = arrObj;
			// report_info("Usao u length", designator_length);
		}
	}

	// Factor List
	
	@Override
	public void visit(MulopFactorList_SingleFactor mulopFactorList_SingleFactor) {
		mulopFactorList_SingleFactor.struct = mulopFactorList_SingleFactor.getFactor().struct;
	}
	
	@Override
	public void visit(MulopFactorList_Mulop mulopFactorList_mulop) {
		if (mulopFactorList_mulop.getMulopFactorList().struct.equals(Tab.intType) &&
				mulopFactorList_mulop.getFactor().struct.equals(Tab.intType)) {
			mulopFactorList_mulop.struct = Tab.intType;
		} else {
			report_error("Mulop operacija vrednostima koje nisu tipa int", mulopFactorList_mulop);
			mulopFactorList_mulop.struct = Tab.noType;
		}
	}
	
	// Term
	
	@Override
	public void visit(Term term) {
		term.struct = term.getMulopFactorList().struct;
	}
	
	// Term List
	
	@Override
	public void visit(AddopTermList_SingleTerm addopTermList_SingleTerm) {
		addopTermList_SingleTerm.struct = addopTermList_SingleTerm.getTerm().struct;
	}
	
	@Override
	public void visit(AddopTermList_Addop addopTermList_addop) {
		if (addopTermList_addop.getAddopTermList().struct.equals(Tab.intType) &&
				addopTermList_addop.getTerm().struct.equals(Tab.intType)) {
			addopTermList_addop.struct = Tab.intType;
		} else {
			report_error("Addop operacija vrednostima koje nisu tipa int", addopTermList_addop);
			addopTermList_addop.struct = Tab.noType;
		}
	}
	
	// Expr

	@Override
	public void visit(Expr_Simple expr_Simple) {
		expr_Simple.struct = expr_Simple.getSimpleExpr().struct;
	}
	
	@Override
	public void visit(SimpleExpr simpleExpr) {
		simpleExpr.struct = simpleExpr.getAddopTermList().struct;
	}

	@Override
	public void visit(Expr_Ternary_Condition expr_Ternary_Condition) {
		Struct trueType = expr_Ternary_Condition.getExpr().struct;
		Struct falseType = expr_Ternary_Condition.getExpr1().struct;
		if (!trueType.assignableTo(falseType) && !falseType.assignableTo(trueType)) {
			report_error("Nekompatibilni tipovi grana u ternarnom izrazu", expr_Ternary_Condition);
			expr_Ternary_Condition.struct = Tab.noType;
		} else {
			expr_Ternary_Condition.struct = trueType;
		}
	}

	// Designator Statement
	
	@Override
	public void visit(DesignatorStatement_Assign designatorStatement_Assign) {
		int kind = designatorStatement_Assign.getDesignator().obj.getKind();
		String name = designatorStatement_Assign.getDesignator().obj.getName();
		if (kind != Obj.Var && kind != Obj.Elem) {
			report_error("Pokusaj dodele vrednosti u konstantu " + name, designatorStatement_Assign);
		} else if (!designatorStatement_Assign.getExpr().struct.assignableTo(designatorStatement_Assign.getDesignator().obj.getType())) {
			report_error("Neadekvatna dodela vrednosti u promenljivu " + name, designatorStatement_Assign);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_INC designatorStatement_INC) {
		int kind = designatorStatement_INC.getDesignator().obj.getKind();
		String name = designatorStatement_INC.getDesignator().obj.getName();
		if (kind != Obj.Var && kind != Obj.Elem) {
			report_error("Inkrement neadekvatne promenljive " + name, designatorStatement_INC);
		} else if (!designatorStatement_INC.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Pokusaj inkrementa promenljive " + name + " koja nije int tipa", designatorStatement_INC);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_DEC designatorStatement_DEC) {
		int kind = designatorStatement_DEC.getDesignator().obj.getKind();
		String name = designatorStatement_DEC.getDesignator().obj.getName();
		if (kind != Obj.Var && kind != Obj.Elem) {
			report_error("Dekrement neadekvatne promenljive " + name, designatorStatement_DEC);
		} else if (!designatorStatement_DEC.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Pokusaj dekrementa promenljive " + name + " koja nije int tipa", designatorStatement_DEC);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_ActPars designatorStatement_ActPars) {
		String name = designatorStatement_ActPars.getDesignator().obj.getName();
		if (designatorStatement_ActPars.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Neispravan poziv metode " + name, designatorStatement_ActPars);
		} else {
			List<Struct> fpList = getFormalParams(designatorStatement_ActPars.getDesignator().obj);
			ActParsCounter parsCounter = new ActParsCounter();
			designatorStatement_ActPars.getActParsList().traverseBottomUp(parsCounter);
			List<Struct> apList = parsCounter.topActParsList;
//			report_info("BROJ FP " + fpList.size(), designatorStatement_ActPars);
//			report_info("BROJ AP " + apList.size(), designatorStatement_ActPars);
			
			if (fpList.size() != apList.size()) {
				report_error("Neispravan broj parametara prilikom poziva metode " + name, designatorStatement_ActPars);
			} else {
				for (int i = 0; i < fpList.size(); i++) {
					Struct aps = apList.get(i);
					Struct fps = fpList.get(i);
					if(!aps.assignableTo(fps)) {
						report_error("Nekompatiblini tipovi prilikom poziva metode " + name, designatorStatement_ActPars);
					}
				}
			}
			
		}
	}
	
	// Statement
	
	@Override
	public void visit(Statement_READ statement_READ) {
		int kind = statement_READ.getDesignator().obj.getKind();
		Struct type = statement_READ.getDesignator().obj.getType();
		String name = statement_READ.getDesignator().obj.getName();
		if (kind != Obj.Var && kind != Obj.Elem) {
			report_error("Neadekvatna read operacija promenljive " + name, statement_READ);
		} else if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("Read nad promenljivom " + name + " koja nije int/char/bool", statement_READ);
		}
	}
	
	@Override 
	public void visit(Statement_PRINT statement_PRINT) {
		Struct type = statement_PRINT.getExpr().struct;
		if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("Print operacija nad izrazom koji nije int/char/bool", statement_PRINT);
		}
	}
	
	@Override
	public void visit(Statement_PRINT_NUM statement_PRINT_NUM) {
		Struct type = statement_PRINT_NUM.getExpr().struct;
		if (!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("Print operacija nad izrazom koji nije int/char/bool", statement_PRINT_NUM);
		}
	}
	
	@Override
	public void visit(Statement_RETURN statement_RETURN) {
		hasReturn = true;
		if (activeMethod.getType() != Tab.noType) {
			report_error("Neispravna return nardeba unutar metode " + activeMethod.getName(), statement_RETURN);
		}
	}
	
	@Override
	public void visit(Statement_RET_Expr statement_RET_Expr) {
		hasReturn = true;
		if (!activeMethod.getType().equals(statement_RET_Expr.getExpr().struct)) {
			report_error("Neispravan tip return nardebe unutar metode " + activeMethod.getName(), statement_RET_Expr);
		}
	}
	
	@Override
	public void visit(ForNonterm forNonterm) {
		loopCount++;
	}
	
	@Override
	public void visit(SwitchNonterm switchNonterm) {
		switchCount++;
	}
	
	@Override
	public void visit(Statement_FOR statement_FOR) {
		loopCount--;
	}
	
	@Override
	public void visit(Statement_SWITCH statement_SWITCH) {
		switchCount--;
	}
	
	@Override
	public void visit(Statement_BREAK statement_BREAK) {
		if (loopCount == 0 && switchCount == 0) {
			report_error("Break naredba van tela petlje ili switch-a", statement_BREAK);
		}
	}
	
	@Override
	public void visit(Statement_CONTINUE statement_CONTINUE) {
		if (loopCount == 0) {
			report_error("Continue naredba van tela petlje ili switch-a", statement_CONTINUE);
		}
	}
	
	// Condition
	
	@Override
	public void visit(CondFact_SimpleExpr condFact_SimpleExpr) {
		if (!condFact_SimpleExpr.getSimpleExpr().struct.equals(boolType)) {
			report_error("Logicki operand nije bool tipa", condFact_SimpleExpr);
			condFact_SimpleExpr.struct = Tab.noType;
		} else {
			condFact_SimpleExpr.struct = boolType;
		}
	}
	
	@Override
	public void visit(CondFact_Relop condFact_Relop) {
		Struct leftExpr = condFact_Relop.getSimpleExpr().struct;
		Struct rightExpr = condFact_Relop.getSimpleExpr1().struct;
		if (leftExpr.compatibleWith(rightExpr)) {
			if (leftExpr.isRefType() || rightExpr.isRefType()) {
				if (condFact_Relop.getRelop() instanceof Relop_EQUAL || condFact_Relop.getRelop() instanceof Relop_NOTEQUAL) {
					condFact_Relop.struct = boolType;
				} else {
					report_error("Poredjenje tipova je izvrseno pogresnim relacionim operatorom", condFact_Relop);
					condFact_Relop.struct = Tab.noType;
				}
			} else {
				condFact_Relop.struct = boolType;
			}
		} else {
			report_error("Logicki operandi nisu kompatibilni", condFact_Relop);
			condFact_Relop.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondFactList_Single condFactList_Single) {
		condFactList_Single.struct = condFactList_Single.getCondFact().struct;
	}
	
	@Override
	public void visit(CondFactList_AND condFactList_AND) {
		Struct leftAnd = condFactList_AND.getCondFactList().struct;
		Struct rightAnd = condFactList_AND.getCondFact().struct;
		if (leftAnd.equals(boolType) && rightAnd.equals(boolType)) {
			condFactList_AND.struct = boolType;
		} else {
			report_error("And operacija nad ne bool vrednostima", condFactList_AND);
			condFactList_AND.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		condTerm.struct = condTerm.getCondFactList().struct;
	}
	
	@Override
	public void visit(CondTermList_Single condTermList_Single) {
		condTermList_Single.struct = condTermList_Single.getCondTerm().struct;
	}
	
	@Override
	public void visit(CondTermList_OR condTermList_OR) {
		Struct leftOr = condTermList_OR.getCondTermList().struct;
		Struct rightOr = condTermList_OR.getCondTerm().struct;
		if (leftOr.equals(boolType) && rightOr.equals(boolType)) {
			condTermList_OR.struct = boolType;
		} else {
			report_error("Or operacija nad ne bool vrednostima", condTermList_OR);
			condTermList_OR.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Condition_OK condition_OK) {
		condition_OK.struct = condition_OK.getCondTermList().struct;
		if (!condition_OK.struct.equals(boolType)) {
			report_error("Uslov nije bool tipa", condition_OK);
		}
	}
	
	// while
	
	@Override
	public void visit(Statement_WHILE statement_WHILE) {
		Struct cond = statement_WHILE.getCondition().struct;
		if (!cond.equals(boolType)) {
			report_error("Uslov while petlje nije bool tipa", statement_WHILE);
		} else {
			loopCount--;
		}
	}
	
	@Override
	public void visit(WhileNonterm whileNonterm) {
		loopCount++;
	}
	
	// do while
	
	@Override
	public void visit(Statement_DO_WHILE statement_DO_WHILE) {
		Struct cond = statement_DO_WHILE.getCondition().struct;
		if (!cond.equals(boolType)) {
			report_error("Uslov do while petlje nije bool tipa", statement_DO_WHILE);
		} else {
			loopCount--;
		}
	}
	
	@Override
	public void visit(DoNonterm doNonterm) {
		loopCount++;
	}
	
	// foreach
	
	@Override
	public void visit(Statement_FOREACH statement_FOREACH) {
		Obj des1 = statement_FOREACH.getDesignator1().obj;
		Obj des2 = statement_FOREACH.getDesignator2().obj;
		if (des2.getType().getKind() == Struct.Array && des1.getType().equals(des2.getType().getElemType())) {
			loopCount--;
			return;
		} else {
			report_error("Tipovi promenljivih u foreach petlji se ne poklapaju", statement_FOREACH);
		}
	}
	
	@Override
	public void visit(Designator1 designator1) {
		designator1.obj = designator1.getDesignator().obj;
	}
	
	@Override
	public void visit(Designator2 designator2) {
		designator2.obj = designator2.getDesignator().obj;
	}
	
	@Override
	public void visit(ForeachNonterm foreachNonterm) {
		loopCount++;
	}
	
	private Set<String> labels = null;
	private Set<String> gotos = null;
	
	// Label
	@Override
	public void visit(Label label) {
		if (!labels.add(label.getI1())) {
			report_error("Visestruka definicija labele " + label.getI1(), label);
		}
	}
	
	// goto
	@Override
	public void visit(Statement_GOTO statement_GOTO) {
		gotos.add(statement_GOTO.getI1());
	}
		
}
