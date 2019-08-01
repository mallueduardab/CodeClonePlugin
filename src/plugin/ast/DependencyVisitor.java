package plugin.ast;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import plugin.persistences.Dependency;
import plugin.persistences.MethodData;

import java.util.ArrayList;

public class DependencyVisitor extends ASTVisitor {

	private CompilationUnit fullClass;
	private IType clazz;
	private Type parent;
	
	private ArrayList<String> dependencias;
	ArrayList<Dependency> dp;
	private ArrayList<MethodData> methods;
	private ArrayList<String> methodsInvo;
	private String variavel = "";
	private ArrayList<String> acessoMetodo;
	private ArrayList<String> acessoAtributo;

	public DependencyVisitor(ICompilationUnit unit) throws JavaModelException {
		dp = new ArrayList<Dependency>();
		acessoAtributo = new ArrayList<>();
		@SuppressWarnings("deprecation")
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setCompilerOptions(JavaCore.getOptions());
		parser.setProject(unit.getJavaProject());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);

		this.fullClass = (CompilationUnit) parser.createAST(null);
		this.fullClass.accept(this);
	}

	@Override
	public boolean visit(TypeDeclaration node) {

//		System.out.println("\n\n Identificador aí ------------------");
//		System.out.println("GET NAME \n" + node.getName());
//		System.out.println("GET SUPERCLASS \n" + node.getSuperclassType());

		dependencias = new ArrayList<String>();
		clazz = (IType) node.resolveBinding().getJavaElement();
		methods = new ArrayList<MethodData>();
		parent = node.getSuperclassType();

		Dependency dependency = new Dependency(clazz, dependencias, methods);

		dp.add(dependency);
		return true;
	}

	// Casos em que é adicionado acesso ao atributo: 
	// 		1 - Na declaração de uma variável e essa variável acessa um método
	//      2 - Quando uma variável é passada por parâmetro
	//      
	@Override
	public boolean visit(FieldDeclaration node) {

		if ((!node.getType().resolveBinding().getKey().toString().contains("java"))
				&& (!node.getType().isPrimitiveType()) && (!node.getType().isArrayType())) {
			String arrayLine[] = new String[2];
			arrayLine = node.fragments().get(0).toString().split("=");
			String var = arrayLine[0];
			acessoAtributo.add(var + "." + node.getType().toString());
		}
		if (!dependencias.contains(node.getType().resolveBinding().getName()) && !node.getType().isPrimitiveType()) {
			dependencias.add(node.getType().resolveBinding().getName());
		}
		
		return true;
	}

	@Override
	public boolean visit(MethodInvocation node) {

		String arrayLine[] = new String[2];
		arrayLine = node.toString().split("\\.");
		variavel = arrayLine[0];
		// System.out.println("var "+ variavel);
		if (variavel.contains("(")) {
			if (!methodsInvo.contains(clazz.getElementName() + "." + node.getName().toString())) {
				methodsInvo.add(clazz.getElementName() + "." + node.getName().toString());
			}
		}
		
//		ListOfGraphs variavel = new ListOfGraphs();
//		
//		variavel.visit();
//		ListOfGraphs.oi();
//		this.acessoAtributo();
//		super.visit(node);
//		visit();

		for (int i = 0; i < acessoAtributo.size(); i++) {
			String array[] = new String[3];
			array = acessoAtributo.get(i).toString().split("\\.");
			// System.out.println("acesso "+acessoMetodo);
			if (variavel.equals(array[0])) {
				String nome = array[1] + "." + node.getName().toString();
				if (!methodsInvo.contains(nome)) {
					methodsInvo.add(nome);
				}
			}
			
//			if (variavel.equals("super")) {
				System.out.println("\n PASSOU AQUI 2: ------------------");
				System.out.println(node);
//				System.out.println(node.getName());
//				System.out.println(node.getExpression()); // Se pá esse é a 'variável' kkk
				
//				if (variavel.equals("super")) System.out.println("VARIAVEL: " + variavel);
//				System.out.println(parent);
//				System.out.println(parent + "." + node.getName().toString());
//			}

		}

		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		acessoMetodo = new ArrayList<>();
		methodsInvo = new ArrayList<String>();

		ArrayList<String> parameters = new ArrayList<String>();

		for (Object o : node.parameters()) {

			if (o instanceof SingleVariableDeclaration) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
				if (!parameters.contains(svd.getType().resolveBinding().getName())
				 && !svd.getType().isPrimitiveType()) {
					acessoMetodo.add(svd.getName() + "." + svd.getType().toString());
					acessoAtributo.add(svd.getName() + "." + svd.getType().toString());
				}
				parameters.add(svd.getType().resolveBinding().getName().toString());
				if (!dependencias.contains(svd.getType().resolveBinding().getName())
						&& !svd.getType().isPrimitiveType()) {
					dependencias.add(svd.getType().resolveBinding().getName());
				}
			}
		}
//		String nome = clazz.getElementName() + "." + node.getName().toString();
//		String[] arr = nome.split("\\.");
//		System.out.println(" Nome completo:" + nome + " Nome picado: " + arr[1]);
		methods.add(new MethodData(parameters, clazz.getElementName() + "." + node.getName().toString(), node,
				methodsInvo, node.resolveBinding().getReturnType().toString(), node.getModifiers()));
		return true;
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		if (!dependencias.contains(node.getType().resolveBinding().getName()) && !node.getType().isPrimitiveType()) {
			dependencias.add(node.getType().resolveBinding().getName());
		}
		return true;
	}

	public IType getClazz() {
		return clazz;
	}

	public ArrayList<Dependency> getDependency() {
		return dp;
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		if (!node.getType().isPrimitiveType()) {
			String arrayLine[] = new String[2];
			arrayLine = node.fragments().get(0).toString().split("=");
			String var = arrayLine[0];
			acessoMetodo.add(var + "." + node.getType().toString());
		}
		return true;
	}

	@Override
	public boolean visit(MethodRef node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

}
