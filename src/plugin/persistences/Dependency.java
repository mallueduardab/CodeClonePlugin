package plugin.persistences;

import java.util.ArrayList;
import org.eclipse.jdt.core.IType;

public class Dependency {
	
	private String newClassName;
	private IType classe;
	private ArrayList<String> dependencias;
	private ArrayList<MethodData> methods;

	
	public Dependency(IType classe, ArrayList<String> dependencias, ArrayList<MethodData> methods){
		this.classe = classe;
		this.dependencias = dependencias;
		this.methods = methods;
	}

	public String newClassName() {
		return newClassName;
	}
	
	public void setNewClassName(String name) {
		this.newClassName = name;
	}

	public IType getClasse() {
		return classe;
	}

	public ArrayList<String> getDependencias() {
		return dependencias;
	}
	
	public String getClasseName(){
		return classe.getElementName();
	}

	public ArrayList<MethodData> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<MethodData> methods) {
		this.methods = methods;
	}





}
