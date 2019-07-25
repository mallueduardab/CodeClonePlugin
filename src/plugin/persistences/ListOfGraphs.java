package plugin.persistences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class ListOfGraphs {

	private HashMap<String, ArrayList<Dependency>> code;
	private ArrayList<GraphStructure> graphs = new ArrayList<GraphStructure>(); //
	private ArrayList<MethodData> metodos = new ArrayList<MethodData>(); // lista de dados dos métodos
	
	int nivel = 0;

	public ListOfGraphs (HashMap<String, ArrayList<Dependency>> codeFragments) {
		this.code = codeFragments;
		createMethod();
		createGraph();
		print();
	}

	private void print() {
		for (int i = 0; i < graphs.size(); i++) {
			
			System.out.println("\n\n" + graphs.get(i).getNivel() + "   "+ 
					graphs.get(i).getNome() + "  "+ 
					Arrays.toString(graphs.get(i).getSubnivel().toArray())+" -> ");
		}
		
	}

	private void createMethod() { // cria cópia de todos os métodos do sistema e suas caracteristicas para uma nova lista 'metodos'
		for (Entry<String, ArrayList<Dependency>> entry : code.entrySet()) {
			for(int i = 0; i < entry.getValue().size();i++) { // classe
				for(int j = 0; j < entry.getValue().get(i).getMethods().size(); j++){ // metodo
					metodos.add(new MethodData(entry.getValue().get(i).getMethods().get(j).getParameters(), 
							entry.getValue().get(i).getMethods().get(j).getMethodName(), 
							entry.getValue().get(i).getMethods().get(j).getMethodBody(), 
							entry.getValue().get(i).getMethods().get(j).getMethodsInvo(),
							entry.getValue().get(i).getMethods().get(j).getRetorno(),
							entry.getValue().get(i).getMethods().get(j).getModificador()));
				}
			}
		}	
	}
	
	/* procura um método da lista, que é adjacência de algum outro método no grafo (nivel = 1),
	* se a lista de adjacências dele não é vazia e, se não for,
	* adiciona um novo nó no grafo e aumenta seu nivel
	*/
	private void addAdjacencia(String invocadoX) { // adiciona uma lista de adjacencia a grafo
		for(int k = 0; k < metodos.size();k++) {
			if(metodos.get(k).getMethodName().equals(invocadoX)) {
				if(!metodos.get(k).getMethodsInvo().isEmpty()) { // afunda
					graphs.add(new GraphStructure(nivel, 
							metodos.get(k).getMethodName(),
							metodos.get(k).getMethodsInvo()));
					//aparentemente isso está errado
					nivel++;
				}
			}
		}
	}

	private void createGraph() { //cria lista de grafos 
		for(int i = 0; i < metodos.size();i++) {
			nivel = 0;
			graphs.add(new GraphStructure(nivel, 
					metodos.get(i).getMethodName(),
					metodos.get(i).getMethodsInvo()));
			nivel++;
			if(!metodos.get(i).getMethodsInvo().isEmpty()) { //se ele chama outros métodos
				for(int j = 0; j < metodos.get(i).getMethodsInvo().size();j++) { // percorre essa lista de métodos procurando a
					addAdjacencia(metodos.get(i).getMethodsInvo().get(j)); // manda o nó para procurar adjacencias
				}
			}
		}
	}
	
	public ArrayList<GraphStructure> getGraph() {
		return graphs;
	}
}
