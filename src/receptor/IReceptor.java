package receptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IReceptor extends Remote{
	
	public int login(String ipTo, int portTo, String ipFrom, int portFrom) throws RemoteException;
	public int sendLogin(String ipOld, int portOld, String ipNew, int portNew) throws RemoteException;
	public int sayHi(String ipFrom, int portFrom) throws RemoteException;
	public int logout() throws RemoteException;


}
