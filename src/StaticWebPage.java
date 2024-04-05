// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StaticWebPage implements Runnable{

    private static int port=8000;
    private int currentPort;
    private String body;
    //private static Response clientOutput2;
    //Robbo classes should not know about other classes unless they absolutly have to
    private ServerSocket serverSocket2;
    public StaticWebPage(String body) throws IOException
    {
        
       this.body=body;
        port++;
        currentPort=port;
        System.out.println("port "+currentPort);
        serverSocket2 = new ServerSocket(currentPort);
    }
    public StaticWebPage(String body, int port) throws IOException
    {
        
       this.body=body;
        port++;
        currentPort=port;
        System.out.println("port "+currentPort);
        serverSocket2 = new ServerSocket(currentPort);
    }
    public int getPort()
    {
        return currentPort;
    }
    public void kill() throws IOException
    {
        serverSocket2.close();
    }
    public void createStaticWebPage() throws IOException
    {
            
                            
                            System.out.println("Website hosted on port "+port);
                        
                            
                            while(true){
                            
                
                                try(Socket client2 = serverSocket2.accept())
                                {
                                   // System.out.println("Debug: got new message "+client2.toString());
                
                                    StringBuilder request2 = new StringBuilder();
                
                                    System.out.println("--REQUEST--");
                                    System.out.println(request2);

                                        // Write the HTML content to the output stream
                                    OutputStream clientOutput2 = client2.getOutputStream();

                                    clientOutput2.write(("HTTP/1.1 200 OK\r\n").getBytes());
                                    clientOutput2.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                  
                                    clientOutput2.write(body.getBytes());
                                    clientOutput2.write(("\r\n").getBytes());
                                    clientOutput2.flush();

                                    // Write the HTML content to the output stream
                                    // clientOutput2 = client2.getOutputStream();

                                    // clientOutput2.write(("HTTP/1.1 200 OK\r\n").getBytes());
                                    // clientOutput2.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                  
                                    // clientOutput2.write(body.getBytes());
                                    // clientOutput2.write(("\r\n").getBytes());
                                    // clientOutput2.flush();
                                                  
                                   // client2.close();
                
                                }
                            }
                            
                       
    }

    @Override
    public void run() {
      try {
        createStaticWebPage();
    } catch (IOException e) {
      
        e.printStackTrace();
    }
      //  throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    public void updateStaticWebpage(String body2) throws IOException{
        // TODO Auto-generated method stub
        // System.out.println("poop");
        // System.out.println(body2);
        // System.out.println(getPort());
        // clientOutput2.write(("HTTP/1.1 200 OK\r\n").getBytes());
        // clientOutput2.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
        
        // clientOutput2.write(body2.getBytes());
        // clientOutput2.write(("\r\n").getBytes());
        // clientOutput2.flush();
        
      //  clientOutput2.findResource4(body2);
    }
    
}
