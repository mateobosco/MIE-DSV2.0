package node;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import receptor.IReceptor;
import receptor.Receptor;
import sender.Sender;

public class Node {

	private Receptor receptor;
	private Sender sender;
	private Lamport lamport;
	
	private Connection myConnection;
	
	private ArrayList<Message> messageList;
	
	public Node(Connection connectionTo, Connection myConnection){
		this.myConnection = myConnection;
		
		this.receptor = this.createReceptor(myConnection.getPort());
		this.sender = new Sender(this, connectionTo);
		this.lamport = new Lamport(this);
		this.messageList = new ArrayList<Message>();
		
		this.sender.connect();
				
		this.sender.login(connectionTo, myConnection);	
	}
	
	public int sendMessage(Message message){
		this.lamport.lock();
		this.sender.sendMessage(message);
		this.lamport.unlock();
		return 0;
	}
	
	public int receiveMessage(Message message, Connection connectionSender){
		this.messageList.add(message);
		if (!myConnection.equals(connectionSender)){
			this.sender.retransmitMessage(message, connectionSender);
		}
		return 0;
	}
	
	public int receiveLogin(Connection connectionTo, Connection connectionFrom){
			
		Connection senderConnectionTo = this.sender.getConnectionTo();
		if (senderConnectionTo.equals(connectionTo)){
			this.sender = new Sender(this, connectionFrom);
			this.sender.connect();
		}
		else{
			this.sender.sendLogin(myConnection, connectionFrom);
		}
		return 0;	
	}
	
	public int sendLogin(Connection connectionOld, Connection connectionNew){

		Connection senderConnectionTo = this.sender.getConnectionTo();
		if (senderConnectionTo.equals(connectionOld)){
			this.sender = new Sender(this, connectionNew);
			this.sender.connect();
		}else{
			this.sender.sendLogin(connectionOld, connectionNew);
		}
		
		return 0;
	}
	
	public int logout(){
		return this.sender.sendLogout(myConnection, this.sender.getConnectionTo());
	}
	
	public int sendLogout(Connection disconnectedConnection, Connection newLastConnection){
		if (disconnectedConnection.equals(this.sender.getConnectionTo())){
			this.sender = new Sender(this, newLastConnection);
			this.sender.connect();
		}
		else{
			this.sender.sendLogout(disconnectedConnection, newLastConnection);
		}
		
		return 0;
	}
	
	
	
	private Receptor createReceptor(int myPort){
		String name = "Receptor";
//		String name2 = "Receptor2";
		Receptor receptor = null;
		try {
			receptor = new Receptor(this);
			
			IReceptor stub = (IReceptor) UnicastRemoteObject.exportObject(receptor, myConnection.getPort());
			
			Registry registry = LocateRegistry.createRegistry(myConnection.getPort());
//			registry.rebind(name, receptor);
			registry.rebind(name, stub);
		}
		catch (Exception e) {
			System.err.println("Data exception: " + e.getMessage());
		}
		return receptor;
	}

	public Receptor getReceptor() {
		return receptor;
	}

	public Sender getSender() {
		return sender;
	}
	
	public Connection getMyConnection(){
		return this.myConnection;
	}
	
	public String getId(){
		return myConnection.getId();
	}
	
	public ArrayList<Message> getMessages(){
		return this.messageList;
	}
	
	public Lamport getLamport(){
		return this.lamport;
	}
	
	public void getCurrentLamportStatus(){
		this.sender.getLamportStatus(this.myConnection);
	}
	
	
}
