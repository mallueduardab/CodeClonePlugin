package clones;

import java.util.ArrayList;
import java.util.HashMap;

import plugin.persistences.Dependency;
import plugin.persistences.MethodData;

public class CodeClones {

	public HashMap<String, ArrayList<Dependency>> code;
	
	private CodeClones(HashMap<String, ArrayList<Dependency>> code) {
		this.code = code;
	}
	

	/*
	 * 	
	 * 
	 */
	
	public void identifyPossibleClones() {
			
		
		boolean isomorfs;
		
		//percorre arrays dos m�todos
		//verifica isomorfias entre os metodos M1 e M2	
			isomorfs = findsIsoforms();
				if(isomorfs){
					
					//findClones();
				}
		
	}
	


	/*
	 * 	Aplica a busca em n�veis para verificar se s�o isomorfos
	 */
	public boolean findsIsoforms() {
		
		boolean verifica = true;
		
		while (verifica) { //&& os n�veis n�o foram todos percorridos
			
			//verifica em cada n�vel
				//se a quantidade de elementos no n�vel � igual para os dois m�todos
					//continua;
				//se n�o
					//verifica = false;
			
		}
		
		return verifica;
	}
	
	
	public void findClones() {
		
	}
	
	public ArrayList<MethodData> geClones() {
		return null;
	}
}
