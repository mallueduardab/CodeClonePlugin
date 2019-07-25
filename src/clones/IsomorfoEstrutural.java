package clones;

import java.util.ArrayList;

import plugin.persistences.GraphStructure;

public class IsomorfoEstrutural {

	private ArrayList<GraphStructure> graphs;
	

	public IsomorfoEstrutural(ArrayList<GraphStructure> graphs) {
		this.graphs = graphs;
		verificar();
	}

	private void verificar() {
		for (int i = 0; i < (graphs.size() - 1); i++) { // i compara com i +1
			 //falta tratar se i tem n=0 e i+1 tb
			if (!graphs.get(i).getSubnivel().isEmpty()) {
				if (!graphs.get(i + 1).getSubnivel().isEmpty()) {
//					System.out.println("metodos com subnivel: " + graphs.get(i).getNome() + "  " + graphs.get(i + 1).getNome());
					if (verificaSubNivel(graphs.get(i), graphs.get(i + 1))) {
							zerar(graphs.get(i));
							zerar(graphs.get(i+1));
						//new IsomorfoAssinatura(graphs.get(i).getNome(), graphs.get(i+1).getNome(), graphs.);
					}
				}
			}
		}
	}

	private void zerar(GraphStructure estruturaGrafo) {
		if (estruturaGrafo != null) {
			if (!estruturaGrafo.getSubnivel().isEmpty()) {
				estruturaGrafo.setFlag(false);
				GraphStructure grafo1 = null;
				for (int i = 0; i < estruturaGrafo.getSubnivel().size(); i++) {
					for (int k = i; k < graphs.size(); k++) {
						if (estruturaGrafo.getSubnivel().get(i).equals(graphs.get(k).getNome())) {
							grafo1 = graphs.get(k);
						}
					}
				}
				if (grafo1 != null) {
					if (grafo1.getFlag()) {
						zerar(grafo1);
					}
				}

			}
		}

	}

	private Boolean verificaSubNivel(GraphStructure estruturaGrafo, GraphStructure estruturaGrafo2) {
		if ( ((estruturaGrafo != null) && (estruturaGrafo2 != null))
				&& (estruturaGrafo.getFlag().equals(false)) && (estruturaGrafo2.getFlag().equals(false))) {
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
						verificaSubNivel(grafo1, grafo2);
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}



}
