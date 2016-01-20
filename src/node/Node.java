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
	
	private String myIp;
	private int myPort;
	
	private ArrayList<Message> messageList;
	
	public Node(String ipTo, int portTo, String myIp, int myPort){
		this.myIp = myIp;
		this.myPort = myPort;
		
		this.receptor = this.createReceptor(myPort);
		this.sender = new Sender(this, ipTo, portTo);
		this.lamport = new Lamport(this);
		this.messageList = new ArrayList<Message>();
		
		this.sender.connect();
				
		this.sender.login(ipTo, portTo, myIp, myPort);	
	}
	
	public int sendMessage(Message message){
		this.lamport.lock();
		this.sender.sendMessage(message);
		this.lamport.unlock();
		return 0;
	}
	
	public int receiveMessage(Message message, String senderIp, int senderPort){
		this.messageList.add(message);
		if (!senderIp.equals(this.myIp) || senderPort != this.myPort){
			this.sender.retransmitMessage(message, senderIp, senderPort);
		}
		return 0;
	}
	
	public int receiveLogin(String ipTo, int portTo, String ipFrom, int portFrom){
				
		String senderIpTo = this.sender.getIpTo();
		int senderPortTo = this.sender.getPortTo();
		if (senderIpTo.equals(ipTo) && senderPortTo == portTo){
			this.sender = new Sender(this, ipFrom, portFrom);
			this.sender.connect();
		}
		else{
			this.sender.sendLogin(myIp, myPort, ipFrom, portFrom);
		}
		return 0;	
	}
	
	public int sendLogin(String ipOld, int portOld, String ipNew, int portNew){
		
		String senderIpTo = this.sender.getIpTo();
		int senderPortTo = this.sender.getPortTo();
		if (senderIpTo.equals(ipOld) && senderPortTo == portOld){
			this.sender = new Sender(this, ipNew, portNew);
			this.sender.connect();
		}else{
			this.sender.sendLogin(ipOld, portOld, ipNew, portNew);
		}
		
		return 0;
	}
	
	public int logout(){
		return this.sender.sendLogout(myIp, myPort, this.sender.getIpTo(), this.sender.getPortTo());
	}
	
	public int sendLogout(String disconnectedIp, int disconnectedPort, String newLastIp, int newLastPort){
		if (disconnectedIp.equals(this.sender.getIpTo()) && disconnectedPort == this.sender.getPortTo()){
			this.sender = new Sender(this, newLastIp, newLastPort);
			this.sender.connect();
		}
		else{
			this.sender.sendLogout(disconnectedIp, disconnectedPort, newLastIp, newLastPort);
		}
		
		return 0;
	}
	
	
	
	private Receptor createReceptor(int myPort){
		String name = "Receptor";
//		String name2 = "Receptor2";
		Receptor receptor = null;
		try {
			receptor = new Receptor(this);
			
			IReceptor stub = (IReceptor) UnicastRemoteObject.exportObject(receptor, myPort);
			
			Registry registry = LocateRegistry.createRegistry(myPort);
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

	public String getMyIp() {
		return myIp;
	}

	public int getMyPort() {
		return myPort;
	}
	
	public ArrayList<Message> getMessages(){
		return this.messageList;
	}
	
	public Lamport getLamport(){
		return this.lamport;
	}
	
	public void getCurrentLamportStatus(){
		this.sender.getLamportStatus(this.myIp, this.myPort);
	}
	
	
}
