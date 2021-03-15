package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

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
		
		Date date = new Date();
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
		}
		
		for(int i = 0;i < states.size()-1; i++) {
			System.out.print("\n" + states.get(i)+ " -> "+ states.get(i+1));
		}
		System.out.println("\n");
		
		
		if(!trapstates.isEmpty()) {
		for(int i = 0; i < trapstates.size(); i++) {
			System.out.println(trapstates.get(i).toString() + " Csapda állapapot");
		}
		}
		
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
