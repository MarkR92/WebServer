
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private  final String url = "jdbc:postgresql://db-cs335-webserver.postgres.database.azure.com/test";
	private  final String user = "rootadmin";
	private  final String password = "CS335_Project";
	private   Connection conn ;
	
	public Database()
	{
		connect();
	}
	public  Connection connect()
	{
		  conn = null;
	        try {
	            conn = DriverManager.getConnection(url, user, password);
	            System.out.println("Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        return conn;
	}
	public int findFreePort() 
	{
		//connect();
		try {
			String insertSQL="SELECT port from ports where bound = false  Order by port LIMIT 1";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();
		//disconnect();

		return result.getInt(1);
		//disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return 0;
		//disconnect();
	}
	public void setPortBound(int port) 
	{
	
		try {
			String updateSQL="Update ports SET bound=true where port = ?";//sql
			PreparedStatement updateStatement=conn.prepareStatement(updateSQL);//prepare
			updateStatement.setString(1,""+port);
			updateStatement.executeUpdate(); //insert the new structure into the db
			updateStatement.close();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		//disconnect();
	}
	public void setPortFree(int port) 
	{
		
		try {
			String updateSQL="Update ports SET bound=false where port = ?";//sql
			PreparedStatement updateStatement=conn.prepareStatement(updateSQL);//prepare
			updateStatement.setString(1,""+port);
			updateStatement.executeUpdate(); //insert the new structure into the db
			updateStatement.close();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
	}
	public void setAllPortsFree() //just in case :P
	{
		
		try {
			String updateSQL="Update ports SET bound=false";//sql
			PreparedStatement updateStatement=conn.prepareStatement(updateSQL);//prepare
			//updateStatement.setString(1,""+port);
			updateStatement.executeUpdate(); //insert the new structure into the db
			updateStatement.close();
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		//disconnect();
	}
	//insert into users (username) values ('Test');
	public  void addUser(String name) throws SQLException
	{
		String insertSQL="insert into users (username) values (?)";//sql
		PreparedStatement insertStatement=conn.prepareStatement(insertSQL);//prepare
		insertStatement.setString(1,name);
		insertStatement.executeUpdate(); //insert the new structure into the db
		insertStatement.close();
	}
	public  void disconnect()
	{
		if(conn!=null)
		{
			try {
				conn.close();
				System.out.println("Disconnected");
			} catch (SQLException e) {
				
			}
			
		}
	}
}
