
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {

	private static int port=8000;
	public static void main(String[] args) {
		
		
		try (//	try (//pick a port to connect and listen to.
					ServerSocket socket = new ServerSocket(port)) {
						System.out.println("Listening on port:"+port);
						 Socket client;
						
						 while((client=socket.accept())!=null)//while connected (forever)
						{
								 System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
								 ConnectionHandler handler = new ConnectionHandler(client);
								 Thread t = new Thread(handler);
								 t.start();

						}
					} catch (IOException e) {
					
						e.printStackTrace();
					}
					}

	


}
