package pack;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Bean implementation class for Enterprise Bean: libsys
 */
public class libsysBean implements javax.ejb.SessionBean {
	
	  
//	-------------------------------------------------------------------------	

	  private Connection CreateCon() {
	  Connection con = null;
	  try {
		  InitialContext ic = new InitialContext();
		  DataSource ds = (DataSource) ic.lookup("jdbc/tutapn");
		  con = ds.getConnection();
		  System.out.println("Connection to tutorialApn Successfull");

	  } catch (NamingException e) {

		  e.printStackTrace();
		  System.out.println("Connection to tutorialApn UNSuccessfull");
	  } catch (SQLException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		  System.out.println("Connection to tutorialApn UNSuccessfull");
	  }
	  return con;
  }
	
//	-------------------------------------------------------------	
	
//	-------------------------------------------------------------	
	
	
	private boolean CloseAll(Connection con,ResultSet rs,PreparedStatement st)
	 {
			boolean closed = true;
			try {
				st.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("preparedstatement not closed properly");
				closed = false;
			}
			try {
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("resultset not closed properly");
				closed = false;
			}
			try {
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Connection not closed properly");
				closed = false;
			}
			return closed;
		}
//	----------------------------------------------------------------------------------		
	

	public String getBook(String id){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;
		String name = "";
		
		try{
			ps = conc.prepareStatement("select title from books where assno = ? ");			
			ps.setString(1,id);
			rs = ps.executeQuery();			
			while (rs.next()){				
				name = rs.getString("title");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return name;
		
	}
//	---------------------------------------------------------------------------
	
	
//	----------------------------------------------------------------------------------		
	

	public boolean isUser(String user,String password){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;
		boolean log=false;
		
		try{
			ps = conc.prepareStatement("select count(userid) from profiles where username = ? and password = ? ");			
			ps.setString(1,user);
			ps.setString(2,password);
			System.out.println(".........before executQuery.....");
			rs = ps.executeQuery();
			System.out.println("..........outside log while looop.....");			
			while (rs.next()){				
					if( rs.getInt(1)==1)
					{
						System.out.println("inside log while looop.....");
						log=true;
					}				
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return log;
		
	}
//	---------------------------------------------------------------------------
		
	
	
	
	
//	----------------------------------------------------------------------------------		
	

	public Vector getSimpleSearch(String field_type,String keyword_collection[],String words_connecter ){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;			
		Vector books_vector = new Vector();
		//System.out.println("field_type :"+field_type+"   keyword_collection[0]:"+keyword_collection[0]+"    words_connecter:"+words_connecter);
		try{
			
			if(words_connecter != "PHRASE")
			{
				
					String q1="SELECT author, title,classno,isbn,subject,assno FROM books WHERE ";
					String q2="("+field_type+"  LIKE '"+  keyword_collection[0] +"%'" +"  OR "+ field_type +"  LIKE '"+"%"+" "+ keyword_collection[0]+"%'"+")";
					for(int i=1;i<keyword_collection.length;i++)
					{
					q2=q2+words_connecter+"("+" "+field_type+"  LIKE '"+  keyword_collection[i] +"%'" +"  OR "+ field_type +"  LIKE '"+"%"+" "+ keyword_collection[i]+"%'"+")";
					}
					
					try{
						System.out.println("(q1+q2)= total query that executing is :"+q1+q2);
						ps = conc.prepareStatement(q1+q2);
						rs=ps.executeQuery();
					}
					catch (Exception e) {
						System.out.println(e);
					}				
			
			}
			
			else if(words_connecter == "PHRASE")
			{
				// use keyword_field.getText()  for query
				System.out.println("!PHRASE");
				ps = conc.prepareStatement("SELECT author, title,classno,isbn,subject,assno FROM books WHERE "+field_type+"='"+ keyword_collection[0]+"'  ");
				rs=ps.executeQuery();//("SELECT author, title,classno,isbn,subject,assno FROM books WHERE "+field_type+"='"+ keyword_collection[0]+"'  ");

			}
			
			
			while (rs.next()){
				Book book=new Book();				
				book.setTitle(rs.getString("title"));
				book.setSubject(rs.getString("subject"));
				book.setIsbn(rs.getString("isbn"));
				book.setClassno(rs.getString("classno"));				
				book.setAuthor(rs.getString("author"));
				book.setAssno(rs.getString("assno"));
				books_vector.addElement(book);
				System.out.println(books_vector);
				System.out.println("book details : "+book.getTitle()+book.getAuthor()+book.getSubject());
			}
			
			

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return books_vector;
		
	}
//	---------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------		
	

	public Vector getBrowsing(String field_type,String keyword){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;		
		Vector books_vector = new Vector();
		try{
			ps = conc.prepareStatement("SELECT author, title,classno,isbn,subject,assno FROM books WHERE "+field_type+"  LIKE '"+keyword+"%'  ");			
			//ps.setString(1,id);
			rs = ps.executeQuery();			
			while (rs.next()){				
				Book book=new Book();				
				book.setTitle(rs.getString("title"));
				book.setSubject(rs.getString("subject"));
				book.setIsbn(rs.getString("isbn"));
				book.setClassno(rs.getString("classno"));				
				book.setAuthor(rs.getString("author"));
				book.setAssno(rs.getString("assno"));
				books_vector.addElement(book);
				System.out.println(books_vector);
				System.out.println("book details : "+book.getTitle()+book.getAuthor()+book.getSubject());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return books_vector;
		
	}
//	---------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------		
	

	public Vector getISBN(String keyword_isbn ){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;		
		Vector books_vector = new Vector();
		try{
			ps = conc.prepareStatement("SELECT * FROM books WHERE isbn='"+keyword_isbn+"'  ");			
			//ps.setString(1,id);
			rs = ps.executeQuery();			
			while (rs.next()){				
				Book book=new Book();				
				book.setTitle(rs.getString("title"));
				book.setSubject(rs.getString("subject"));
				book.setIsbn(rs.getString("isbn"));
				book.setClassno(rs.getString("classno"));				
				book.setAuthor(rs.getString("author"));
				book.setAssno(rs.getString("assno"));
				books_vector.addElement(book);
				System.out.println(books_vector);
				System.out.println("book details : "+book.getTitle()+book.getAuthor()+book.getSubject());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return books_vector;
		
	}
//	---------------------------------------------------------------------------
	
	
//	----------------------------------------------------------------------------------		
	

	public Vector getAdvanceSearch(String field_type1,String field_type2,String field_type3,String words_connecter1,String words_connecter2,String words_connecter3,String keyword_collection1[],String  keyword_collection2[],String  keyword_collection3[] ,String interword_connector1,String interword_connector2){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;		
		Vector books_vector = new Vector();
		String q1="SELECT author, title,classno,isbn,subject,assno FROM books WHERE ";
		String q2=null ;
		String q3=null ;
		String q4=null ;
//		  ------------------------------------------------------------------------------------------------------------------------------
		if(words_connecter1 != "PHRASE")
		{
			 q2="("+field_type1+"  LIKE '"+  keyword_collection1[0] +"%'" +"  OR "+ field_type1 +"  LIKE '"+"%"+" "+ keyword_collection1[0]+"%'"+")";
			for(int i=1;i<keyword_collection1.length;i++)
			{
			q2=q2+words_connecter1+"("+" "+field_type1+"  LIKE '"+  keyword_collection1[i] +"%'" +"  OR "+ field_type1+"  LIKE '"+"%"+" "+ keyword_collection1[i]+"%'"+")";
			}
		}

		if(words_connecter1 == "PHRASE")
		{
			String k=null;
			for(int i=0;i<keyword_collection1.length;i++)
			{
				k +=keyword_collection1[i]+" ";
			}	
//		   reset=stm.executeQuery("SELECT author, title,assno,isbn FROM books WHERE "+field_type1+"='"+ keyword1.getText()+"'  ");
			q2="("+field_type1+"="+ k+")";
		}
//		  ----------------------------------------------------------------------------------------------------------------------------------
//		  ------------------------------------------------------------------------------------------------------------------------------
		if(words_connecter2 != "PHRASE")
		{
				q3="("+field_type2+"  LIKE '"+  keyword_collection2[0] +"%'" +"  OR "+ field_type2 +"  LIKE '"+"%"+" "+ keyword_collection2[0]+"%'"+")";
				for(int i=1;i<keyword_collection2.length;i++)
				{
					q3=q3+words_connecter2+"("+" "+field_type2+"  LIKE '"+  keyword_collection2[i] +"%'" +"  OR "+ field_type2+"  LIKE '"+"%"+" "+ keyword_collection2[i]+"%'"+")";
				}
		}
		else if(words_connecter2 == "PHRASE")
		{
				String k=null;
				for(int i=0;i<keyword_collection2.length;i++)
				{
					k +=keyword_collection2[i]+" ";
				}
				//		   reset=stm.executeQuery("SELECT author, title,assno,isbn FROM books WHERE "+field_type2+"='"+ keyword2.getText()+"'  ");
				q3="("+field_type1+"="+ k+")";
		}
//		  ----------------------------------------------------------------------------------------------------------------------------------
//		  ------------------------------------------------------------------------------------------------------------------------------
		if(words_connecter3 != "PHRASE")
		{
				 q4="("+field_type3+"  LIKE '"+  keyword_collection3[0] +"%'" +"  OR "+ field_type3 +"  LIKE '"+"%"+" "+ keyword_collection3[0]+"%'"+")";
				for(int i=1;i<keyword_collection3.length;i++)
				{
				q4=q4+words_connecter3+"("+" "+field_type3+"  LIKE '"+  keyword_collection3[i] +"%'" +"  OR "+ field_type3+"  LIKE '"+"%"+" "+ keyword_collection3[i]+"%'"+")";
				}
		}

		else if(words_connecter3 == "PHRASE")
		{
				String k=null;
				for(int i=0;i<keyword_collection3.length;i++)
				{
					k +=keyword_collection3[i]+" ";
				}
				//		   reset=stm.executeQuery("SELECT author, title,assno,isbn FROM books WHERE "+field_type3+"='"+ keyword3.getText()+"'  ");
				q4="("+field_type1+"="+ k+")";
		}
//		  ----------------------------------------------------------------------------------------------------------------------------------
		String query=q1+q2+" "+interword_connector1+" "+q3+" "+interword_connector2+" "+q4 ;
		System.out.println(query);

		try{
			ps = conc.prepareStatement(query);			
			//ps.setString(1,id);
			rs = ps.executeQuery();			
			while (rs.next()){				
				Book book=new Book();				
				book.setTitle(rs.getString("title"));
				book.setSubject(rs.getString("subject"));
				book.setIsbn(rs.getString("isbn"));
				book.setClassno(rs.getString("classno"));				
				book.setAuthor(rs.getString("author"));
				book.setAssno(rs.getString("assno"));
				books_vector.addElement(book);
				System.out.println(books_vector);
				System.out.println("book details : "+book.getTitle()+book.getAuthor()+book.getSubject());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return books_vector;
		
	}
//	---------------------------------------------------------------------------
		
	
	
	
	
	
	
	
	
//	----------------------------------------------------------------------------------		
	

	public Vector getAditionalSearch(String  keyword_collection_author[],String  keyword_collection_title[],String  keyword_collection_suject[],String words_connecter[] ){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;		
		Vector books_vector = new Vector();		
		String q1="SELECT * FROM books WHERE ";
		String q2=null;
		String q3=null;
		String q4=null;
//		  -----------------------------------------------------------author-------------------------------------------------------------------------------------
		 if((String) words_connecter[0] != "PHRASE")
			{

				 q2="("+"author"+"  LIKE '"+  keyword_collection_author[0] +"%'" +"  OR "+ "author"+"  LIKE '"+"%"+" "+ keyword_collection_author[0]+"%'"+")";
	
				for(int i=1;i<keyword_collection_author.length;i++)
				{
	
				q2=q2+(String) words_connecter[0]+"("+" "+"author"+"  LIKE '"+  keyword_collection_author[i] +"%'" +"  OR "+ "author"+"  LIKE '"+"%"+" "+ keyword_collection_author[i]+"%'"+")";
	
				}
			}
//		  -----------------------
			else if((String) words_connecter[0] == "PHRASE")
			{
				String k=null;
				for(int i=0;i<keyword_collection_author.length;i++)
				{
					k +=keyword_collection_author[i]+" ";
				}
		q2="author = "+k+" ";

			}


//		  -------------------------------------------------------finish author query-------------------------------------------------------------------------




//		  -----------------------------------------------------------title-------------------------------------------------------------------------------------
		 if((String) words_connecter[1] != "PHRASE")
			{

					 q3="("+"title"+"  LIKE '"+   keyword_collection_title[0] +"%'" +"  OR "+ "title"+"  LIKE '"+"%"+" "+  keyword_collection_title[0]+"%'"+")";
		
					for(int i=1;i< keyword_collection_title.length;i++)
					{
		
					q3=q3+(String) words_connecter[1]+"("+" "+"title"+"  LIKE '"+   keyword_collection_title[i] +"%'" +"  OR "+ "title"+"  LIKE '"+"%"+" "+  keyword_collection_title[i]+"%'"+")";
		
					}
			}
//		  ---------------
			else if((String) words_connecter[1] == "PHRASE")
			{
				
					String k=null;
					for(int i=0;i<keyword_collection_title.length;i++)
					{
						k +=keyword_collection_title[i]+" ";
							}
					
					q3="title = "+k+" ";

			}

//		  -------------------------------------------------------finish title query-------------------------------------------------------------------------


//		  -----------------------------------------------------------suject-------------------------------------------------------------------------------------
		 if((String) words_connecter[2] != "PHRASE")
			{

				 q4="("+"subject"+"  LIKE '"+   keyword_collection_suject[0] +"%'" +"  OR "+ "subject"+"  LIKE '"+"%"+" "+  keyword_collection_suject[0]+"%'"+")";
	
				for(int i=1;i< keyword_collection_title.length;i++)
				{
	
				q4=q4+(String) words_connecter[2]+"("+" "+"subject"+"  LIKE '"+   keyword_collection_suject[i] +"%'" +"  OR "+ "subject"+"  LIKE '"+"%"+" "+  keyword_collection_suject[i]+"%'"+")";
	
				}
			}
//		  ---------------
			else if((String) words_connecter[2] == "PHRASE")
			{

		
						String k=null;
						for(int i=0;i<keyword_collection_suject.length;i++)
						{
							k +=keyword_collection_suject[i]+" ";
						}
						
				q4="subject = "+k+" ";

			}

//		  -------------------------------------------------------finish title query-------------------------------------------------------------------------







		try{
			ps = conc.prepareStatement(q1+q2+" AND "+q3+" AND "+q4);			
			//ps.setString(1,id);
			rs = ps.executeQuery();			
			while (rs.next()){				
				Book book=new Book();				
				book.setTitle(rs.getString("title"));
				book.setSubject(rs.getString("subject"));
				book.setIsbn(rs.getString("isbn"));
				book.setClassno(rs.getString("classno"));				
				book.setAuthor(rs.getString("author"));
				book.setAssno(rs.getString("assno"));
				books_vector.addElement(book);
				System.out.println(books_vector);
				System.out.println("book details : "+book.getTitle()+book.getAuthor()+book.getSubject());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			CloseAll(conc,rs,ps);
		}
	
		return books_vector;
		
	}
//	---------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private javax.ejb.SessionContext mySessionCtx;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
	

}
