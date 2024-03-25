
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {

	
	/*
	 * use windows command line curl command to send requests
	 * postman and chrome browser error sometimes for post. needs to be investigated
	 * 
	 * curl examples
	 * GET:
	 * curl localhost:8097/people 
	 * curl localhost:8097/people -v 
	 * POST:
	 * curl -d "person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian" http://localhost:8097/people 
	 * curl.exe -d "person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian" localhost:8097/people 
	 * 
	 * curl -d "job1=mime&job2=janitor&job3=gangam styler&job4=krusty krab chef&job5=bee keeper" http://localhost:8097/people/jobs
	 *  
	 * curl.exe -X PUT -d "person1=bark&person2=rob&person3=khate&person4=dan&person5=dorian" localhost:8097/people
	 * 
	 * 
	 */
	public static void main(String[] args) {
		
		
		try (//	try (//pick a port to connect and listen to.
					ServerSocket socket = new ServerSocket(8080)) {
						System.out.println("Listening");
						 Socket client;
						
						 while((client=socket.accept())!=null)//while connected (forever)
						{
								 System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
								 ConnectionHandler handler = new ConnectionHandler(client);
								 Thread t = new Thread(handler);
								 t.start();
								//Socket client=socket.accept();// launch in thread to make concurrent. Needs invest
						
							


						}
					} catch (IOException e) {
						//TODO Auto-generated catch block
						e.printStackTrace();
					}
					}

	


}
