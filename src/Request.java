import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Request {
    
    private BufferedReader reader;
    private  StringBuilder header;

    public Request(BufferedReader reader)
    {
        this.reader=reader;
        this.header=new StringBuilder();
        
    }
    public void readHeader()
    {
     
     
        try {
            String line="";//temp holding one line of our message
            line = reader.readLine();//gives us the first line
             if(!line.isEmpty()) {
                 while(!line.isBlank())//while the line is not blank continue to read headers end with a blank line
                 {
                  header.append(line+"\r\n");//add line to request
                   line=reader.readLine();//check the next line
                
                 }
        }
        } catch (Exception e) {
            e.printStackTrace();
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
    public int getContentLength()
    {
        int contentLength=0;
                                //parse out content length using the much beloved regex
            // Define the pattern to match "Content-Length: value"
        Pattern pattern = Pattern.compile("Content-Length: (\\d+)");

        // Create a matcher with the input headers
        Matcher matcher = pattern.matcher(header);

        // Check if the pattern is found
        if (matcher.find()) {
            // Get the matched content length value
            contentLength= Integer.parseInt(matcher.group(1));
            System.out.println("Content-Length: " + contentLength);
        } else {
            System.out.println("Content-Length not found in the headers.");
        }
        return contentLength;
    }
    public String getBody()
    {
        String body="";
        int contentLength=getContentLength();
        //System.out.println("h");
//						int bytesRemaining = 38;
        
        //while we still have content to take in to build the body
        while (contentLength > 0)  {
            
        
            contentLength--;
            
            try {
                body+=""+(char)reader.read();
            } catch (IOException e) {
               
                e.printStackTrace();
            }//check the next char
            
            //System.out.println(body);
        }
       // System.out.println(body+"out");


        // //Split the input into lines
        // String[] lines = body.split("\n");

        // // Exclude the first 3 lines and the last line
        // String[] result = Arrays.copyOfRange(lines, 3, lines.length - 1);

        // // Join the lines back into a single string
        // String output = String.join("\n", result);

         //System.out.println(body);
        return body;
    }

 


}