package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.ReactionTrigger;
import org.yakindu.sct.model.stext.stext.SimpleScope;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		List<String> states = new ArrayList<String>();
		List<String> trapstates = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		
		
				while (iterator.hasNext()) {
					
				EObject content = iterator.next();
				
				
				//if(content instanceof State  content instanceof EventDefinition ||  content instanceof VariableDefinition) {
					if(content instanceof State) {
						State state = (State) content;
							System.out.println(state.getName());
						}
					else if(content instanceof VariableDefinition) {
						VariableDefinition var = (VariableDefinition) content;
						System.out.println(var.getName());
						variables.add(var.getName());
						
					}
					else if(content instanceof EventDefinition) {
						EventDefinition var = (EventDefinition) content;
						System.out.println(var.getName());
					}
				}
				RunStateChartKiir();
		
		/*Date date = new Date();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			if(content instanceof State) {
					
				State state = (State) content;
				if(state.getName() == null) 
				{System.out.println(" recommended name for nameless state: NamedState" + date.getTime());
				
				state.setName("State +" + date.getTime() );
				}
				
				EList<Transition> list = new BasicEList<Transition>();
				list = state.getOutgoingTransitions();
				
				if(list.isEmpty()) {trapstates.add(state.getName());}
				
				states.add(state.getName());
			}
		}*/
		
	/*	for(int i = 0;i < states.size()-1; i++) {
			System.out.print("\n" + states.get(i)+ " -> "+ states.get(i+1));
		}
		System.out.println("\n");*/
		
		
	/*	if(!trapstates.isEmpty()) {
		for(int i = 0; i < trapstates.size(); i++) {
			System.out.println(trapstates.get(i).toString() + " Csapda állapapot");
		}
		}*/
		
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	
	public static void kiir(List<String> list) {
		
		System.out.println("public static void print(IExampleStatemachine s) {");
		for(int i = 0 ; i < list.size(); i++) {
			System.out.println("System.out.println(\"" + list.get(i) + " = \" + s.getSCInterface().get"+list.get(i).substring(0, 1).toUpperCase()+list.get(i).substring(1)+"());");
		}
		System.out.println("}");
			//System.out.println("W = " + s.getSCInterface().get<Első változó neve>());
			
	}
	
	
	public static void RunStateChartKiir() {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		Scanner sc = new Scanner(System.in);
		String model = sc.nextLine();
		
		// Loading model
		//EObject root = manager.loadModel("model_input/example.sct");
		EObject root = manager.loadModel(model);
		
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		List<String> states = new ArrayList<String>();
		List<String> events = new ArrayList<String>();
		List<String> variables = new ArrayList<String>();
		
		
				while (iterator.hasNext()) {
					
				EObject content = iterator.next();
				
				
				
				/*	if(content instanceof State) {
						State state = (State) content;
							System.out.println(state.getName());
						}*/
					
					if(content instanceof VariableDefinition) {
						VariableDefinition var = (VariableDefinition) content;
						variables.add(var.getName());
						
					}
					else if(content instanceof EventDefinition) {
						EventDefinition var = (EventDefinition) content;
						events.add(var.getName());
					}
				}
		
				System.out.println("public static void main(String[] args) throws IOException {");
				System.out.println("Scanner sc = new Scanner(System.in);");
				System.out.println("ExampleStatemachine s = new ExampleStatemachine();");
				System.out.println("s.setTimer(new TimerService());");
				System.out.println("RuntimeService.getInstance().registerStatemachine(s, 200);");
				System.out.println("s.init();s.enter();s.runCycle();print(s);");
				System.out.println("while(true) {String inp = sc.nextLine();");
				System.out.println("switch(inp) {");
				//case "start": s.raiseStart();break;
				for(int i = 0; i < variables.size(); i++) {
					System.out.println("  case \""+variables.get(i)+"\": s.raise"+variables.get(i).substring(0,1).toUpperCase()+variables.get(i).substring(1)+"();break;");
				}
				System.out.println("case \"exit\": print(s);System.exit(0);");
				System.out.println("default: print(s);}s.runCycle();print(s);}}");
				kiir(variables);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
