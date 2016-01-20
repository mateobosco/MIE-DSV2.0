package node;

public class Main2 {

	
	public static void main(String[] args){
		
		Node node3 = new Node("127.0.0.1", 2010, "127.0.0.1", 2011);

		
		node3.getLamport().lock();
	}
}
