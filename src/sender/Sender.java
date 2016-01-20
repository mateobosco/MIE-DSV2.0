package sender;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import node.LamportUpdate;
import node.Node;

import receptor.IReceptor;

public class Sender {
	
	private String ipTo;
	private int portTo;
	private Node node;
	private IReceptor receptor;
	
	public Sender(Node node, String ipTo, int portTo){
		this.node = node;
		this.ipTo = ipTo;
		this.portTo = portTo;
	}
	
	public int connect(){
		
		String name = "Receptor";
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(this.ipTo, this.portTo);
			this.receptor = (IReceptor) registry.lookup(name);
			this.receptor.sayHi(node.getMyIp(), node.getMyPort());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int login(String ipTo, int portTo, String myIp, int myPort){
		try {
			this.receptor.login(ipTo, portTo, myIp, myPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogin(String ipFrom, int portFrom, String ipNew, int portNew){
		try {
			this.receptor.sendLogin(ipFrom, portFrom, ipNew, portNew);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int sendLogout(String disconnectedIp, int disconnectedPort, String newLastIp, int newLastPort){
		try {
			this.receptor.sendLogout(disconnectedIp, disconnectedPort, newLastIp, newLastPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int sendMessage(String message){
		int val = -1;
		try {
			val = this.receptor.receiveMessage(message, this.node.getMyIp(), this.node.getMyPort());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public int retransmitMessage(String message, String senderIp, int senderPort){
		int val = -1;
		try {
			val = this.receptor.receiveMessage(message, senderIp, senderPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public String getIpTo(){
		return ipTo;
	}
	
	public int getPortTo(){
		return portTo;
	}


	public void sendLamportUpdate(LamportUpdate lu) {
		try {
			this.receptor.receiveLamportUpdate(lu, this.node.getMyIp(), this.node.getMyPort());
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}

	public void retransmitLamportUpdate(LamportUpdate lu, String senderIp,int senderPort) {
		try {
			this.receptor.receiveLamportUpdate(lu, senderIp, senderPort);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


}
