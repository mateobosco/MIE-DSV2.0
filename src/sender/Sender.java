package sender;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import node.Connection;
import node.LamportStatus;
import node.Message;
import node.NetworkStatus;
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
		preventCrash();
		try {
			this.receptor.login(connectionTo, myConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogin(Connection connectionFrom, Connection connectionNew){
		preventCrash();
		try {
			this.receptor.sendLogin(connectionFrom, connectionNew);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogout(Connection disconnectedConnection, Connection newLastConnection){
		preventCrash();
		try {
			this.receptor.sendLogout(disconnectedConnection, newLastConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int sendMessage(Message message){
		preventCrash();
		int val = -1;
		try {
			val = this.receptor.receiveMessage(message, this.node.getMyConnection());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public int retransmitMessage(Message message, Connection senderConnection){
		preventCrash();
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
		preventCrash();
		LamportStatus actualStatus = new LamportStatus();
		try {
			this.receptor.receiveLamportStatus(actualStatus, senderConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	public void retransmitLamportStatus(LamportStatus actualStatus,	Connection senderConnection) {
		preventCrash();
		try {
			this.receptor.receiveLamportStatus(actualStatus, senderConnection);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void sendNetworkStatus(NetworkStatus status) {
		preventCrash();
		if (!status.isTriple()){
			try {
				this.receptor.updateNetworkStatus(status);
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private boolean checkNextAlive(){
		boolean sendSuccess = false;
		try {
			this.receptor.sayHi(connectionTo);
			sendSuccess = true;
		} catch (RemoteException e) {
			System.out.println("-----------Crash detected------------");
			this.connectionTo = this.node.getNetworkStatus().getTable().get(this.connectionTo.getId());
			this.connect();
		}
		return sendSuccess;
	}
	
	public void preventCrash(){
		boolean crashed = false;
		boolean res = false;
		while (!res){
			res = checkNextAlive();
			if (res == false) crashed = true;
		}
		if (crashed){
			NetworkStatus ns = this.node.getNetworkStatus();
			ns.set(node.getMyConnection(), this.connectionTo);
			ns.restartCounter();
			this.sendNetworkStatus(ns);
		}
	}


}
