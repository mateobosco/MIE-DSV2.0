package node;

public class Main {
	
	
	public static void main(String[] args){
		
		Node node1 = new Node("127.0.0.1", 2010, "127.0.0.1", 2010);
//		Node node2 = new Node("127.0.0.1", 2010, "127.0.0.1", 2011);
		
//		Node node4 = new Node("127.0.0.1", 2011, "127.0.0.1", 2014);
//		Node node5 = new Node("127.0.0.1", 2011, "127.0.0.1", 2015);

		
		node1.sendMessage("me conecte manga de caretaas");
		node1.getLamport().lock();
		
		try {
		    Thread.sleep(8000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		node1.getLamport().unlock();
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
