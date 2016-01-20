package program;

import node.Message;
import node.Node;
import node.User;

public class BatchThread implements Runnable, MessageReceiver{

	private Node node;
	private User user;
	
	public BatchThread(Node node, User user){
		this.node = node;
		this.user = user;
		this.node.setMessageReceiver(this);
	}
	
	public void run() {
		for (int i = 0 ; i < 20 ; i++){
			String body = "Hello from " + this.node.getId() + " " + i;
			Message m = new Message(this.node, this.user, body);
			this.node.sendMessage(m);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.node.logout();
		System.out.println(this.node.getId()+ " is now disconnected" );
		
	}

	@SuppressWarnings("deprecation")
	public void receiveMessage(Message m) {
		String time = m.getDate().getHours() + ":" + m.getDate().getMinutes();
		System.out.println( this.node.getId() + " received (" + time + ") sent: " + m.getBody()  );
	}
	
	

}
