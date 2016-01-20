package receptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

import node.LamportUpdate;

public interface IReceptor extends Remote{
	
	public int login(String ipTo, int portTo, String ipFrom, int portFrom) throws RemoteException;
	public int sendLogin(String ipOld, int portOld, String ipNew, int portNew) throws RemoteException;
	public int sayHi(String ipFrom, int portFrom) throws RemoteException;
	public int sendLogout(String ipFrom, int portFrom, String newLastIp, int newLastPort) throws RemoteException;
	public int receiveMessage(String message, String senderIp, int senderPort) throws RemoteException;
	public int receiveLamportUpdate(LamportUpdate lu, String senderIp, int senderPort) throws RemoteException;
//	public int updateLamportVectors();


}
