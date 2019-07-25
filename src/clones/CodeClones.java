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
		
		//percorre arrays dos métodos
		//verifica isomorfias entre os metodos M1 e M2	
			isomorfs = findsIsoforms();
				if(isomorfs){
					
					//findClones();
				}
		
	}
	


	/*
	 * 	Aplica a busca em níveis para verificar se são isomorfos
	 */
	public boolean findsIsoforms() {
		
		boolean verifica = true;
		
		while (verifica) { //&& os níveis não foram todos percorridos
			
			//verifica em cada nível
				//se a quantidade de elementos no nível é igual para os dois métodos
					//continua;
				//se não
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
