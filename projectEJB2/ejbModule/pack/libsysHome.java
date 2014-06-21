package pack;
/**
 * Home interface for Enterprise Bean: libsys
 */
public interface libsysHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: libsys
	 */
	public pack.libsys create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
