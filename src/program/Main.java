package program;

import node.Connection;
import node.Message;
import node.Node;

public class Main {
	
	
	public static void main(String[] args){
		
		Node node1 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2010));
		Node node2 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2011));
		Node node3 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2012));
		Node node4 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2013));
		
//		Node node4 = new Node("127.0.0.1", 2011, "127.0.0.1", 2014);
//		Node node5 = new Node("127.0.0.1", 2011, "127.0.0.1", 2015);

		Message m = new Message("127.0.0.1:2010", "pepe", "ME CONECTE MANGA DE PUTISSSSSS");
		
//		node1.sendMessage(m);
//		imprimirListaDeMensajes(node1);
		node1.getNetworkStatus().print();
		node2.getNetworkStatus().print();
		node3.getNetworkStatus().print();
		node4.getNetworkStatus().print();
		
		System.out.println("ME DUERMO UN RATO");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		node1.sendMessage(m);
		
		imprimirEstadoNodo(node1);
		imprimirEstadoNodo(node2);
		imprimirEstadoNodo(node3);
		imprimirEstadoNodo(node4);
		
		node1.getNetworkStatus().print();
		node2.getNetworkStatus().print();
		node3.getNetworkStatus().print();
		node4.getNetworkStatus().print();
	}
	
	public static void imprimirEstadoNodo(Node n){
		System.out.println("Soy el nodo " + n.getId() + " envio a " + n.getSender().getConnectionTo().getId()+ " recivo de " + n.getReceptor().getConnectionFrom().getId());
	}
	
	public static void imprimirListaDeMensajes(Node n){
		System.out.println("----MENSAJES DE NODO " + n.getId() + " -------------");
		for (int i = 0 ; i < n.getMessages().size(); i++){
			Message m = n.getMessages().get(i);
			System.out.println(m.getFrom() + " : " + m.getBody());
		}
	}

}
