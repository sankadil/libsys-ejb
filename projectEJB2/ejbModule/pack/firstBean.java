package pack;
import java.util.Vector;
import java.rmi.RemoteException;
/**
 * Remote interface for Enterprise Bean: firstBean
 */
public interface firstBean extends javax.ejb.EJBObject {
	//public Vector retriveMove() throws RemoteException;
	public String getMovie1(String id)throws RemoteException;
}
