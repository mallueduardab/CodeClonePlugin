package plugin.persistences;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.UUID;

public class MethodData {

	private ArrayList<String> parameters = new ArrayList<String>();
	private String methodName;
	private MethodDeclaration methodBody;
	private ArrayList<String> methodsInvo = new ArrayList<String>();
	private String retorno;
	private int modificador;
	private String id;

	public MethodData(ArrayList<String> parameters, String methodName, MethodDeclaration methodBody,
			ArrayList<String> methodsInvo, String retorno, int modificador) {
		this.parameters = parameters;
		this.methodName = methodName;
		this.methodBody = methodBody;
		this.methodsInvo = methodsInvo;
		this.retorno = retorno;
		this.modificador = modificador;
		this.id = UUID.randomUUID().toString();
	}


	public ArrayList<String> getParameters() {
		return parameters;
	}

	public String getMethodName() {
		return methodName;
	}

	public MethodDeclaration getMethodBody() {
		return methodBody;
	}

	public ArrayList<String> getMethodsInvo() {
		return methodsInvo;
	}


	public String getAssinatura() {
		return modificador + "." + retorno + "." + methodBody.getName();
	}

	public void setEStrelas(int valor) {

	}

	public String getRetorno() {
		return retorno;
	}

	public int getModificador() {
		return modificador;
	}


	public String getId() {
		return id;
	}

}
