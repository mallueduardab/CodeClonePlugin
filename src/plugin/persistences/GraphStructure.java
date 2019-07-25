package plugin.persistences;

import java.util.ArrayList;

public class GraphStructure {

	int nivel;
	String nome;
	ArrayList<String> subnivel;
	Boolean flag;
	
	public GraphStructure(int nivel, String nome, ArrayList<String> subnivel) {
		this.nivel = nivel;
		this.nome = nome;
		this.subnivel = subnivel;
		this.flag = false;
	}
	
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<String> getSubnivel() {
		return subnivel;
	}
	public void setSubnivel(ArrayList<String> subnivel) {
		this.subnivel = subnivel;
	}

	
}
