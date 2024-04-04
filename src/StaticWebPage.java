// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StaticWebPage implements Runnable{

    private static int port=9000;
    private String body;
    private static Response clientOutput2;

    public StaticWebPage(String body)
    {
        
       this.body=body;
        port++;
        System.out.println("port "+port);
    }
    public int getPort()
    {
        return port;
    }
    public void createStaticWebPage() throws IOException
    {
            try (ServerSocket serverSocket2 = new ServerSocket(port))
                        {
                            
                            System.out.println("Website hosted on port "+port);
                        
                            
                            while(true){
                            
                
                                try(Socket client2 = serverSocket2.accept())
                                {
                                   // System.out.println("Debug: got new message "+client2.toString());
                
                                    StringBuilder request2 = new StringBuilder();
                
                                    System.out.println("--REQUEST--");
                                    System.out.println(request2);

                                    clientOutput2 = new Response(client2.getOutputStream());
                                    clientOutput2.findResource3(body);

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
                        catch (IOException e) 
                        {              
                            System.out.println(e);
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
        
        clientOutput2.findResource4(body2);
    }
    
}
