import java.io.BufferedReader;
import java.io.IOException;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.Headers;

public class Request implements HttpHandler {
    
    private BufferedReader reader;
    private  StringBuilder header;

    public Request(BufferedReader reader)
    {
        this.reader=reader;
        this.header=new StringBuilder();
        
    }
    public void readHeader() throws IOException
    {
     
     
                    
        String line="";//temp holding one line of our message
        line = reader.readLine();//gives us the first line
         if(!line.isEmpty()) {
             while(!line.isBlank())//while the line is not blank continue to read headers end with a blank line
             {
              header.append(line+"\r\n");//add line to request
               line=reader.readLine();//check the next line
            
             }
    }

    }
    public StringBuilder getHeader()
    {
        return header;
    }
    public String getMethod()
    {
        //Decide how to responed to request
        String firstLine=header.toString().split("\n")[0];
        String method=firstLine.split(" ")[0];

        return method;
    }
    public String getResource()
    {
        String firstLine=header.toString().split("\n")[0];
        String resource=firstLine.split(" ")[1];

        return resource;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        //TODO get httpexhange working
      //  Headers headers= exchange.getRequestHeaders();

        //System.out.println(headers.toString());
       // throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }


}
