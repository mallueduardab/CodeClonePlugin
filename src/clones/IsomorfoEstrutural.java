package clones;

import plugin.persistences.GraphStructure;

import java.util.ArrayList;

public class IsomorfoEstrutural {

	private ArrayList<GraphStructure> graphs;

	public IsomorfoEstrutural(ArrayList<GraphStructure> graphs) {
		this.graphs = graphs;
		verificar();
	}

	private void verificar() {

		// - Verifica estrutura, quando cada graph for igual,
		// - Verificar assinaturas, jogaremos em uma lista de pares
		// - Verificar clones (Tipo 1 - Idêntico)

		for (int i = 0; i < (graphs.size() - 1); i++) { // i compara com i + 1

			// falta tratar se i tem n=0 e i+1 tb
			if (!graphs.get(i).getSubnivel().isEmpty()) {
				if (!graphs.get(i + 1).getSubnivel().isEmpty()) {

					// Graph é a lista de adjacências, ou seja, o mesmo nó pode aparecer diversas
					// vezes. Logo, precisamos compará-los pra evitar que verifiquemos o mesmo nó
					if (!graphs.get(i).getNome().equals(graphs.get(i + 1).getNome())) {
						if (verificaSubNivel(graphs.get(i), graphs.get(i + 1))) {

							zerar(graphs.get(i));
							zerar(graphs.get(i + 1));

							// Verifica se os métodos possuem assinaturas com chances de duplicação
							if (verificarItensAssinatura(graphs.get(i), graphs.get(i + 1))) {

//								System.out.println("Método 1: " + graphs.get(i).getNome());
//								System.out.println("Método 2: " + graphs.get(i + 1).getNome());
//								System.out.println("");
								
								verificarCloneTipo2(graphs.get(i), graphs.get(i + 1));
								
								// Verifica se os métodos são possíveis clones do tipo 1
								if (verificarCloneTipo1(graphs.get(i), graphs.get(i + 1))) {
									// TODO
								}

							}

//						if (verificarAssinatura(graphs.get(i), graphs.get(i + 1))) {
//							
//						}
							/*
							 * Aqui vão ser procuradas coincidências nas assinaturas dos métodos
							 * 
							 * 
							 */
							// new IsomorfoAssinatura(graphs.get(i).getNome(), graphs.get(i+1).getNome(),
							// graphs);
						}
					}
				}
			}
		}
	}

	/**
	 * Valida se duas estruturas de grafo são considerados possíveis de duplicação
	 * tendo a assinatura como base.
	 * 
	 * Se o número de parâmetros forem vazios e o nome for diferente, deve-se retornar falso.
	 * 
	 * Se o número de parâmetros e seus tipos coincidirem OU o nome do método for o mesmo,
	 * a estrutura é considerada possível de duplicação.
	 * 
	 * @param estruturaGrafo Estrutura de grafo #1
	 * @param estruturaGrafo2 Estrutura de grafo #2, que será testada com a #1
	 * @return Verdadeiro ou false considerando a regra
	 */
	private Boolean verificarItensAssinatura(GraphStructure estruturaGrafo, GraphStructure estruturaGrafo2) {
		if (estruturaGrafo == null || estruturaGrafo2 == null)
			return false;

		// Extrai o nome do método
		String[] nomeEstruturaGrafo1 = estruturaGrafo.getNome().split("\\.");
		String[] nomeEstruturaGrafo2 = estruturaGrafo2.getNome().split("\\.");

		// Se os parâmetros forem vazios e o nome do método for diferente
		// deve retornar falso
		if ((estruturaGrafo.getParametros().isEmpty() && estruturaGrafo.getParametros().isEmpty())
				&& !nomeEstruturaGrafo1[1].equals(nomeEstruturaGrafo2[1])) {
			return false;
		}

		// Se o número de parâmetros e o tipo deles forem iguais OU o nome do método
		// for igual deve retornar verdadeiro
		return (estruturaGrafo.getParametros().size() == estruturaGrafo2.getParametros().size()
				&& verificarTipoParametro(estruturaGrafo.getParametros(), estruturaGrafo2.getParametros()))
				|| nomeEstruturaGrafo1[1].equals(nomeEstruturaGrafo2[1]);

	}

	/**
	 * Verifica se duas estruturas de grafo são considerados possíveis de duplicação
	 * tendo como base a regra de clone do tipo 1: Dois métodos com estrutura idêntica,
	 * possuindo variações de espaço, guias, layouts e comentários.
	 * 
	 * @param estruturaGrafo Estrutura de grafo #1
	 * @param estruturaGrafo2 Estrutura de grafo #2, que será testada com a #1
	 * @return Verdadeiro ou false considerando a regra
	 */
	private Boolean verificarCloneTipo1(GraphStructure estruturaGrafo, GraphStructure estruturaGrafo2) {
		return estruturaGrafo.getMethodBody().equals(estruturaGrafo2.getMethodBody());
	}
	
	private Boolean verificarCloneTipo2(GraphStructure estruturaGrafo, GraphStructure estruturaGrafo2) {
		System.out.println("---------- CLONE TIPO 2 ------------");
		System.out.println("BODY:");
		System.out.println(estruturaGrafo.getMethodBody().getBody());
		System.out.println("AST:");
		System.out.println(estruturaGrafo.getMethodBody().getBody().getAST());		
		
		return true;
	}

	/**
	 * Verifica se uma lista de parâmetros são considerados iguais.
	 * 
	 * @param params1
	 * @param params2
	 * @return
	 */
	private Boolean verificarTipoParametro(ArrayList<String> params1, ArrayList<String> params2) {
		if (params1.isEmpty() && params2.isEmpty())
			return true;
		
		// Clonando os parâmetros para evitar manipular o original
		ArrayList<String> aux = (ArrayList<String>) params2.clone();

		// Remove da lista auxiliar quando o parâmetro 1 for igual ao parâmetro 2
		for (int i = 0; i < params1.size(); i++) {
			for (int j = 0; j < params2.size(); j++) {
				if (params1.get(i).equals(params2.get(j))) {
					aux.remove(params2.get(j));
				}
			}
		}

		// Tem que retornar `true` se todos os itens da
		// lista `params2` forem removidos
		return aux.isEmpty();
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
		if (((estruturaGrafo != null) && (estruturaGrafo2 != null)) && (estruturaGrafo.getFlag().equals(false))
				&& (estruturaGrafo2.getFlag().equals(false))) {
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
