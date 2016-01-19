package node;

public class Main {
	
	
	public static void main(String[] args){
		
		Node node1 = new Node("127.0.0.1", 2010, "127.0.0.1", 2010);
		Node node2 = new Node("127.0.0.1", 2010, "127.0.0.1", 2011);
		imprimirEstadoNodo(node1);
		imprimirEstadoNodo(node2);
		Node node3 = new Node("127.0.0.1", 2011, "127.0.0.1", 2012);
		Node node4 = new Node("127.0.0.1", 2011, "127.0.0.1", 2014);
		Node node5 = new Node("127.0.0.1", 2011, "127.0.0.1", 2015);
		
		System.out.println("-----------------ESTADO FINAL--------------");
		imprimirEstadoNodo(node1);
		imprimirEstadoNodo(node2);
		imprimirEstadoNodo(node3);
		imprimirEstadoNodo(node4);
		imprimirEstadoNodo(node5);
		
	}
	
	public static void imprimirEstadoNodo(Node n){
		System.out.println("Soy el nodo " + n.getMyPort() + " envio a " + n.getSender().getPortTo() + " recivo de " + n.getReceptor().getPortFrom());
	}

}
