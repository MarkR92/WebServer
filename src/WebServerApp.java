
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
   
    
    public class WebServerApp implements Runnable  {

        private static ArrayList<StaticWebPage> webPageList = new ArrayList<>();
       // private static final String homeURL= "src//dashboard//";
        private static final String homeURL= "src//dashboard_rob//";
        private Socket socket;
        private static Database  db = new Database();
        String css;
        String html;
        String js;
       
       
        public WebServerApp(Socket socket )
        {
            this.socket = socket;
      
           
            
        }
        public static void main(String[] args) {
		 //connect to db
        onStartUp();
            try (//	try (//pick a port to connect and listen to.
                        ServerSocket socket = new ServerSocket(8000)) {
                            System.out.println("Listening on port:"+8000);
                            Socket client;
                            System.out.println("");
                             while((client=socket.accept())!=null)//while connected (forever)
                            {
                                     System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
                                     WebServerApp handler = new WebServerApp(client);
                                     Thread t = new Thread(handler);
                                     t.start();
    
                            }
                        } catch (IOException e) {
                        
                            e.printStackTrace();
                        }

                       


                        }

        public static void onStartUp()
        {
            //connect to db
           // db = new Database();
            //load all ports in db
           // System.out.println(db.getLoadedPortList().size());
            for(int i=0;i<db.getLoadedPortList().size();i++)
                {
                    //create a webpage for every port found in db
                  
                    StaticWebPage webpage;
                    try {
                        webpage = new StaticWebPage(db.getLoadedHTMLList().get(i),db.getLoadedCSSList().get(i),db.getLoadedJavaScriptList().get(i),db.getLoadedPortList().get(i));
                        webPageList.add(webpage);
                       
                        Thread t = new Thread(webpage);
                        t.start();
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
                  
                }

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
                           db.loadPorts() ;
                           onStartUp();
                            
                           
                     
                          
                            
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
                            response.sendResponse("200 OK",portList,0);
                            
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
                         
                           ArrayList<Integer> portList= new ArrayList<>();
                           for (StaticWebPage staticWebPage : webPageList) {

                               portList.add(staticWebPage.getPort());
                           }
                           response.sendResponse("200 OK", portList,0);
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

                            for (StaticWebPage staticWebPage : webPageList) {
                                System.out.println(staticWebPage.getPort());

                                if(staticWebPage.getPort() == port){


                                    parseFiles(request.getBody());

                                  
                                    staticWebPage.setHTML(html);
                                    staticWebPage.setCSS(css);
                                    staticWebPage.setJavaScript(js);
                                    //save web documents into db
                                    db.setFiles(port,html,css,js);
                                    //int port=webpage.getPort();
        
                                    
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
                                    break;
                                    
                                    // System.out.println("Website deleted on port "+port);
                                   // response.sendResponse("200 OK");
                                }
                       // response.sendResponse("200 OK");
                            }
                            ArrayList<Integer> portList= new ArrayList<>();
                            for (StaticWebPage staticWebPage : webPageList) {

                                portList.add(staticWebPage.getPort());
                            }
                            response.sendResponse("200 OK" ,portList,0);
                        // for (StaticWebPage staticWebPage : webPageList) {
                        //     System.out.println(staticWebPage.getPort());

                            // if(staticWebPage.getPort() == port){
                                
                            //     // staticWebPage.kill();

                            //     // webPageList.remove(staticWebPage);
                                
                            //     // System.out.println("Website deleted on port "+port);
                            //     response.sendResponse("200 OK");
                            // }
                            // else{
                            //     response.sendResponse("404 Not Found");
                            // }
                       // }     
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
    public void parseFiles(String body)
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
}
    
