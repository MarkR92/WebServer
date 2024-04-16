
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StaticWebPage implements Runnable{

    private int port;
    private String body;
    private String html,css,js;
    private ServerSocket webpageSocket;
  
    //the boday will contain the unedited uploaded files. We will parse them in this class
    public StaticWebPage(String body, int port) 
    {
        
        this.body=body;
        this.port=port;
        html="";
        css="";
        js="";
        parseFiles();
        try {
            webpageSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public StaticWebPage(String html,String css,String js ,int port) throws IOException
    {
        
      
        this.port=port;
        this.html=html;
        this.css=css;
        this.js=js;
        // html="";
        // css="";
        // js="";
        // parseFiles();
        webpageSocket = new ServerSocket(port);
    }
    public int getPort()
    {
        return port;
    }
    public void setPort(int port)
    {
        this.port=port;
    }
    public String getHTML()
    {
        return html;

    }
    public void setHTML(String html)
    {
        this.html=html;
    }
    public String getCSS()
    {
        return css;

    }
    public void setCSS(String css)
    {
        this.css=css;
    }
    public String getJavaScript()
    {
        return js;

    }
    public void setJavaScript(String js)
    {
        this.js=js;
    }

    public void parseFiles()
    {
        String[] content =body.split("------WebKitFormBoundary");
        Pattern filePattern = Pattern.compile("filename=\".*\\/([^\"]+)");
        Pattern contentPattern = Pattern.compile("(Content-Type.*\\/.*)");
        
        
        for(int i = 1; i< content.length - 1; i++){
            //System.out.println(content[i]);
            String file = "";
            String split = "";
            Matcher matcher = filePattern.matcher(content[i]);
            while(matcher.find() == true)
            {
                    file = matcher.group(1);
            }
            matcher = contentPattern.matcher(content[i]);
            while(matcher.find() == true)
            {
                    split = matcher.group(1);
            }

            content[i] = content[i].split(split)[1]; 

            if(file.matches("^.*\\.css$"))
            {
                css=content[i];
                
            }else if(file.matches("^.*\\.html$"))
            {
                html=content[i];
                //System.out.println(html);
            }
            else if(file.matches("^.*\\.js$"))
            {
                js=content[i];
            }
           

        }

    }
    public void kill() throws IOException
    {
        //TODO need to interupt the thread
        webpageSocket.close();
    }
    public void createStaticWebPage() throws IOException
    {
            
                            
                            while(true){
                            
                              
                                try(Socket client = webpageSocket.accept())
                                {
                                    BufferedReader in = null;

                                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));//read in all the inputs on the socket and build a buffer
                                    Request request = new Request(in);//create a new request from the buffer
                                    request.readHeader();//each HTTP request has a header
                                   // System.out.println("Debug: got new message "+client2.toString());
                
                                    //StringBuilder request = new StringBuilder();
                
                                    System.out.println("--WebREQUEST--");
                                    System.out.println(request.getHeader());

                                    if(request.getMethod().equals("GET"))
                                    {
                                        if(request.getResource().equals("/"))//the root doesnt do anything rn. Just a "welcome page"
                                        {
                                            client.getOutputStream().write(("HTTP/1.1 200 OK\r\n").getBytes());
                                            client.getOutputStream().write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                            client.getOutputStream().write(html.getBytes());
                                            client.getOutputStream().write(("\r\n").getBytes());
                                            client.getOutputStream().flush();
                                          
                                            
                                        }
                                        else if(request.getResource().matches("^.*\\.css$"))//the root doesnt do anything rn. Just a "welcome page"
                                        {
                                            client.getOutputStream().write(("HTTP/1.1 200 OK\r\n").getBytes());
                                            client.getOutputStream().write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                            client.getOutputStream().write(css.getBytes());
                                            client.getOutputStream().write(("\r\n").getBytes());
                                            client.getOutputStream().flush();
                                          
                                            
                                        }
                                        else if(request.getResource().matches("^.*\\.js$"))//the root doesnt do anything rn. Just a "welcome page"
                                        {
                                            client.getOutputStream().write(("HTTP/1.1 200 OK\r\n").getBytes());
                                            client.getOutputStream().write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                                            client.getOutputStream().write(js.getBytes());
                                            client.getOutputStream().write(("\r\n").getBytes());
                                            client.getOutputStream().flush();
                                        
                                            
                                        }
                                    }
                                
                                 
                
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

    
}