
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
   
    
    public class ConnectionHandler implements Runnable  {

        private static ArrayList<StaticWebPage> webPageList = new ArrayList<>();
       //private static final String homeURL="C:\\Users\\MarkR\\OneDrive\\Documents\\GitHub\\WebServer\\WebServer-1\\src\\dashboard.html";
       // private static ArrayList<String> people= new ArrayList<>();
       private static final String homeURL= "src//dashboard//";
        private Socket socket;
        private static Database db = new Database();
        private boolean onLoad=true;
       
        public ConnectionHandler(Socket socket )
        {
            this.socket = socket;
            //db.connect();
       // db.loadPorts();
           
            
        }
        public static void main(String[] args) {
		
		db.connect();
        // db.loadPorts();
            try (//	try (//pick a port to connect and listen to.
                        ServerSocket socket = new ServerSocket(8000)) {
                            System.out.println("Listening on port:"+8000);
                            Socket client;
                            System.out.println("");
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
        @Override
        public void run() {
            if(onLoad)
            {
               
                onLoad=false;

            }
          

          
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
                    //System.out.println("--REQUEST--");
                   // System.out.println(request.getHeader());
    

    //				/*when the method in the header is a GET we retrieve the resource. 
    //				 * the GET method may have an optional body but we ignore for now 
    //				*/
                    if(request.getMethod().equals("GET"))
                    {
                        if(request.getResource().equals("/"))//the root doesnt do anything rn. Just a "welcome page"
                        {
                            response.sendResponse("200 OK",homeURL+"dashboard.html");
                            db.clearLoadedPortList();
                            for(int i=0;i<db.getLoadedPortList().size();i++)
                            {
                                StaticWebPage webpage= new StaticWebPage(db.getLoadedHTMLList().get(i),db.getLoadedCSSList().get(i),db.getLoadedJavaScriptList().get(i),db.getLoadedPortList().get(i));
                               // System.out.println(db.getLoadedHTMLList().get(i));

                                     webPageList.add(webpage);

                                     Thread t = new Thread(webpage);
                                     t.start();
                                   
          
                                    // response.sendResponse("200 OK", db.getLoadedPortList().get(i));
                            }
                           
                     
                          
                            
                        }
                        else if (request.getResource().equals("/dashboard.css")) {
                           
                          
                            response.sendResponse("200 OK",homeURL+"dashboard.css");
                            
                        }
                        else if (request.getResource().equals("/assignment-01.css")) {
                           
                          System.out.println("here");
                            response.sendResponse("200 OK");
                            
                        }
                        else if (request.getResource().equals("/dashboard.js")) {
                            
                          
                            response.sendResponse("200 OK",homeURL+"dashboard.js");
                            
                        }
                        else if (request.getResource().equals("/url")) {
                           // System.out.println("here url");
                            ArrayList<Integer> portList= new ArrayList<>();
                            for (StaticWebPage staticWebPage : webPageList) {

                                portList.add(staticWebPage.getPort());
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
                            int port=0;
                            //search database for a free port
                            port=(db.findFreePort()); 
                            //mark free port as bound
                            db.setPortBound(port);

                            //create a static webpage 
                           StaticWebPage webpage= new StaticWebPage(request.getBody(),port);
                           webPageList.add(webpage);
                           //save web documents into db
                           db.setFiles( webpage.getPort(),webpage.getHTML(),webpage.getCSS(),webpage.getJavaScript());
                           //int port=webpage.getPort();
                           Thread t = new Thread(webpage);
                           t.start();
                         

                           response.sendResponse("200 OK", port);
                         //  db.setFiles( webpage.getPort(),webpage.getHTML(),webpage.getCSS(),webpage.getJavaScript());
                          
                          // response.findResource2(port);
                        }
                    }
                    else if(request.getMethod().equals("PUT"))
                    {
                
                        if(request.getResource().matches("/update/\\d{4}"))//check the resource we are looking for
                        {
                           
                            int port = Integer.parseInt(request.getResource().split("/")[2]);
                            System.out.println(port);
                            db.setFiles(8001,"","","");
                            for (StaticWebPage webpage : webPageList) {
                                System.out.println(webpage.getPort());

                                if(webpage.getPort() == port){
                                    //staticWebPage.kill();
                                    // StaticWebPage webpage= new StaticWebPage(request.getBody(),port - 1);
                                    // webPageList.add(webpage);
            
                                    // Thread t = new Thread(webpage);
                                    // t.start();
                                    
                                    // response.sendResponse("200 OK", port);

                                    //response.sendResponseOk();
                                    //int port=0;
                                    
                                    
                                    //search database for a free port
                                    //port=(db.findFreePort()); 
                                    //mark free port as bound
                                    //db.setPortBound(port);

                                    //create a static webpage 
                                    //StaticWebPage webpage= new StaticWebPage(request.getBody(),port);
                                    //webPageList.add(webpage);
                                    //save web documents into db
                                    
                                    //int port=webpage.getPort();
                                    // Thread t = new Thread(webpage);
                                    // t.start();
                         

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
                        db.setPortFree(port);

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
    
