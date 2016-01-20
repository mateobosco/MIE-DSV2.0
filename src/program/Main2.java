package program;

import node.Connection;
import node.Node;

public class Main2 {

	
	public static void main(String[] args){
		
		Node node3 = new Node(new Connection("127.0.0.1", 2010) ,new Connection("127.0.0.1", 2011));

		
		node3.getLamport().lock();
	}
}
