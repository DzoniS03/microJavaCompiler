package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ActParsCounter extends VisitorAdaptor {
	
	List<Struct> topActParsList = new ArrayList<>();
	Stack<List<Struct>> actParsLists = new Stack<>();
	
	@Override
	public void visit(ActParsStart actParsStart) {
		actParsLists.push(new ArrayList<>());
	}
	
	@Override
	public void visit(ActPar actPar) {
		actParsLists.peek().add(actPar.getExpr().struct);
	}
	
	@Override
	public void visit(ActParsList_Chain actParsList_Chain) {
		topActParsList = actParsLists.pop();
	}
	
	@Override
	public void visit(ActParsList_e ActParsList_e) {
		topActParsList = actParsLists.pop();
	}
	
}
