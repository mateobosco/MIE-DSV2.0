package node;

public class Main {
	
	
	public static void main(String[] args){
		
		Node node1 = new Node("127.0.0.1", 2010, "127.0.0.1", 2010);
		Node node2 = new Node("127.0.0.1", 2010, "127.0.0.1", 2011);
		Node node3 = new Node("127.0.0.1", 2011, "127.0.0.1", 2012);
		Node node4 = new Node("127.0.0.1", 2011, "127.0.0.1", 2014);
		Node node5 = new Node("127.0.0.1", 2011, "127.0.0.1", 2015);
		
		node1.sendMessage("me conecte manga de caretaas");
		imprimirListaDeMensajes(node1);
		imprimirListaDeMensajes(node2);
		imprimirListaDeMensajes(node3);
		imprimirListaDeMensajes(node4);
		imprimirListaDeMensajes(node5);
	}
	
	public static void imprimirEstadoNodo(Node n){
		System.out.println("Soy el nodo " + n.getMyPort() + " envio a " + n.getSender().getPortTo() + " recivo de " + n.getReceptor().getPortFrom());
	}
	
	public static void imprimirListaDeMensajes(Node n){
		System.out.println("----MENSAJES DE NODO " + n.getMyPort() + " -------------");
		for (int i = 0 ; i < n.getMessages().size(); i++){
			String m = n.getMessages().get(i);
			System.out.println(i + " : " + m);
		}
	}

}
