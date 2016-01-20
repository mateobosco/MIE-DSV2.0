package node;

public class Main {
	
	
	public static void main(String[] args){
		
		Node node1 = new Node("127.0.0.1", 2010, "127.0.0.1", 2010);
//		Node node2 = new Node("127.0.0.1", 2010, "127.0.0.1", 2011);
		
//		Node node4 = new Node("127.0.0.1", 2011, "127.0.0.1", 2014);
//		Node node5 = new Node("127.0.0.1", 2011, "127.0.0.1", 2015);

		Message m = new Message("127.0.0.1:2010", "pepe", "ME CONECTE MANGA DE PUTISSSSSS");
		
		node1.sendMessage(m);
		imprimirListaDeMensajes(node1);
	}
	
	public static void imprimirEstadoNodo(Node n){
		System.out.println("Soy el nodo " + n.getMyPort() + " envio a " + n.getSender().getPortTo() + " recivo de " + n.getReceptor().getPortFrom());
	}
	
	public static void imprimirListaDeMensajes(Node n){
		System.out.println("----MENSAJES DE NODO " + n.getMyPort() + " -------------");
		for (int i = 0 ; i < n.getMessages().size(); i++){
			Message m = n.getMessages().get(i);
			System.out.println(m.getFrom() + " : " + m.getBody());
		}
	}

}
