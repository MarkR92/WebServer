import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static ArrayList<String> people= new ArrayList<>();
	private static ArrayList<String> jobs= new ArrayList<>();
	
	/*
	 * use windows command line curl command to send requests
	 * postman and chrome browser error sometimes for post. needs to be investigated
	 * 
	 * curl examples
	 * GET:
	 * curl localhost:8097/people 
	 * curl localhost:8097/people -v 
	 * POST:
	 * curl -d "person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian" http://localhost:8097/people 
	 * curl.exe -d "person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian" localhost:8097/people 
	 * 
	 * curl -d "job1=mime&job2=janitor&job3=gangam styler&job4=krusty krab chef&job5=bee keeper" http://localhost:8097/people/jobs
	 *  
	 * curl.exe -X PUT -d "person1=bark&person2=rob&person3=khate&person4=dan&person5=dorian" localhost:8097/people
	 * 
	 */
	public static void main(String[] args) {
		
		try (//pick a port to connect and listen to.
		ServerSocket socket = new ServerSocket(8097)) {
			System.out.println("Listening");
			
			while(true)//while connected (forever)
			{
				Socket client=socket.accept();// launch in thread to make concurrent. Needs invest
		
			
				//read all incoming messages
				InputStreamReader input=new InputStreamReader(client.getInputStream());
				//buffer these incoming messages
				BufferedReader buff= new BufferedReader(input);
				//store the incoming messages
				StringBuilder header=new StringBuilder();
				
				String line="";//temp holding one line of our message
				line = buff.readLine();//gives us the first line
			
				while(!line.isBlank())//while the line is not blank continue to read headers end with a blank line
				{
					header.append(line+"\r\n");//add line to request
					line=buff.readLine();//check the next line
					
				}
				/*the first part of the response is the header. the first line of the header contains info such 
				 HTTP method (GET,POST,PUT...etc) and resource and resource root (/people of /people/jobs ...etc)
				  */
				System.out.println();
				System.out.println("--REQUEST--");
				
				System.out.println(header);
				
				
				//Decide how to responed to request
				
				
				
				
				
				//get the first line of the request
				String firstLine=header.toString().split("\n")[0];
				
				/*
				 * 
				 *  GET 
				 *  /people 
				 *  HTTP/1.1
				
				 */
				//System.out.println(firstLine);
				//get the second element from first line
				String resource=firstLine.split(" ")[1];
				String method=firstLine.split(" ")[0];
				
				System.out.println(firstLine+" fline");
				System.out.println(resource+" rline");

				//compare the element to our list of things
				//send back apprpiate thing
				
				/*when the method in the header is a GET we retrieve the resource. 
				 * the GET method may have an optional body but we ignore for now 
				*/
				if(method.equalsIgnoreCase("GET"))
				{
					if(resource.equals("/people"))//check the resource we are looking for
					{
						OutputStream clientOutput=client.getOutputStream();//output all responses
						
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						//people are contained in an arraylist. So when queried we output everyone in the list
						for(int i=0;i<people.size();i++)
						{
							clientOutput.write((people.get(i)+" \r\n").getBytes());//encode to bytes
							
						}
				
						
						clientOutput.flush();//empty the built up buffer
					}
					else if(resource.equals("/people/jobs"))//check the resource we are looking for
					{
						OutputStream clientOutput=client.getOutputStream();//output all responses
						
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						//people are contained in an arraylist. So when queried we output everyone in the list
						for(int i=0;i<jobs.size();i++)
						{
							clientOutput.write((jobs.get(i)+" \r\n").getBytes());//encode to bytes
							
						}
				
						
						clientOutput.flush();//empty the built up buffer
					}
					else if(resource.equals("/"))//the root doesnt do anything rn. Just a "welcome page"
					{
						OutputStream clientOutput=client.getOutputStream();//output all responses
						
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						clientOutput.write(("<button>Hello<button>").getBytes());//encode to bytes
						
						clientOutput.flush();//empty the built up buffer
						
					}
					else {
						//if the resource is not found output 404
						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 404 Not Found\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						clientOutput.write(("404").getBytes());//encode to bytes
						clientOutput.flush();//empty the built up buffer
					}
					
				}
				/*if the method is post we need to get the info stored in the body. The body comes after the header. W
				 * we need to check the content length to know when we are done and we need to check content type so we know 
				 * which parser to use. currently only Content-Type: application/x-www-form-urlencoded . is supported but need to look into this more*/
				else if(method.equals("POST"))
				{
									
					if(resource.equals("/people"))
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
				        
						String body="";
						//System.out.println("h");
//						int bytesRemaining = 38;
						
						//while we still have content to take in to build the body
						while (contentLength > 0)  {
							
						
							contentLength--;
							
							body+=""+(char)buff.read();//check the next char
							
							
						}
						
						Pattern pattern2 = Pattern.compile("person\\d=([^&]+)");

				        // Create a matcher with the input string
				        Matcher matcher2 = pattern2.matcher(body);

				        // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
				        while (matcher2.find()) {
				            String name = matcher2.group(1);
				            people.add(name);
				        }
						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						
						
						clientOutput.flush();//empty the built up buffer
						 //
					
					
							
							
						
					}
					else if(resource.equals("/people/jobs"))
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
				        
						String body="";
						//System.out.println("h");
//						int bytesRemaining = 38;
						
						//while we still have content to take in to build the body
						while (contentLength > 0)  {
							
						
							contentLength--;
							
							body+=""+(char)buff.read();//check the next char
							
							
						}
						
						Pattern pattern2 = Pattern.compile("job\\d=([^&]+)");

				        // Create a matcher with the input string
				        Matcher matcher2 = pattern2.matcher(body);

				        // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
				        while (matcher2.find()) {
				            String name = matcher2.group(1);
				            jobs.add(name);
				        }
						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						
						
						clientOutput.flush();//empty the built up buffer
						 //
					
					
							
							
						
					}

				}
				else if(method.equalsIgnoreCase("DELETE"))
				{
					if(resource.equals("/people"))
					{
						people.clear();//TO DO

						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						clientOutput.flush();//empty the built up buffer
					}
					else if(resource.equals("/people/jobs"))
					{
						people.clear();//TO DO

						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						clientOutput.flush();//empty the built up buffer
					}
				}
				else if(method.equalsIgnoreCase("PUT"))
				{
									
					if(resource.equals("/people"))
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
				        
						String body="";
						//System.out.println("h");
//						int bytesRemaining = 38;
						
						//while we still have content to take in to build the body
						while (contentLength > 0)  {
							
						
							contentLength--;
							
							body+=""+(char)buff.read();//check the next char
							
							
						}
						System.out.println(body);
						
						Pattern pattern2 = Pattern.compile("person\\d=([^&]+)");

				        // Create a matcher with the input string
				        Matcher matcher2 = pattern2.matcher(body);
						int i = 0;
				        // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
				        while (matcher2.find()) {
				            String name = matcher2.group(1);
							people.set(i,name);
							i++;
				        }
						
						// for(int i = 0; i < people.size(); i++)
						// {
						// 	if(matcher2.find())
						// 	{
						// 		String name = matcher2.group(1);
						// 		people.set(i,name);
						// 	}			
				        }
						

						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						
						
						clientOutput.flush();//empty the built up buffer
						 //
					
					
							
							
						
					}
					else if(resource.equals("/people/jobs"))
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
				        
						String body="";
						//System.out.println("h");
//						int bytesRemaining = 38;
						
						//while we still have content to take in to build the body
						while (contentLength > 0)  {
							
						
							contentLength--;
							
							body+=""+(char)buff.read();//check the next char
							
							
						}
						
						Pattern pattern2 = Pattern.compile("job\\d=([^&]+)");

				        // Create a matcher with the input string
				        Matcher matcher2 = pattern2.matcher(body);

				        // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
						int i = 0;
				        while (matcher2.find()) {
				            String name = matcher2.group(1);
							people.set(i,name);
							i++;
				        }
						OutputStream clientOutput=client.getOutputStream();//output all responses
						clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
						clientOutput.write(("\r\n").getBytes());//blank line
						
						
						clientOutput.flush();//empty the built up buffer
						 //
					
					
							
							
						
					
				}
				
				
				
				
				client.close();// get ready for next message
//				WebServerConnection con = new WebServerConnection();
//				con.connect();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
