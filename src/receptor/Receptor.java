package receptor;

import java.rmi.RemoteException;

import node.Connection;
import node.LamportStatus;
import node.Message;
import node.Node;

public class Receptor implements IReceptor{
	
	private Node node;
	private Connection connectionFrom;
	
	
	public Receptor(Node node){
		this.node = node;
	}

	public int login(Connection connectionTo, Connection connectionFrom) throws RemoteException {
		
		this.connectionFrom = connectionFrom;
		
		this.node.receiveLogin(connectionTo, connectionFrom);
		
		return 0;
	}
	
	public int sendLogin(Connection connectionOld, Connection connectionNew) throws RemoteException {
		this.node.sendLogin(connectionOld, connectionNew);
		return 0;
	}
	
	public int sayHi(Connection connectionFrom) throws RemoteException{
		this.connectionFrom = connectionFrom;
		
		return 0;
		
	}
	
	public int sendLogout(Connection disconnectedConnection, Connection newLastConnection) throws RemoteException {
		this.node.sendLogout(disconnectedConnection, newLastConnection);
		return 0;
	}
	
	public int receiveMessage(Message message, Connection senderConnection){
		this.node.receiveMessage(message, senderConnection);
		return 0;
	}
	
	public Connection getConnectionFrom(){
		return this.connectionFrom;
	}

	public int receiveLamportStatus(LamportStatus actualStatus, Connection senderConnection) throws RemoteException {
		actualStatus.addMyStatus(this.node.getLamport());
		if (senderConnection.equals(this.node.getMyConnection())){
			this.node.getLamport().updateCurrentStatus(actualStatus);
		}
		else{
			this.node.getSender().retransmitLamportStatus(actualStatus, senderConnection);
		}
		return 0;

	}



}