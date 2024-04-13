// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StaticWebPage implements Runnable{

    private int port;
    private String body;
    //private static Response clientOutput2;
    //Robbo classes should not know about other classes unless they absolutly have to
    //YES
    
    private ServerSocket webpageSocket;
    // public StaticWebPage(String body) throws IOException
    // {
        
    //    this.body=body;
     
    //     currentPort=port;
    //     System.out.println("port "+currentPort);
    //     serverSocket2 = new ServerSocket(currentPort);
    // }
    //the boday will contain the unedited uploaded files. We will parse them in this class
    public StaticWebPage(String body, int port) throws IOException
    {
        
       this.body=body;
        this.port=port;
      
       webpageSocket = new ServerSocket(port);
    }
    public int getPort()
    {
        return port;
    }
    public void kill() throws IOException
    {
        //TO DO need to interupt the thread
        webpageSocket.close();
    }
    public void createStaticWebPage() throws IOException
    {
            
                            
                           // System.out.println("Website hosted on port "+port);
                        
                            
                            while(true){
                            
                
                                try(Socket client = webpageSocket.accept())
                                {
                                   // System.out.println("Debug: got new message "+client2.toString());
                
                                    //StringBuilder request = new StringBuilder();
                
                                    System.out.println("--REQUEST--");
                                    //System.out.println(request);

                                        // Write the HTML content to the output stream
                                    OutputStream clientOutput = client.getOutputStream();

                                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                                    clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                  
                                    clientOutput.write(body.getBytes());
                                    clientOutput.write(("\r\n").getBytes());
                                    clientOutput.flush();

                                 
                
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