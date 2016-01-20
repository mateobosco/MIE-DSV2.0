package sender;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import node.Connection;
import node.LamportStatus;
import node.Message;
import node.Node;

import receptor.IReceptor;

public class Sender {
	
	private Connection connectionTo;
	private Node node;
	private IReceptor receptor;
	
	public Sender(Node node, Connection connectionTo){
		this.node = node;
		this.connectionTo = connectionTo;
	}
	
	public int connect(){
		
		String name = "Receptor";
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(this.connectionTo.getIp(), this.connectionTo.getPort());
			this.receptor = (IReceptor) registry.lookup(name);
			this.receptor.sayHi(node.getMyConnection());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int login(Connection connectionTo, Connection myConnection){
		try {
			this.receptor.login(connectionTo, myConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogin(Connection connectionFrom, Connection connectionNew){
		try {
			this.receptor.sendLogin(connectionFrom, connectionNew);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogout(Connection disconnectedConnection, Connection newLastConnection){
		try {
			this.receptor.sendLogout(disconnectedConnection, newLastConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int sendMessage(Message message){
		int val = -1;
		try {
			val = this.receptor.receiveMessage(message, this.node.getMyConnection());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public int retransmitMessage(Message message, Connection senderConnection){
		int val = -1;
		try {
			val = this.receptor.receiveMessage(message, senderConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public Connection getConnectionTo(){
		return this.connectionTo;
	}
	
	public void getLamportStatus(Connection senderConnection){
		LamportStatus actualStatus = new LamportStatus();
		try {
			this.receptor.receiveLamportStatus(actualStatus, senderConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	public void retransmitLamportStatus(LamportStatus actualStatus,	Connection senderConnection) {
		try {
			this.receptor.receiveLamportStatus(actualStatus, senderConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


}
