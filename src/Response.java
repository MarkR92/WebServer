import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Response {
    private OutputStream output;
    private final String url="http://localhost:";
    public Response(OutputStream output) 
    {
        this.output = output;
    }
    public void sendResponse(String responseCode)
    {
       
        try {
            output.write(("HTTP/1.1 "+responseCode+"\r\n").getBytes());//encode to bytes
            output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());//encode to bytes
            output.write(("\r\n").getBytes());//blank line
            output.flush();// empty the built up buffer
        } catch (IOException e) {
            System.out.print(""); 
            e.printStackTrace();
        }
     
    }
    public void sendResponse(String responseCode, String filelocation)
    {
     StringBuilder htmlContent = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(filelocation)))
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
         try {
         output.write(("HTTP/1.1 "+responseCode+"\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
         output.write(("\r\n").getBytes());//blank line
         output.write(htmlContent.toString().getBytes());
         output.flush();// empty the built up buffer
        } catch (IOException e) {
            
            e.printStackTrace();
        }

    }
    public void sendResponse(String responseCode, int port)
    {
    
         try {
         output.write(("HTTP/1.1 "+responseCode+"\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
         output.write(("\r\n").getBytes());//blank line

         output.write(("url:localhost:"+port).getBytes());//blank line
         output.flush();// empty the built up buffer
        } catch (IOException e) {
            
            e.printStackTrace();
        }

    }
    public void sendResponse(String responseCode, ArrayList<Integer> portList)
    {
    
         try {
         output.write(("HTTP/1.1 "+responseCode+"\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());//encode to bytes
         output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
         output.write(("\r\n").getBytes());//blank line
            for (Integer port : portList) {
                String ref = url+port;
                // String target="_blank";
                // String rel="noopener noreferrer";
               // <p>Check out <a href="https://www.freecodecamp.org/" target="_blank" rel="noopener noreferrer">freeCodeCamp</a>.</p>
               output.write(("<a href="+ref+">"+ref+"</a>"+"<button style='font-size:20px;'class='delete' onclick='test("+port+")'>x</button>").getBytes());//blank line
               //output.write(("<a href="+ref+"target="+target+" rel="+rel+">"+ref+"</a>"+"<button style='font-size:20px;'class='delete' onclick='test("+port+")'>x</button>").getBytes());//blank line

               // output.write(("<a href="+ref+" target=_blank></a>"+"<button style='font-size:20px;'class='delete' onclick='test("+port+")'>x</button>").getBytes());//blank line
                output.write(("\r\n").getBytes());//blank line
            }
        
         output.flush();// empty the built up buffer
        } catch (IOException e) {
            
            e.printStackTrace();
        }

    }
    // public void sendResponseOk() throws IOException
    // {
    //     output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
    //     output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
    //     output.write(("\r\n").getBytes());//blank line
    //     output.flush();//empty the built up buffer
    // }
    // public void sendResponseNotFound() throws IOException
    // {
    //     output.write(("HTTP/1.1 404 Not Found\r\n").getBytes());//encode to bytes
    //     output.write(("\r\n").getBytes());//blank line
    //     output.flush();//empty the built up buffer
    // }
    
    // public void findResource() throws IOException
    // {
    //  StringBuilder htmlContent = new StringBuilder();
    //     try(BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Robert\\Documents\\CS335\\Java\\WebServer\\src\\upload.html")))
    //      {
    //         String htmlLine;
    //         while((htmlLine = reader.readLine()) != null)
    //          {
    //         htmlContent.append(htmlLine).append("\n");
    //          }
    //      } catch (IOException e)
    //      {
    //         e.printStackTrace();
    //      }

    //   output.write(("HTTP/1.1 200 OK\r\n").getBytes());
    //   output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
    //   output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
    //   output.write(("\r\n").getBytes());
    //   output.write(htmlContent.toString().getBytes());
    //   output.flush();
    // }
//    public void findResource2(int port) throws IOException{

//         output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
//         output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
//         output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
//         output.write(("\r\n").getBytes());//blank line
//         output.write(("<h1>url:localhost:"+port+"</h1>").getBytes());//blank line
//         output.flush();

//     } 
//    public void findResource3(String body) throws IOException{
     
//         output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
//         output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
//         output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());


//         output.write(body.getBytes());
//         output.write(("\r\n").getBytes());
//         output.flush();
 

//     } 
//    public void findResource4(String body) throws IOException{
     
//     output.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
//     output.write(("\r\n").getBytes());//blank line
//     output.flush();
//     } 

  
    
}