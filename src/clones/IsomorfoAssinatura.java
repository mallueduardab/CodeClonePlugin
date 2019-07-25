package clones;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import plugin.persistences.Dependency;
import plugin.persistences.GraphStructure;
import plugin.persistences.MethodData;

public class IsomorfoAssinatura {

	private String assinatura1;
	private String assinatura2;
	private ArrayList<GraphStructure> graphs;
	
	private HashMap<String, ArrayList<Dependency>> code;
	private ArrayList<MethodData> metodos = new ArrayList<MethodData>();

	public IsomorfoAssinatura(String assinatura1, String assinatura2, ArrayList<GraphStructure> graphs ) {
		this.assinatura1 = assinatura1;
		this.assinatura2 = assinatura2;
		this.graphs = graphs;
		
		//procuraAssinatura();
		//verificar();
		
	}

	private void verificar() {
		for (int i = 0; i < (graphs.size() - 1); i++) { // i compara com i +1
			if (!graphs.get(i).getSubnivel().isEmpty()) {
				if (!graphs.get(i + 1).getSubnivel().isEmpty()) {
//					System.out.println("metodos com subnivel: " + graphs.get(i).getNome() + "  " + graphs.get(i + 1).getNome());
					if (procuraAssinatura(graphs.get(i), graphs.get(i + 1))) {
							
							zerar(graphs.get(i));
							zerar(graphs.get(i+1));
							
						new IsomorfoAssinatura(graphs.get(i).getNome(), graphs.get(i+1).getNome());
						
					}
				}
			}
		}
	}

	private void procuraAssinatura(GraphStructure estruturaGrafo, GraphStructure estruturaGrafo2) {
		if ((estruturaGrafo.getFlag().equals(false)) && (estruturaGrafo2.getFlag().equals(false))
				&& ((estruturaGrafo != null) && (estruturaGrafo2 != null))) {
			if ((!estruturaGrafo.getSubnivel().isEmpty()) && (!estruturaGrafo2.getSubnivel().isEmpty())) {
				if (estruturaGrafo.getSubnivel().size() == estruturaGrafo2.getSubnivel().size()) {
					GraphStructure grafo1 = null;
					GraphStructure grafo2 = null;
					estruturaGrafo.setFlag(true);
					estruturaGrafo2.setFlag(true);
					for (int i = 0; i < estruturaGrafo.getSubnivel().size(); i++) {
						for (int k = 0; k < graphs.size(); k++) {
							if (estruturaGrafo.getSubnivel().get(i).equals(graphs.get(k).getNome())
									&& !estruturaGrafo.getSubnivel().isEmpty()) {
								grafo1 = graphs.get(k);
							}
						}
					}
					for (int i = 0; i < estruturaGrafo2.getSubnivel().size(); i++) {
						for (int k = 0; k < graphs.size(); k++) {
							if (estruturaGrafo2.getSubnivel().get(i).equals(graphs.get(k).getNome())
									&& !estruturaGrafo2.getSubnivel().isEmpty()) {
								grafo2 = graphs.get(k);
							}

						}
					}
					if (grafo1 == null || grafo2 == null) {
						return true;
					} else {
						procuraAssinatura(grafo1, grafo2);
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

}
