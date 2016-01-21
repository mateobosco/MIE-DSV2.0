package node;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import program.ChatLogger;
import program.MessageReceiver;

import receptor.IReceptor;
import receptor.Receptor;
import sender.Sender;

public class Node {

	private Receptor receptor;
	private Sender sender;
	private Lamport lamport;
	private NetworkStatus status;
	
	private Connection myConnection;
		
	private MessageReceiver receiver = null;
	
	private ChatLogger logger;
	
	public Node(Connection connectionTo, Connection myConnection){
		this.myConnection = myConnection;
		this.logger = new ChatLogger(this.myConnection.getId());
		
		this.receptor = this.createReceptor(myConnection.getPort());
		this.sender = new Sender(this, connectionTo);
		this.lamport = new Lamport(this);
		this.status = new NetworkStatus();
		
		this.sender.connect();
		this.logger.log("Connected");
				
		this.sender.login(connectionTo, myConnection);
		this.logger.log("Send login to" + connectionTo.getId());
		this.sender.sendNetworkStatus(this.status);
		
	}
	
	public int sendMessage(Message message){
		this.lamport.lock();
		this.logger.log("Lock obtained");
		this.sender.sendMessage(message);
		this.logger.log("Sending message");
		this.lamport.unlock();
		this.logger.log("Lock released");
		return 0;
	}
	
	public int receiveMessage(Message message, Connection connectionSender){
		this.logger.log("Message received");
		if (!myConnection.equals(connectionSender)){
			this.sender.retransmitMessage(message, connectionSender);
		}
		
		if (this.receiver != null){
			this.receiver.receiveMessage(message);
		}
		
		return 0;
	}
	
	public int receiveLogin(Connection connectionTo, Connection connectionFrom){
		this.logger.log("Login received");
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
		this.logger.log("Sending Login");
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
		this.logger.log("Logout");
		return this.sender.sendLogout(myConnection, this.sender.getConnectionTo());
	}
	
	public int sendLogout(Connection disconnectedConnection, Connection newLastConnection){
		this.logger.log("retransmiting logout");
		if (disconnectedConnection.equals(this.sender.getConnectionTo())){
			this.sender = new Sender(this, newLastConnection);
			this.sender.connect();
		}
		else{
			this.sender.sendLogout(disconnectedConnection, newLastConnection);
		}
		
		return 0;
	}
	
	public void setMessageReceiver(MessageReceiver receiver){
		this.receiver = receiver;
	}

	private Receptor createReceptor(int myPort){
		this.logger.log("Receptor created");
		String name = "Receptor";
		Receptor receptor = null;
		try {
			receptor = new Receptor(this);
			System.setProperty("java.rmi.server.hostname", this.myConnection.getIp());
			IReceptor stub = (IReceptor) UnicastRemoteObject.exportObject(receptor, 0);
			
			Registry registry = LocateRegistry.createRegistry(myConnection.getPort());
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
	
	public Lamport getLamport(){
		return this.lamport;
	}
	
	public void getCurrentLamportStatus(){
		this.sender.getLamportStatus(this.myConnection);
	}
	
	public void updateNetworkStatus(NetworkStatus status){
		
		this.status.setTable(status.getTable());
	}
	
	public NetworkStatus getNetworkStatus(){
		return this.status;
	}
	
	
}
