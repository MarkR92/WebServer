import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream output;

    public Response(OutputStream output) 
    {
        this.output = output;
    }

    public void sendResponseOk() throws IOException
    {
        output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
        output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
        output.write(("\r\n").getBytes());//blank line
        output.flush();//empty the built up buffer
    }
    public void sendResponseNotFound() throws IOException
    {
        output.write(("HTTP/1.1 404 Not Found\r\n").getBytes());//encode to bytes
        output.write(("\r\n").getBytes());//blank line
        output.flush();//empty the built up buffer
    }
    
    public void findResource() throws IOException
    {
     StringBuilder htmlContent = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\rootadmin\\Desktop\\Web Server Folder\\WebServer\\src\\upload.html"))) 
         {
            String htmlLine;
            while((htmlLine = reader.readLine()) != null)
             {
            htmlContent.append(htmlLine).append("\n");
             }
         } catch (IOException e)
         {
            e.printStackTrace();
         }

      output.write(("HTTP/1.1 200 OK\r\n").getBytes());
      output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
      output.write(("\r\n").getBytes());
      output.write(htmlContent.toString().getBytes());
      output.flush();
    }
   public void findResource2(int port) throws IOException{

        output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
        output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
        output.write(("\r\n").getBytes());//blank line
        output.write(("<h1>url:20.84.89.246:"+port+"</h1>").getBytes());//blank line
        output.flush();
    }
  
    
}
