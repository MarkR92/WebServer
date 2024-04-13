
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
	//private  int portnum=8001;
	// public static void main(String [] arg)
	// {
	// 	connect();
	// 	//insert
	// 	// try {
	// 	// 	for(int i =0;i<200;i++)
	// 	// 	{
	// 	// 		addPort();
	// 	// 	}
	// 	// } catch (SQLException e) {
			
	// 	// 	e.printStackTrace();
	// 	// }
	// 	disconnect();
		
	// }
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
	//insert into users (username) values ('Test');
	public  void addUser(String name) throws SQLException
	{
		String insertSQL="insert into users (username) values (?)";//sql
		PreparedStatement insertStatement=conn.prepareStatement(insertSQL);//prepare
		insertStatement.setString(1,name);
		insertStatement.executeUpdate(); //insert the new structure into the db
		insertStatement.close();
	}

	// public  void addPort() throws SQLException
	// {
	// 	// String insertSQL="insert into ports (port) values (?)";//sql
	// 	// PreparedStatement insertStatement=conn.prepareStatement(insertSQL);//prepare
	// 	// insertStatement.setInt(1,portnum);
	// 	// insertStatement.executeUpdate(); //insert the new structure into the db
	// 	// insertStatement.close();
	// 	// portnum++;
	// }
	public int findFreePort() 
	{
		connect();
		try {
			String insertSQL="SELECT port from ports where bound = false";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();
		disconnect();

		return result.getInt(1);
		//disconnect();
		} catch (Exception e) {
			
		}
	
		return 0;
		//disconnect();
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
