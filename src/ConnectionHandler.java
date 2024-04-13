
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
    import java.io.InputStreamReader;
  

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
    //import java.util.regex.Matcher;
    //import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
    
    public class ConnectionHandler implements Runnable  {

        private static ArrayList<StaticWebPage> webPageList = new ArrayList<>();
        private Database db=new Database();
        //private static final String homeURL="C:\\Users\\MarkR\\OneDrive\\Documents\\GitHub\\WebServer\\WebServer-1\\src\\dashboard.html";
       // private static ArrayList<String> people= new ArrayList<>();
       private static final String homeURL= "\\Users\\rootadmin\\Documents\\WebServer-1\\WebServer\\src\\dashboard\\dashboard.html";
        private Socket socket;

        public ConnectionHandler(Socket socket )
        {
            this.socket = socket;
            
        }
        @Override
        public void run() {
            
             BufferedReader in = null;
          //   OutputStream clientOutput = null;
             
             try
             {
                  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//read in all the inputs on the socket and build a buffer
                  Request request = new Request(in);//create a new request from the buffer
                  request.readHeader();//each HTTP request has a header

                 // clientOutput = socket.getOutputStream();

                  Response response= new Response(socket.getOutputStream());
                    /*the first part of the response is the header. the first line of the header contains info such 
                     HTTP method (GET,POST,PUT...etc) and resource and resource root (/people of /people/jobs ...etc)
                      */
                    System.out.println();
                    System.out.println("--REQUEST--");
                    System.out.println(request.getHeader());
    

    //				/*when the method in the header is a GET we retrieve the resource. 
    //				 * the GET method may have an optional body but we ignore for now 
    //				*/
                    if(request.getMethod().equals("GET"))
                    {
                        if(request.getResource().equals("/"))//the root doesnt do anything rn. Just a "welcome page"
                        {
                            response.sendResponse("200 OK",homeURL);
                            // response.sendResponse("200 OK","\\Users\\MarkR\\OneDrive\\Desktop\\dashboard\\dashboard.css");
                            
                            // response.sendResponse("200 OK","\\Users\\MarkR\\OneDrive\\Desktop\\dashboard\\dashboard.js");
                            
                        }
                        else if (request.getResource().equals("/dashboard.css")) {
                           
                          
                            response.sendResponse("200 OK","\\Users\\rootadmin\\Documents\\WebServer-1\\WebServer\\src\\dashboard\\dashboard.css");
                            
                        }
                        else if (request.getResource().equals("/dashboard.js")) {
                            
                          
                            response.sendResponse("200 OK","\\Users\\rootadmin\\Documents\\WebServer-1\\WebServer\\src\\dashboard\\dashboard.js");
                            
                        }
                        else if (request.getResource().equals("/url")) {
                            System.out.println("here url");
                            ArrayList<Integer> portList= new ArrayList<>();
                            for (StaticWebPage staticWebPage : webPageList) {

                                portList.add(staticWebPage.getPort());
                                System.out.println("//////////////////////"+staticWebPage.getPort());
                            }
                            response.sendResponse("200 OK",portList);
                            
                        }
                        else {
                   
                           // response.sendResponseNotFound();
                            response.sendResponse("404 Not Found");
                        }
                        
                    }
    //				/*if the method is post we need to get the info stored in the body. The body comes after the header. W
    //				 * we need to check the content length to know when we are done and we need to check content type so we know 
    //				 * which parser to use. currently only Content-Type: application/x-www-form-urlencoded . is supported but need to look into this more*/
                    else if(request.getMethod().equals("POST"))
                    {
                        if(request.getResource().equals("/upload"))//check the resource we are looking for
                        {
                            //response.sendResponseOk();
/////////rob
 //response.sendResponseOk();
                            //System.out.println(request.getBody());
                            //
                            String[] content = request.getBody().split("------WebKitFormBoundary");
                            Pattern filePattern = Pattern.compile("filename=\".*\\/([^\"]+)");
                            Pattern contentPattern = Pattern.compile("(Content-Type.*\\/.*)");

                            //System.out.println(content[0]);
                            //System.out.println(content.length);
                            int port=0;
                           
                               port=(db.findFreePort()); 
                              System.out.println(port);

                          
                            // int port = 8004;
                            
                            
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

                                //System.out.println(file);
                                Files.createDirectories(Paths.get("C:\\Users\\rootadmin\\Desktop\\Webpage\\htmlFiles\\"+port));
                                File myObj = new File("C:\\Users\\rootadmin\\Desktop\\Webpage\\htmlFiles\\"+port+"\\"+file);
                                //File folder = new File("C:\\Users\\Robert\\Documents\\CS335\\Java\\WebServer\\htmlFiles\\"+port).mkdir();

                                try {
                                    FileWriter myWriter = new FileWriter(myObj.getAbsolutePath());
                                    myWriter.write(content[i]);
                                    myWriter.close();
                                    System.out.println("Successfully wrote to the file.");
                                  } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                  }
                            }
                            //////rob
                            
                           StaticWebPage webpage= new StaticWebPage(request.getBody(),port);
                           webPageList.add(webpage);

                           //port=webpage.getPort();
                           Thread t = new Thread(webpage);
                           t.start();
                           
                           response.sendResponse("200 OK", port);
                          // response.findResource2(port);
                        }
                    }
                    else if(request.getMethod().equals("PUT"))
                    {
                
                        if(request.getResource().matches("/update/\\d{4}"))//check the resource we are looking for
                        {
                           
                            int port = Integer.parseInt(request.getResource().split("/")[2]);
                            System.out.println(port);

                            for (StaticWebPage staticWebPage : webPageList) {
                                System.out.println(staticWebPage.getPort());

                                if(staticWebPage.getPort() == port){
                                    staticWebPage.kill();
                                    StaticWebPage webpage= new StaticWebPage(request.getBody(),port - 1);
                                    webPageList.add(webpage);
            
                                    Thread t = new Thread(webpage);
                                    t.start();
                                    
                                    response.sendResponse("200 OK", port);
                                }
                            }     
                        }else{
                            response.sendResponse("404 Not Found");
                        }

                    }
                    else if(request.getMethod().equals("DELETE"))
                    {
                        if(request.getResource().matches("/delete/\\d{4}"))//check the resource we are looking for
                        {
                        int port = Integer.parseInt(request.getResource().split("/")[2]);
                        //System.out.println(port);

                        for (StaticWebPage staticWebPage : webPageList) {
                            System.out.println(staticWebPage.getPort());

                            if(staticWebPage.getPort() == port){
                                staticWebPage.kill();
                                webPageList.remove(staticWebPage);
                                
                                System.out.println("Website deleted on port "+port);
                                response.sendResponse("200 OK");
                            }
                            else{
                                response.sendResponse("404 Not Found");
                            }
                        }     
                    }                              
                   
                } else{
                    response.sendResponse("404 Not Found");  
                }
                
                socket.close();//do not remove this 

                    //get the first line of the request
                    //String firstLine=header.toString().split("\n")[0];
                  
             }
             catch (IOException e) 
             {   
                         
                 
             }  
    
    }
    }
    
