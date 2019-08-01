package plugin.persistences;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;

public class GraphStructure {

	int nivel;
	String nome;
	ArrayList<String> subnivel;
	Boolean flag;
	ArrayList<String> parameters;
	private MethodDeclaration methodBody;
	
	public GraphStructure(int nivel, String nome, ArrayList<String> subnivel, ArrayList<String> parametros, MethodDeclaration methodBody) {
		this.nivel = nivel;
		this.nome = nome;
		this.subnivel = subnivel;
		this.flag = false;
		this.parameters = parametros;
		this.methodBody = methodBody;
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

	public ArrayList<String> getParametros() {
		return parameters;
	}

	public MethodDeclaration getMethodBody() {
		return methodBody;
	}
}
