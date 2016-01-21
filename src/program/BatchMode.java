package program;

import node.Connection;
import node.Node;
import node.User;

public class BatchMode {
	
	public static void main(String[] args){
		
		User userJose = new User("Jose");
		User userPepe = new User("Pepe");
		User userAdro = new User("Adro");
		User userJony = new User("Jony");
		User userKocho = new User("Kocho");
		
		Node node1 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2010));
		Node node2 = new Node(new Connection("127.0.0.1", 2010), new Connection( "127.0.0.1", 2011));
		Node node3 = new Node(new Connection("127.0.0.1", 2011), new Connection( "127.0.0.1", 2012));
		Node node4 = new Node(new Connection("127.0.0.1", 2012), new Connection( "127.0.0.1", 2013));
		Node node5 = new Node(new Connection("127.0.0.1", 2013), new Connection( "127.0.0.1", 2014));
		
		imprimirEstadoNodo(node1);
		imprimirEstadoNodo(node2);
		imprimirEstadoNodo(node3);
		imprimirEstadoNodo(node4);
		imprimirEstadoNodo(node5);
		
		Thread clientThread1 = new Thread(new BatchThread(node1, userJose));
		clientThread1.start();
		
		Thread clientThread2 = new Thread(new BatchThread(node2, userPepe));
		clientThread2.start();
		
		Thread clientThread3 = new Thread(new BatchThread(node3, userAdro));
		clientThread3.start();
		
		Thread clientThread4 = new Thread(new BatchThread(node4, userJony));
		clientThread4.start();
		
		Thread clientThread5 = new Thread(new BatchThread(node5, userKocho));
		clientThread5.start();
		
	}
	
	public static void imprimirEstadoNodo(Node n){
		System.out.println("Soy el nodo " + n.getId() + " envio a " + n.getSender().getConnectionTo().getId()+ " recivo de " + n.getReceptor().getConnectionFrom().getId());
	}

}
