
    import java.io.BufferedReader;

import java.io.IOException;
    import java.io.InputStreamReader;
  

import java.net.Socket;
    import java.util.ArrayList;
    //import java.util.regex.Matcher;
    //import java.util.regex.Pattern;
    
    public class ConnectionHandler implements Runnable  {

        private static ArrayList<StaticWebPage> webPageList = new ArrayList<>();
    
       // private static ArrayList<String> people= new ArrayList<>();
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
                            response.findResource();
                        }

                   

                        else {
                   
                            response.sendResponseNotFound();
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

                           
                            
                           StaticWebPage webpage= new StaticWebPage(request.getBody());
                           webPageList.add(webpage);

                           int port=webpage.getPort();
                           Thread t = new Thread(webpage);
                           t.start();
                           
                           response.findResource2(port);
                        }

                    }
                    else if(request.getMethod().equals("PUT"))
                    {
                        if(request.getResource().equals("/update"))//check the resource we are looking for
                        {
                            // System.out.println("hello!!!!!!");
                            // request.getBody();

                            for (StaticWebPage staticWebPage : webPageList) {
                                System.out.println(staticWebPage.getPort());

                                if(staticWebPage.getPort() == 9001){
                                    staticWebPage.kill();
                                    StaticWebPage webpage= new StaticWebPage(request.getBody(),9000);
                                    webPageList.add(webpage);
         
                                    int port=9000;
                                    Thread t = new Thread(webpage);
                                    t.start();
                                    
                                    response.findResource2(port+1);
                                }
                            }
                            

                            //response.sendResponseOk();

                           //Port 8001

                           //Update exisitng webpage
                           //Pass in port and new html file
                           //Search static webpage arraylist for port
                           //webpage.getPort()
                           //webpage.updateStaticWebpage();

                            // .write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
                            // output.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                            // output.write(("Access-Control-Allow-Headers: *\r\n").getBytes());
                            // output.write(("\r\n").getBytes());//blank line
                            // output.write(("<h1>url:localhost:"+port+"</h1>").getBytes());//blank line
                            // output.flush();
                        }

                    }
    //
                    
                    
                    
                    socket.close();
                    //get the first line of the request
                    //String firstLine=header.toString().split("\n")[0];
                  
             }
             catch (IOException e) 
             {              
                 
             }  
    
    }
    }
    

