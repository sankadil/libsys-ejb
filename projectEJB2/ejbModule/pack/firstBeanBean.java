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
 * Bean implementation class for Enterprise Bean: firstBean
 */
public class firstBeanBean implements javax.ejb.SessionBean {
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
	
//	---------------------------------------------------------------------------

	public String getMovie1(String id){
		Connection conc = CreateCon();
		PreparedStatement ps = null;
		ResultSet rs =null;
		String name = "";
		
		try{
			ps = conc.prepareStatement("select username from users where userid = ? ");
			//ps = conc.prepareStatement("select name from movies whe");
			ps.setString(1,id);
			rs = ps.executeQuery();
			//Vector v = new Vector();
			while (rs.next()){
				//Reso ress = new Reso();
				name = rs.getString("username");
				//v.addElement(rs.getString("name"));
				//System.out.println(v);
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
//	------------------------------------------------------------------	
	/*  public Vector retriveMove(){			
	
			  Vector v = new Vector();
			  Connection con = CreateCon();
			  PreparedStatement ps = null;
			  ResultSet rs = null;
			  String name = "";
			  String status = "";
			  String movie_id = "";	
	
			  try {
				  ps =con.prepareStatement("SELECT * From users");
				  //ps.setString(1, id);
				  rs = ps.executeQuery();
				  while (rs.next()) {
			
					  Movie movie=new Movie();
					  name = rs.getString("userid");
					  movie_id = rs.getString("username");
					  status = rs.getString("password");
					  movie.setName(name);
					  movie.setMovie_id(movie_id);
					  movie.setStatus(status);
					  v.add(movie);				
					  System.out.println("vector is : "+v);
					  System.out.println("vector size is : "+v.size());
		
				  }

			  } catch (Exception ex) {
				  ex.printStackTrace();

			  } 
			  finally 
			  {
				  CloseAll(con, rs, ps);
			  }
	
			  return v;
			
	  }*/
	  
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


}
