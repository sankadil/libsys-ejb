package pack;
/**
 * Home interface for Enterprise Bean: firstBean
 */
public interface firstBeanHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: firstBean
	 */
	public pack.firstBean create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
