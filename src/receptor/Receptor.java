package receptor;

import java.rmi.RemoteException;

import node.LamportStatus;
import node.Message;
import node.Node;

public class Receptor implements IReceptor{
	
	private Node node;
	private String ipFrom;
	private int portFrom;
	
	
	public Receptor(Node node){
		this.node = node;
	}

	public int login(String ipTo, int portTo, String ipFrom, int portFrom) throws RemoteException {
		
		this.ipFrom = ipFrom;
		this.portFrom = portFrom;
		
		this.node.receiveLogin(ipTo, portTo, ipFrom, portFrom);
		
		return 0;
	}
	
	public int sendLogin(String ipOld, int portOld, String ipNew, int portNew) throws RemoteException {
		this.node.sendLogin(ipOld, portOld, ipNew, portNew);
		return 0;
	}
	
	public int sayHi(String ipFrom, int portFrom) throws RemoteException{
		this.ipFrom = ipFrom;
		this.portFrom = portFrom;
		
		return 0;
		
	}
	
	public int sendLogout(String disconnectedIp, int disconnectedPort, String newLastIp, int newLastPort) throws RemoteException {
		this.node.sendLogout(disconnectedIp, disconnectedPort, newLastIp, newLastPort);
		return 0;
	}
	
	public int receiveMessage(Message message, String senderIp, int senderPort){
		this.node.receiveMessage(message, senderIp, senderPort);
		return 0;
	}
	
	public String getIpFrom() {
		return ipFrom;
	}

	public int getPortFrom() {
		return portFrom;
	}

	public int receiveLamportStatus(LamportStatus actualStatus, String senderIp, int senderPort) throws RemoteException {
		actualStatus.addMyStatus(this.node.getLamport());
		if (senderIp.equals(this.node.getMyIp()) && senderPort == this.node.getMyPort()){
			this.node.getLamport().updateCurrentStatus(actualStatus);
		}
		else{
			this.node.getSender().retransmitLamportStatus(actualStatus, senderIp, senderPort);
		}
		return 0;

	}



}