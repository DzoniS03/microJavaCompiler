package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPC;
	
	public int getMainPc() {
		return this.mainPC;
	}
	
	public CodeGenerator() {
		Obj chr = Tab.find("chr");
		Obj ord = Tab.find("ord");
		chr.setAdr(Code.pc);
		ord.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj len = Tab.find("len");
		len.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// Code.put(0) upisuje na code 1B sa vrednoscu 0 u compile time
	// Code.loadConst(0) upisuje na code const_0 instrukciju u compile time i onda 
	// ta instrukcija u runtime upisuje na expression stack vrednost 0 na sirinu 4B
	
	// metode
	
	@Override
	public void visit(MethTypeName_Type methTypeName_Type) {
		methTypeName_Type.obj.setAdr(Code.pc);
		if (methTypeName_Type.getI2().equalsIgnoreCase("main")) {
			this.mainPC = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(methTypeName_Type.obj.getLevel());
		Code.put(methTypeName_Type.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethTypeName_VOID methTypeName_VOID) {
		methTypeName_VOID.obj.setAdr(Code.pc);
		if (methTypeName_VOID.getI1().equalsIgnoreCase("main")) {
			this.mainPC = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(methTypeName_VOID.obj.getLevel());
		Code.put(methTypeName_VOID.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// statementi
	
	@Override
	public void visit(Statement_PRINT statement_PRINT) {
		if (skipping) {
			cnt--;
			if (cnt == 0) {
				Code.fixup(skipAddr);
				skipping = false;
			}
		} else {
			Code.loadConst(0);
			if (!statement_PRINT.getExpr().struct.equals(Tab.charType)) {
				Code.put(Code.print);
			} else {
				Code.put(Code.bprint);
			}
		}
	}
	
	@Override
	public void visit(Statement_PRINT_NUM statement_PRINT_NUM) {
		Code.loadConst(statement_PRINT_NUM.getN2());
		if (!statement_PRINT_NUM.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.print);
		} else {
			Code.put(Code.bprint);
		}
	}
	
	@Override
	public void visit(Statement_RETURN statement_RETURN) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_RET_Expr Statement_RET_Expr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_READ statement_READ) {
		if (statement_READ.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(statement_READ.getDesignator().obj);
	}
	
	// designator statementi
	
	@Override
	public void visit(DesignatorStatement_Assign designatorStatement_Assign) {
		Code.store(designatorStatement_Assign.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_INC designatorStatement_INC) {
		if (designatorStatement_INC.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_INC.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatement_INC.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_DEC designatorStatement_DEC) {
		if (designatorStatement_DEC.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_DEC.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatement_DEC.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_ActPars designatorStatement_ActPars) {
		// da je sledeca linija stajala ispod Code.put(Code.call) ne bismo skocili na enter
		// instrukciju vec na 1B posle zato sto put pomera pc za 1
		int offset = designatorStatement_ActPars.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		// ako se funkcija koja nije void pozove kao f(); to ide u ovu smenu i return te
		// funkcije ce ostati na steku pa moramo da ga sklonimo
		if (designatorStatement_ActPars.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	// Designator
	
	@Override
	public void visit(DesArrayName desArrayName) {
		Code.load(desArrayName.obj);
	}
	
//	@Override
//	public void visit(DesName desName) {
//		if (!desName.obj.equals(Tab.noObj)) {
//			Code.load(desName.obj);
//		}
//	}
	
	@Override
	public void visit(Designator_Length designator_Length) {
		Code.load(designator_Length.getDesName().obj);
		Code.put(Code.arraylength);
	}
	
	// faktor opcije pa navise u stablu jer Expr mora da bude na steku
	// zanima nas samo designator unutar expr koji je sa desne strane assignop
	
	@Override
	public void visit(FactorOption_Method factorOption_Method) {
		// isto i ovde
		int offset = factorOption_Method.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(FactorOption_Des FactorOption_Des) {
		if (!(FactorOption_Des.getDesignator() instanceof Designator_Length)) {
			Code.load(FactorOption_Des.getDesignator().obj);
		}	
	}
	
	@Override
	public void visit(FactorOption_NUM factorOption_NUM) {
		Code.loadConst(factorOption_NUM.getN1());
	}
	
	@Override
	public void visit(FactorOption_CHAR factorOption_CHAR) {
		Code.loadConst(factorOption_CHAR.getC1());
	}
	
	@Override
	public void visit(FactorOption_BOOL factorOption_BOOL) {
		Code.loadConst(factorOption_BOOL.getB1());
	}
	
	@Override
	public void visit(FactorOption_NEW factorOption_NEW) {
		Code.put(Code.newarray);
		if (factorOption_NEW.getType().struct.equals(Tab.intType)) {
			Code.put(1);
		} else if (factorOption_NEW.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		}
	}
	
	// factor
	public void visit(Factor factor) {
		if (factor.getUnaryOp() instanceof UnaryOp_MINUS) {
			Code.put(Code.neg);
		}
	}
	
	// expr
	
	@Override
	public void visit(AddopTermList_Addop addopTermList_Addop) {
		if (addopTermList_Addop.getAddop() instanceof Addop_PLUS) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(MulopFactorList_Mulop mulopFactorList_Mulop) {
		if (mulopFactorList_Mulop.getMulop() instanceof Mulop_MUL) {
			Code.put(Code.mul);
		} else if (mulopFactorList_Mulop.getMulop() instanceof Mulop_DIV) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	// For petlja

	private Stack<Integer> forCondStartPc = new Stack<>();
	private Stack<Integer> forIncrStartPc = new Stack<>();
	private Stack<Integer> forExitJump = new Stack<>();
	private Stack<List<Integer>> breakJumps = new Stack<>();
	private Stack<Boolean> forHasCondition = new Stack<>();

	@Override
	public void visit(ForCondStart forCondStart) {
		forCondStartPc.push(Code.pc);
		breakJumps.push(new ArrayList<>());
	}

	@Override
	public void visit(ForCondition_exists forCondition_exists) {
		forHasCondition.push(true);
	}

	@Override
	public void visit(ForCondition_e forCondition_e) {
		forHasCondition.push(false);
	}

	@Override
	public void visit(ForIncrStart forIncrStart) {
		if (forHasCondition.pop()) {
			// Condition_OK already emitted the false-path jump into skipThen.
			// Steal it as our exit jump — we'll patch it to exit in Statement_FOR.
			forExitJump.push(skipThen.pop());
		} else {
			forExitJump.push(-1); // no condition, only break can exit
		}
		Code.putJump(0); // jump over incr to body (patched in ForBodyStart)
		skip.push(Code.pc - 2);
		forIncrStartPc.push(Code.pc); // incr starts right here
	}

	@Override
	public void visit(ForBodyStart forBodyStart) {
		Code.putJump(forCondStartPc.peek()); // after incr, jump back to cond
		Code.fixup(skip.pop()); // patch "jump over incr" → body starts here
	}

	@Override
	public void visit(Statement_FOR statement_FOR) {
		Code.putJump(forIncrStartPc.pop()); // after body, jump to incr
		int exitJump = forExitJump.pop();
		if (exitJump != -1) {
			Code.fixup(exitJump); // patch cond-false jump → here (exit)
		}
		forCondStartPc.pop();
		for (int bj : breakJumps.pop()) {
			Code.fixup(bj); // patch all break jumps → here (exit)
		}
	}

	@Override
	public void visit(Statement_BREAK statement_BREAK) {
		Code.putJump(0);
		breakJumps.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(Statement_CONTINUE statement_CONTINUE) {
		Code.putJump(forIncrStartPc.peek());
	}

	// Switch

	private Stack<Integer> switchCaseJne = new Stack<>();
	private Stack<Integer> switchFallthroughJump = new Stack<>(); // one per active case

	@Override
	public void visit(SwitchNonterm switchNonterm) {
		// na pocetku nema nikoga ko preskace na sledeci case ili propada
		switchCaseJne.push(-1);
		switchFallthroughJump.push(-1);    
		breakJumps.push(new ArrayList<>());
	}

	@Override
	public void visit(SwitchCaseCheck switchCaseCheck) {
		// obezbedjuje se doskok za prethodni case
		int prevJne = switchCaseJne.pop();
		if (prevJne != -1) Code.fixup(prevJne);

		Code.put(Code.dup);
		Code.loadConst(switchCaseCheck.getN1());
		Code.putFalseJump(Code.eq, 0); // skok na sledeci case(...) 
		switchCaseJne.push(Code.pc - 2);

		Code.put(Code.pop); // ako odgovara case, skidamo switch vrednost (expr) sa steka

		// ovde pocinje telo casea pa obezbedjujemo doskok na ovo mesto ako ima propadanja
		int prevFallthrough = switchFallthroughJump.pop();
		if (prevFallthrough != -1) Code.fixup(prevFallthrough);
	}

	@Override
	public void visit(SingleCase singleCase) {
		// iz bodija trenutnog case se obezbedjuje skok na sledeci bodi i tako se realizuje propadanje
		Code.putJump(0);
		switchFallthroughJump.push(Code.pc - 2);
	}

	@Override
	public void visit(Statement_SWITCH statement_SWITCH) {
		// kad dodjemo do kraja celog switcha fixujemo poslednji skok iz case (...) jer nema vise caseova
		int lastJne = switchCaseJne.pop();
		if (lastJne != -1) Code.fixup(lastJne);

		Code.put(Code.pop); // posto nismo nasli odgovarajuci case za expr na steku, treba da ga sklonimo

		// fixujemo poslednje propadanje na kraj celog switcha
		int lastFallthrough = switchFallthroughJump.pop();
		if (lastFallthrough != -1) Code.fixup(lastFallthrough);

		// fixujemo i sve breakove na kraj switcha
		for (int addr : breakJumps.pop()) Code.fixup(addr);
	}

	// Condition
	private Stack<Integer> skip = new Stack<Integer>(); // oni koji su netacni i skipuju trenutni OR
	private Stack<Integer> tacni = new Stack<Integer>(); // oni koji su tacni i skupuju ceo condition da bi presli na then
	private Stack<Integer> skipThen = new Stack<Integer>(); // zbog redosleda fixupovanja ugnezdjenih ifova
	private Stack<Integer> skipElse = new Stack<Integer>(); // isto i ovde
	
	private int findRelop(Relop relop) {
		if (relop instanceof Relop_EQUAL) {
			return Code.eq;
		} else if (relop instanceof Relop_NOTEQUAL) {
			return Code.ne;
		} else if (relop instanceof Relop_GREATER) {
			return Code.gt;	
		} else if (relop instanceof Relop_GREATEREQUAL) {
			return Code.ge;
		} else if (relop instanceof Relop_LESSER) {
			return Code.lt;
		} else if (relop instanceof Relop_LESSEREQUAL) {
			return Code.le;
		} else return 0;
	}
	
	@Override
	public void visit(CondFact_SimpleExpr condFact_SimpleExpr) {
		Code.loadConst(0); // ovo da bismo imali sa cime da poredimo kad nam je neki expr vec na steku
		Code.putFalseJump(Code.ne, 0); // netacna, iskace se kad su jednaki, tj oba su false
		skip.push(Code.pc - 2);
		// tacna	
	}
	
	@Override
	public void visit(CondFact_Relop condFact_Relop) {
		// sad imamo 2 expr na steku pa imamo sa cime da poredimo
		Code.putFalseJump(findRelop(condFact_Relop.getRelop()), 0); // netacna, iskace se kad nisu jednaki
		skip.push(Code.pc - 2);
		// tacna
	}
	
	@Override
	public void visit(CondTerm condTerm) {
		// ovde su dosli svi tokovi koji su ispunili sve ANDove unutar jednog CondFacta
		// dakle tacan je bar jedan OR sto znaci da moze da se skoci na then granu
		Code.putJump(0); // tacne skacu na then
		tacni.push(Code.pc - 2); // cuvamo njegovu adresu
		// netacne se vracaju da provere ANDove u sledecem ORu
		while (!skip.empty()) {
			Code.fixup(skip.pop());
		}
		// netacne nastavljaju
	}
	
	@Override
	public void visit(Condition_OK condition_OK) {
		// dolazi se do trenutka kada su svi ORovi provereni
		// i sada moramo da preusmerimo sve netacne na else granu
		Code.putJump(0); // netacne skacu na else
		skipThen.push(Code.pc - 2); // sacuvamo lokaciju gde posle upisujemo gde je else
		// THEN GRANA
		while (!tacni.empty()) { // upisujemo lokaciju then grane u sve koji su tacni
			Code.fixup(tacni.pop());
		}
		// tacne
	}
	
	@Override
	public void visit(ElseStm_nay elseStm_nay) {
		// tacne
		Code.fixup(skipThen.pop()); // vracamo netacne u isti tok gde su tacne jer nema else grane
		// tacne i netacne zajedno
	}
	
	@Override
	public void visit(Else Else) {
		// tacne
		Code.putJump(0); // sve koje su tacne skacu na kraj else
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop()); // dolaze sve koje su netacne
		// netacne
	}
	
	@Override
	public void visit(ElseStm_yay elseStm_yay) {
		// netacne
		Code.fixup(skipElse.pop()); // stavljamo lokaciju kraja else grane da bi tacni znali gde da skoce
		// netacne i tacne
	}

	// Ternary operator - isti princip kao if/else

	@Override
	public void visit(TernaryColon ternaryColon) {
		// tacna grana se zavrsila
		Code.putJump(0);          // tacni skacu iza false grane
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop()); // netacni dolaze ovde (pocetak false grane)
	}

	@Override
	public void visit(Expr_Ternary_Condition expr_Ternary_Condition) {
		// false grana se zavrsila
		Code.fixup(skipElse.pop()); // tacni dolaze ovde (iza false grane)
	}
	
	// while
	
	private Stack<Integer> whileBegin = new Stack<>();
	
	@Override
	public void visit(WhileCondBegin whileCondBegin) {
		whileBegin.push(Code.pc);
		breakJumps.push(new ArrayList<>());
	}
	
	@Override
	public void visit(Statement_WHILE statement_WHILE) {
		Code.putJump(whileBegin.peek());
		Code.fixup(skipThen.pop());
		
		int last = whileBegin.pop();
		for (int addr : breakJumps.pop()) Code.fixup(addr);
	}
	
	// do while
	
	private Stack<Integer> doWhileBegin = new Stack<>();
	
	@Override
	public void visit(DoNonterm doNonterm) {
		doWhileBegin.push(Code.pc);
		breakJumps.add(new ArrayList<>());
	}
	
	@Override
	public void visit(Statement_DO_WHILE Statement_DO_WHILE) {
		Code.putJump(doWhileBegin.peek());
		Code.fixup(skipThen.pop());
		
		int last = doWhileBegin.pop();
		for (int addr : breakJumps.pop()) Code.fixup(addr);
	}
	
	// foreach
	
	private Obj indeks = new Obj(Obj.Var, "indeks", Tab.intType);
	private Obj i = null;
	
	@Override
	public void visit(ForEachBegin forEachBegin) {
		Code.loadConst(0);
		Code.store(indeks);
	}
	
	@Override
	public void visit(Designator1 designator1) {
		i = designator1.obj;
		//Code.put(Code.pop);
	}
	
	private Stack<Integer> after = new Stack<>();
	private Stack<Integer> foreachAddr = new Stack<>();
	private Stack<Integer> backToForeach = new Stack<>();
	
	public void visit(Designator2 designator2) {
		// provera da li se vec desila prva iteracija, ako nije, sacuvaj lokaciju
		// ako jeste, obezbedi da moze da se doskoci ovde
		if (foreachAddr.contains(Code.pc)) {
			Code.fixup(backToForeach.pop());
		} else {
			foreachAddr.push(Code.pc);
		}
		
		// na stek stavljamo adresu niza koji se nalazi u des2 + instrukcija za duzinu niza
		// rezultat je duzina, stavljamo i indeks
		// vrsi se poredjenje i ako je duzina <= indeks iskace se iz petlje, tj na Code.le
		Code.load(designator2.obj);
		Code.put(Code.arraylength);
		Code.load(indeks);
		Code.putFalseJump(Code.gt, 0);
		after.push(Code.pc - 2);
		
		// na stek se stavljaju adresa niza i indeks da bismo instrukcijom aload 
		// dobili element na tom indeksu i sacuvali ga u promenljivu koja nam sluzi kao iterator
		Code.load(designator2.obj);
		Code.load(indeks);
		Code.put(Code.aload);
		Code.store(i);
		
		// indeks++
		Code.load(indeks);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(indeks);
	}
	
	@Override
	public void visit(ForeachNonterm foreachNonterm) {
		//Code.put(Code.pop);
	}
	
	@Override
	public void visit(Statement_FOREACH statement_FOREACH) {
		Code.putJump(foreachAddr.peek());
		backToForeach.push(Code.pc - 2);
		Code.fixup(after.pop());
	}
	
	// label
	private Map<String, Integer> labels = new HashMap<>();
	private Map<String, List<Integer>> gotos = new HashMap<>();
	
	@Override
	public void visit(Label label) {
		labels.put(label.getI1(), Code.pc);
		
		if (gotos.containsKey(label.getI1())) {
			while (!gotos.get(label.getI1()).isEmpty()) {
				Code.fixup(gotos.get(label.getI1()).remove(0));
			}
		}
	}
	
	// goto
	
	@Override
	public void visit(Statement_GOTO statement_GOTO) {
		if (labels.containsKey(statement_GOTO.getI1())) {
			Code.putJump(labels.get(statement_GOTO.getI1()));
		} else {
			Code.putJump(0);
			if (gotos.containsKey(statement_GOTO.getI1())) {
				gotos.get(statement_GOTO.getI1()).add(Code.pc - 2);
			} else {
				List<Integer> gotosList = new ArrayList<>();
				gotosList.add(Code.pc - 2);
				gotos.put(statement_GOTO.getI1(), gotosList);
			}
		}
	}
	
	// skip
	private int cnt = 0;
	private int skipAddr;
	private boolean skipping = false;
	
	@Override
	public void visit(Statement_SKIP statement_SKIP) {
		cnt = statement_SKIP.getN1();
		skipping = true;
		Code.putJump(0);
		skipAddr = Code.pc - 2;
	}
	
//	@Override
//	public void visit(Statement statement) {
//		if (skipping) {
//			cnt--;
//			if (cnt == 0) {
//				Code.fixup(skipAddr);
//				skipping = false;
//			}
//		}
//	}
}
