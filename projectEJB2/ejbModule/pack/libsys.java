package pack;
import java.util.Vector;
import java.rmi.RemoteException;
/**
 * Remote interface for Enterprise Bean: libsys
 */
public interface libsys extends javax.ejb.EJBObject {
	
	//public Vector retriveMove() throws RemoteException;
	public String getBook(String id)throws RemoteException;
	public Vector getSimpleSearch(String field_type,String keyword_collection[],String words_connecter )throws RemoteException;
	public Vector getBrowsing(String field_type,String keyword)throws RemoteException;
	public Vector getISBN(String keyword_isbn )throws RemoteException;
	public Vector getAdvanceSearch(String field_type1,String field_type2,String field_type3,String words_connecter1,String words_connecter2,String words_connecter3,String keyword_collection1[],String  keyword_collection2[],String  keyword_collection3[] ,String interword_connector1,String interword_connector2)throws RemoteException;
	public Vector getAditionalSearch(String  keyword_collection_author[],String  keyword_collection_title[],String  keyword_collection_suject[],String words_connecter[] )throws RemoteException;
	public boolean isUser(String user,String password)throws RemoteException;
}
