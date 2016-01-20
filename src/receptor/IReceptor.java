package receptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

import node.Connection;
import node.LamportStatus;
import node.Message;

public interface IReceptor extends Remote{
	
	public int login(Connection connectionTo, Connection connectionFrom) throws RemoteException;
	public int sendLogin(Connection connectionOld, Connection connectionNew) throws RemoteException;
	public int sayHi(Connection connectionFrom) throws RemoteException;
	public int sendLogout(Connection connectionFrom, Connection newLastConnection) throws RemoteException;
	public int receiveMessage(Message message, Connection senderConnection) throws RemoteException;
	public int receiveLamportStatus(LamportStatus actualStatus, Connection senderConnection) throws RemoteException;


}
