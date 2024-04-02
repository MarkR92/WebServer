
    import java.io.BufferedReader;

import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;

import java.net.Socket;
    import java.util.ArrayList;
    //import java.util.regex.Matcher;
    //import java.util.regex.Pattern;
    
    public class ConnectionHandler implements Runnable  {
    
        private static ArrayList<String> people= new ArrayList<>();
        private Socket socket;

        public ConnectionHandler(Socket socket )
        {
            this.socket = socket;
            
        }
        @Override
        public void run() {
            
             BufferedReader in = null;
             OutputStream clientOutput = null;
             
             try
             {
                  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//read in all the inputs on the socket and build a buffer
                  Request request = new Request(in);//create a new request from the buffer
                  request.readHeader();//each HTTP request has a header

                  clientOutput = socket.getOutputStream();

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
                        if(request.getResource().equals("/people"))//check the resource we are looking for
                        {
                           
                            response.sendResponseOk();
                            //people are contained in an arraylist. So when queried we output everyone in the list
                            for(int i=0;i<people.size();i++)
                            {
                                clientOutput.write((people.get(i)+" \r\n").getBytes());//encode to bytes
                                
                            }
                                           
                            clientOutput.flush();//empty the built up buffer
                        }
                        //I did something here
                        else if(request.getResource().equals("/"))//the root doesnt do anything rn. Just a "welcome page"
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
                           
                           StaticWebPage webpage= new StaticWebPage();
                          
                           webpage.createStaticWebPage(request.getBody());  
                        }
               
                    
                    



    //                     if(resource.equals("/people"))
    //                     {
    //                        
                            
    //                        
    //                         Pattern pattern2 = Pattern.compile("person\\d=([^&]+)");
    
    //                         // Create a matcher with the input string
    //                         Matcher matcher2 = pattern2.matcher(body);
    
    //                         // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
    //                         while (matcher2.find()) {
    //                             String name = matcher2.group(1);
    //                             System.out.println(name);
    //                             people.add(name);
    //                             people.size();
    //                         }
    //                         //OutputStream clientOutput=socket.getOutputStream();//output all responses
    //                         clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
    //                         clientOutput.write(("\r\n").getBytes());//blank line
                            
                            
    //                         clientOutput.flush();//empty the built up buffer
            
                  
                    
    //                 }
    //                 else if(resource.equals("/login")) {
					
    //                     int contentLength=0;
                        
    //                     Pattern pattern = Pattern.compile("Content-Length: (\\d+)");
    //                     Matcher matcher = pattern.matcher(header);
    
    //                     // Check if the pattern is found
    //                     if (matcher.find()) {
                          
    //                         contentLength= Integer.parseInt(matcher.group(1));
                           
    //                     } else {
    //                         System.out.println("Content-Length not found in the headers.");
    //                     }
                        
    //                     String body="";
                    
    //                     while (contentLength > 0)  {
                            
                        
    //                         contentLength--;
                            
    //                         body+=""+(char)in.read();//check the next char
                            
    //                         //System.out.println(body);
    //                     }
    //                     //System.out.println(body+"out");
    //                      pattern = Pattern.compile("username=([^&]+)&password=([^&]+)");
    
    //                     // Create a matcher with the input string
    //                      matcher = pattern.matcher(body);
    //                      String username="";
    //                      String pwd ="";
    //                     // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
    //                     while (matcher.find()) {
    //                         username = matcher.group(1);
    //                         //System.out.println(username);
    //                         pwd = matcher.group(2);
    //                        // System.out.println(pwd);
                            
    //                     }
    //                     if(username.equals("admin")&pwd.equals("admin"))
    //                     {
    //                         //System.out.println("here");

    //                         // StringBuilder htmlContent = new StringBuilder();
    //                         // try(BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\MarkR\\OneDrive\\Documents\\GitHub\\WebServer\\WebServer-1\\src\\simon.html"))) 
    //                         // {
    //                         //     String htmlLine;
    //                         //     while((htmlLine = reader.readLine()) != null)
    //                         //     {
    //                         //         htmlContent.append(htmlLine).append("\n");
    //                         //     }
    //                         // } catch (IOException e)
    //                         // {
    //                         //     e.printStackTrace();
    //                         // }
    //                     // Write the HTML content to the output stream
    //                     clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
    //                     clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
    //                     clientOutput.write(("\r\n").getBytes());
    //                    // clientOutput.write(htmlContent.toString().getBytes());
    //                     clientOutput.flush();
                            
                            
    //                     }
    //                     else
    //                     {
    //                         //OutputStream clientOutput=socket.getOutputStream();//output all responses
    //                         clientOutput.write(("HTTP/1.1 401 OK\r\n").getBytes());//encode to bytes
    //                         clientOutput.write(("Login for "+username+" failed. Invalid user name or password\r\n").getBytes());//encode to bytes
    //                         clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
    //                         clientOutput.write(("\r\n").getBytes());//blank line
                            
                            
    //                         clientOutput.flush();//empty the built up buffer
    //                     }
                    
    //                 }
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
    

