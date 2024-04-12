
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	private static final String url = "jdbc:postgresql://db-cs335-webserver.postgres.database.azure.com/test";
	private static final String user = "rootadmin";
	private static final String password = "CS335_Project";
	private static  Connection conn ;
	
	public static void main(String [] arg)
	{
		connect();
		//insert
		// try {
		// 	//addUser("Mark2");
		// } catch (SQLException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		disconnect();
		
	}
	public static Connection connect()
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
	public static void addUser(String name) throws SQLException
	{
		String insertSQL="insert into users (username) values (?)";//sql
		PreparedStatement insertStatement=conn.prepareStatement(insertSQL);//prepare
		insertStatement.setString(1,name);
		insertStatement.executeUpdate(); //insert the new structure into the db
		insertStatement.close();
	}
	public static void disconnect()
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
