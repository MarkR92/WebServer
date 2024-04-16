
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
	private final String url = "jdbc:postgresql://db-cs335-webserver.postgres.database.azure.com/test";
	private final String user = "rootadmin";
	private final String password = "CS335_Project";
	private Connection conn ;
	private static ArrayList<Integer> loadedPortList= new ArrayList<>();
	private static ArrayList<String> loadedHTMLList= new ArrayList<>();
	private static ArrayList<String> loadedCSSList= new ArrayList<>();
	private static ArrayList<String> loadedJavaScriptList= new ArrayList<>();
	//private static ArrayList<Integer> loadedPortList= new ArrayList<>();
	

	
	public Database()
	{
		// connect();
		// loadPorts() ;
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
	public void loadPorts() 
	{
		//connect();
		try {
			String insertSQL="SELECT port from ports where bound = true  Order by port";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();//first result is col header so go to next result
		//disconnect();
		while(result.next()) //loop thru results... i.e. loop thru each file name found
		{
			
			//System.out.println(result.getString(1));
			int port= Integer.parseInt(result.getString(1));
			loadedPortList.add(port);
		
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String insertSQL="SELECT html from ports where bound = true  Order by port";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();//first result is col header so go to next result
		//disconnect();
		while(result.next()) //loop thru results... i.e. loop thru each file name found
		{
			
			String html =result.getString(1);
			loadedHTMLList.add(html);
		
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String insertSQL="SELECT css from ports where bound = true  Order by port";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();//first result is col header so go to next result
		//disconnect();
		while(result.next()) //loop thru results... i.e. loop thru each file name found
		{
			
			String css =result.getString(1);
			loadedCSSList.add(css);
		
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String insertSQL="SELECT js from ports where bound = true  Order by port";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			ResultSet result=checkStatement.executeQuery();
			result.next();//first result is col header so go to next result
		//disconnect();
		while(result.next()) //loop thru results... i.e. loop thru each file name found
		{
			
			String js =result.getString(1);
			loadedJavaScriptList.add(js);
		
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	//	return 0;
		//disconnect();
	}
	public ArrayList<Integer> getLoadedPortList()
	{
		return loadedPortList;
	}
	public void clearLoadedPortList()
	{
		 loadedPortList.clear();;
	}
	public ArrayList<String> getLoadedHTMLList()
	{
		return loadedHTMLList;
	}
	public ArrayList<String> getLoadedCSSList()
	{
		return loadedCSSList;
	}
	public ArrayList<String> getLoadedJavaScriptList()
	{
		return loadedJavaScriptList;
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
	public String getHTML(int port) 
	{
		try {
			String insertSQL="SELECT html from ports where port = ?  Order by port LIMIT 1";//sql
			PreparedStatement checkStatement=conn.prepareStatement(insertSQL);
			checkStatement.setString(1, ""+port);
			ResultSet result=checkStatement.executeQuery();
			result.next();
		//disconnect();

		return result.getString(1);
		//disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public void setFiles(int port,String html,String css, String js) 
	{
	
		try {
			String updateSQL="Update ports SET html=? where port = ?";//sql
			PreparedStatement updateStatement=conn.prepareStatement(updateSQL);//prepare
			updateStatement.setString(1,""+html);
			updateStatement.setString(2,""+port);
			updateStatement.executeUpdate(); //insert the new structure into the db
			updateStatement.close();

			 updateSQL="Update ports SET css=? where port = ?";//sql
			 updateStatement=conn.prepareStatement(updateSQL);//prepare
			updateStatement.setString(1,""+css);
			updateStatement.setString(2,""+port);
			updateStatement.executeUpdate(); //insert the new structure into the db
			updateStatement.close();

			 updateSQL="Update ports SET js=? where port = ?";//sql
			 updateStatement=conn.prepareStatement(updateSQL);//prepare
			updateStatement.setString(1,""+js);
			updateStatement.setString(2,""+port);
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
	// public  void addUser(String name) throws SQLException
	// {
	// 	String insertSQL="insert into users (username) values (?)";//sql
	// 	PreparedStatement insertStatement=conn.prepareStatement(insertSQL);//prepare
	// 	insertStatement.setString(1,name);
	// 	insertStatement.executeUpdate(); //insert the new structure into the db
	// 	insertStatement.close();
	// }
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
