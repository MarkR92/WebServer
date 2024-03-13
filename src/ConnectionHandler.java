
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.net.Socket;
    import java.util.ArrayList;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
    
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
                  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  clientOutput = socket.getOutputStream();
                  StringBuilder header=new StringBuilder();
                    
                    String line="";//temp holding one line of our message
                    line = in.readLine();//gives us the first line
                if(!line.isEmpty()) {
                    while(!line.isBlank())//while the line is not blank continue to read headers end with a blank line
                    {
                        header.append(line+"\r\n");//add line to request
                        line=in.readLine();//check the next line
                        
                    }
                }
                    /*the first part of the response is the header. the first line of the header contains info such 
                     HTTP method (GET,POST,PUT...etc) and resource and resource root (/people of /people/jobs ...etc)
                      */
                    System.out.println();
                    System.out.println("--REQUEST--");
                    
                    System.out.println(header);
                    
                    
                    //Decide how to responed to request
                    String firstLine=header.toString().split("\n")[0];
    //				
    //				/*
    //				 * 
    //				 *  GET 
    //				 *  /people 
    //				 *  HTTP/1.1
    //				
    //				 */
    //				//System.out.println(firstLine);
    //				//get the second element from first line
                    String resource=firstLine.split(" ")[1];
                    String method=firstLine.split(" ")[0];
    //				
    ////				System.out.println(firstLine+" fline");
    ////				System.out.println(resource+" rline");
    //
    //				//compare the element to our list of things
    //				//send back apprpiate thing
    //				
    //				/*when the method in the header is a GET we retrieve the resource. 
    //				 * the GET method may have an optional body but we ignore for now 
    //				*/
                    if(method.equals("GET"))
                    {
                        if(resource.equals("/people"))//check the resource we are looking for
                        {
                            //OutputStream clientOutput=socket.getOutputStream();//output all responses
                            
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
                            clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());//blank line
                            //people are contained in an arraylist. So when queried we output everyone in the list
                            for(int i=0;i<people.size();i++)
                            {
                                clientOutput.write((people.get(i)+" \r\n").getBytes());//encode to bytes
                                
                            }
                    
                            
                            clientOutput.flush();//empty the built up buffer
                        }
                        else if(resource.equals("/"))//the root doesnt do anything rn. Just a "welcome page"
                        {
                            //OutputStream clientOutput=socket.getOutputStream();//output all responses
                            
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
                            clientOutput.write(("\r\n").getBytes());//blank line
                            clientOutput.write(("<!DOCTYPE html>\r\n"
                            + "<html lang=\"en\">\r\n"
                            + "<head>\r\n"
                            + "    <meta charset=\"UTF-8\">\r\n"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                            + "    <title>Document</title>\r\n"
                            + "<style>\r\n"
                            + "    .container {\r\n"
                            + "        width: 300px;\r\n"
                            + "        height: 300px;\r\n"
                            + "        outline: solid 1px black;\r\n"
                            + "        margin: 0 auto;\r\n"
                            + "        z-index: 0;\r\n"
                            + "    }\r\n"
                            + "    /* .container2 {\r\n"
                            + "        width: 300px;\r\n"
                            + "        height: 300px;\r\n"
                            + "        outline: solid 1px red;\r\n"
                            + "        margin: 0 auto;\r\n"
                            + "        z-index: 0;\r\n"
                            + "        display:none;\r\n"
                            + "    } */\r\n"
                            + "    /* main container we stick all div's onto */\r\n"
                            + "    \r\n"
                            + ".container2 {\r\n"
                            + "    display:none;\r\n"
                            + "    width: 300px;\r\n"
                            + "    height: 300px;\r\n"
                            + "\r\n"
                            + "    margin: 0 auto;\r\n"
                            + "    z-index: 0;\r\n"
                            + "}\r\n"
                            + "/* info container describing whats happening i.e waiting  */\r\n"
                            + ".infocontainer {\r\n"
                            + "    display: none;\r\n"
                            + "  width: 300px;\r\n"
                            + "  height: 22px;\r\n"
                            + "  /* outline: solid 1px black; */\r\n"
                            + "  margin: 0 auto;\r\n"
                            + "  z-index: 0;\r\n"
                            + "}\r\n"
                            + "/* creates coloured shapes */\r\n"
                            + ".greenpad{\r\n"
                            + "    position: relative;\r\n"
                            + "    width: 100px;\r\n"
                            + "    height: 100px;\r\n"
                            + "    background: green;\r\n"
                            + "    border-radius:50px;\r\n"
                            + "    left: 5px;\r\n"
                            + "    top: 5px;\r\n"
                            + "    \r\n"
                            + "}\r\n"
                            + ".redpad{\r\n"
                            + "    position: relative;\r\n"
                            + "    width: 100px;\r\n"
                            + "    height: 100px;\r\n"
                            + "    background: rgb(127, 0, 0);\r\n"
                            + "    border-radius:50px;\r\n"
                            + "    left: 5px;\r\n"
                            + "    top: 5px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + ".yellowpad{\r\n"
                            + "    position: relative;\r\n"
                            + "    width: 100px;\r\n"
                            + "    height: 100px;\r\n"
                            + "    background: #8B8000;\r\n"
                            + "    border-radius:50px;\r\n"
                            + " \r\n"
                            + "    left: 5px;\r\n"
                            + "    top: 5px;\r\n"
                            + "}\r\n"
                            + ".bluepad{\r\n"
                            + "    position: relative;\r\n"
                            + "    width: 100px;\r\n"
                            + "    height: 100px;\r\n"
                            + "    background: rgb(0, 0, 128);\r\n"
                            + "    border-radius:50px;\r\n"
                            + " \r\n"
                            + "    left: 5px;\r\n"
                            + "    top: 5px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + ".startbutton{\r\n"
                            + "    position: relative;\r\n"
                            + "    \r\n"
                            + "    width: 80px;\r\n"
                            + "    height: 25px;\r\n"
                            + "    \r\n"
                            + "    left: 74px;\r\n"
                            + "    top: -110px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + "\r\n"
                            + "/*top score display*/\r\n"
                            + ".leftbox{\r\n"
                            + "    position: relative;\r\n"
                            + "    font-family: \"Kimberley\", sans-serif ;\r\n"
                            + "    width: 35px;\r\n"
                            + "    height: 25px;\r\n"
                            + "    text-align: center;\r\n"
                            + "    left: 74px;\r\n"
                            + "    top: -110px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + "/*current score display*/\r\n"
                            + ".rightbox{\r\n"
                            + "    position: relative;\r\n"
                            + "    \r\n"
                            + "    width: 35px;\r\n"
                            + "    height: 25px;\r\n"
                            + "    text-align: center;\r\n"
                            + "    left: 74px;\r\n"
                            + "    top: -110px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + ".led{\r\n"
                            + "    position: relative;\r\n"
                            + "    width: 8px;\r\n"
                            + "    height: 8px;\r\n"
                            + "    background: red;\r\n"
                            + "    border-radius:50px;\r\n"
                            + " \r\n"
                            + "    left: 148px;\r\n"
                            + "    top: -105px;\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "/* creates black background for each shape */\r\n"
                            + ".greencontainer{\r\n"
                            + "\r\n"
                            + "    position: relative;\r\n"
                            + "    \r\n"
                            + "    height:110px;\r\n"
                            + "    \r\n"
                            + "    width: 110px;\r\n"
                            + "    \r\n"
                            + "    z-index: 1;\r\n"
                            + "    margin-left: 0px;\r\n"
                            + "    margin-top:0px;\r\n"
                            + "    border-radius:55px;\r\n"
                            + "    background: black;\r\n"
                            + "    \r\n"
                            + "    }\r\n"
                            + ".redcontainer{\r\n"
                            + "\r\n"
                            + "  position: relative;\r\n"
                            + "        \r\n"
                            + "  height:110px;\r\n"
                            + "    \r\n"
                            + "  width:110px;\r\n"
                            + "        \r\n"
                            + "  z-index:1;\r\n"
                            + "  border-radius:55px;\r\n"
                            + "  background: black;\r\n"
                            + "        \r\n"
                            + "  margin-left: 190px;\r\n"
                            + "  margin-top:-110px;\r\n"
                            + "\r\n"
                            + "        }\r\n"
                            + ".yellowcontainer{\r\n"
                            + "\r\n"
                            + "  position: relative;\r\n"
                            + "  \r\n"
                            + "  height:110px;\r\n"
                            + "\r\n"
                            + "  width: 110px;\r\n"
                            + "  \r\n"
                            + "  z-index: 1;\r\n"
                            + "  border-radius:55px;\r\n"
                            + "  background: black;\r\n"
                            + "  \r\n"
                            + "  margin-left: 0px;\r\n"
                            + "  margin-top:80px;\r\n"
                            + "  \r\n"
                            + "  }\r\n"
                            + ".bluecontainer{\r\n"
                            + "\r\n"
                            + "  position: relative;\r\n"
                            + "    \r\n"
                            + "  height:110px;\r\n"
                            + "\r\n"
                            + "  width: 110px;\r\n"
                            + "    \r\n"
                            + "  z-index: 1;\r\n"
                            + "  background: black;\r\n"
                            + "  border-radius:55px;\r\n"
                            + "  margin-left: 190px;\r\n"
                            + "  margin-top:-110px;\r\n"
                            + "\r\n"
                            + "    \r\n"
                            + "    }\r\n"
                            + "\r\n"
                            + "  /* center shape contains scores and start button etc*/\r\n"
                            + ".center{\r\n"
                            + "\r\n"
                            + "  position: relative;\r\n"
                            + "  \r\n"
                            + "  width: 200px;\r\n"
                            + "  border: 2px solid gray;\r\n"
                            + "  height: 200px;\r\n"
                            + "  \r\n"
                            + "  z-index: -3;\r\n"
                            + "  \r\n"
                            + "  background: black;\r\n"
                            + "  \r\n"
                            + "  margin-left: 50px;\r\n"
                            + "  margin-top:-250px;\r\n"
                            + "  border-radius:100px;\r\n"
                            + "    }\r\n"
                            + "       \r\n"
                            + "\r\n"
                            + " \r\n"
                            + "\r\n"
                            + "</style>\r\n"
                            + "</head>\r\n"
                            + "<body>\r\n"
                            + "    <div class =\"infocontainer\" id=\"infocon\" >\r\n"
                            + "        <p style=\"text-align: center; font-family: Orbitron\"  id=\"info\">Press start to begin</p>\r\n"
                            + "      </div>\r\n"
                            + "  \r\n"
                            + "      <div class =\"container2\" id=\"container2\">\r\n"
                            + "  \r\n"
                            + "      <div class =\"greencontainer\">\r\n"
                            + "      <input type=\"button\" class=\"greenpad\" id=\"green\" />\r\n"
                            + "      </div>\r\n"
                            + "  \r\n"
                            + "     <div class =\"redcontainer\">\r\n"
                            + "     <input type=\"button\" class=\"redpad\" id =\"red\"  />\r\n"
                            + "     </div>\r\n"
                            + "  \r\n"
                            + "     <div class =\"yellowcontainer\">\r\n"
                            + "     <input type=\"button\" class=\"yellowpad\" id=\"yellow\"  />\r\n"
                            + "     </div>\r\n"
                            + "  \r\n"
                            + "     <div class =\"bluecontainer\">\r\n"
                            + "     <input type=\"button\" class=\"bluepad\" id=\"blue\" />\r\n"
                            + "     </div>\r\n"
                            + "  \r\n"
                            + "     <div class =\"center\"></div>\r\n"
                            + "     <input type=\"button\" style=\"font-family: Orbitron\" class=\"leftbox\" value=\"0\" id=\"top\"/>\r\n"
                            + "     <input type=\"button\" style=\"font-family: Orbitron\" class=\"startbutton\" id=\"startbutton\" value=\"Start\"  />\r\n"
                            + "     <input type=\"button\" style=\"font-family: Orbitron\"  class=\"rightbox\" id=\"current\"value=\"0\"/>\r\n"
                            + "  \r\n"
                            + "    <div class=\"led\" id =\"led\"></div>\r\n"
                            + "     </div>\r\n"
                            + "     \r\n"
                            + "      \r\n"
                            + "  </div>\r\n"
                            + "   <div class =\"container\"  id=\"logincontainer\">\r\n"
                            + "   <div>\r\n"
                            + "     <form>\r\n"
                            + "        <label for=\"username\">Username:</label><br>\r\n"
                            + "        <input type=\"text\" id=\"username\" name=\"username\"><br>\r\n"
                            + "        <label for=\"pwd\">Password:</label><br>\r\n"
                            + "        <input type=\"password\" id=\"pwd\" name=\"pwd\">\r\n"
                            + "      </form>\r\n"
                            + "    <input type=\"button\" value=\"Login\" id =\"login\" onclick=\"login(document.getElementById('username').value,document.getElementById('pwd').value)\"/></div>\r\n"
                            + "  \r\n"
                            + "    </div>\r\n"
                            + "    <script>\r\n"
                            + "             const Http = new XMLHttpRequest();\r\n"
                            + "\r\n"
                            + "        function login(user ,pwd)\r\n"
                            + "        {\r\n"
                            + "           \r\n"
                            + "            const url ='http://localhost:8097/login';\r\n"
                            + "            Http.open(\"POST\",url);\r\n"
                            + "            Http.send(\"username=\"+user+\"&password=\"+pwd);\r\n"
                            + "            \r\n"
                            + "       Http.onreadystatechange=(e)=>{\r\n"
                            + "        console.log(Http.responseText);\r\n"
                            + "\r\n"
                            + "        if(Http.responseText==200)\r\n"
                            + "        {\r\n"
                            + "            var x = document.getElementById(\"logincontainer\");\r\n"
                            + "            if (x.style.display === \"none\") {\r\n"
                            + "             x.style.display = \"block\";\r\n"
                            + "            } else {\r\n"
                            + "              x.style.display = \"none\";\r\n"
                            + "            }\r\n"
                            + "            var x = document.getElementById(\"container2\");\r\n"
                            + "            \r\n"
                            + "             x.style.display = \"block\";\r\n"
                            + "             var x = document.getElementById(\"infocon\");\r\n"
                            + "            \r\n"
                            + "            x.style.display = \"block\";\r\n"
                            + "\r\n"
                            + "             \r\n"
                            + "            \r\n"
                            + "            \r\n"
                            + "        }\r\n"
                            + "       }\r\n"
                            + "        }\r\n"
                            + "    //    const Http = new XMLHttpRequest();\r\n"
                            + "    //    const url ='http://localhost:8097/people';\r\n"
                            + "    //    Http.open(\"GET\",url);\r\n"
                            + "    //    Http.send();\r\n"
                            + "\r\n"
                            + "        // function login(user,pwd)\r\n"
                            + "        // {\r\n"
                            + "        //     const ws=new WebSocket(\"wss://localhost:8097/\");\r\n"
                            + "\r\n"
                            + "        //     ws.addEventListener(\"open\", ()=>{\r\n"
                            + "        //         console.log(\"Connected\");\r\n"
                            + "        //     });\r\n"
                            + "        //     console.log(user);\r\n"
                            + "        //     console.log(pwd);\r\n"
                            + "\r\n"
                            + "        //     ws.send(user);\r\n"
                            + "        // }\r\n"
                            + "\r\n"
                            + "        \r\n"
                            + "\r\n"
                            + "//tested with Win10 Chrome\r\n"
                            + "\r\n"
                            + "//get refercences to html elements. \r\n"
                            + "//This means we don't have to use OnClick in the html doc\r\n"
                            + "const startButton = document.querySelector(\".startbutton\");\r\n"
                            + "const scoreDisplay = document.querySelector(\"#current\");\r\n"
                            + "const topScoreDisplay = document.querySelector(\"#top\");\r\n"
                            + "const green = document.querySelector(\"#green\");\r\n"
                            + "const red = document.querySelector(\"#red\");\r\n"
                            + "const yellow = document.querySelector(\"#yellow\");\r\n"
                            + "const blue = document.querySelector(\"#blue\");\r\n"
                            + "const info = document.querySelector(\"#info\");\r\n"
                            + "\r\n"
                            + "//This is a general wait delay which allows us to wait when the start button is pressed\r\n"
                            + "// as well as wait for flashes see https://www.sitepoint.com/delay-sleep-pause-wait/ for more details\r\n"
                            + "const delay = (delay) => new Promise((resolve) => setTimeout(resolve, delay))//creats a delay\r\n"
                            + "\r\n"
                            + "let computerSequence=[];   //stores the randomly generated computer sequence (sequence = flash of a light) during computer phase of game\r\n"
                            + "let userSequence=[];       //stores which button is pressed by the user during the userpick phase of gams\r\n"
                            + "let playingGame;           // when true we ae in the game loop playing the game\r\n"
                            + "let score=0;               //current user score for \"that\" game loop. Resets every game loop\r\n"
                            + "let topScore=0;            //highest score acheived from all game loops . Resets when browers is refreshed.\r\n"
                            + "let level=1;               //keep track of what level we are on. Timer will decreases at certain levels\r\n"
                            + "let timer=1000;            //this is how fast the lights will flash. Over the levels it will decrese. This is an input parameter to the delay.\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "//listen for the start button to be clicked\r\n"
                            + " startButton.addEventListener('click',async(event)=>{\r\n"
                            + "   document.getElementById(\"led\").style.background=\"green\";\r\n"
                            + "   info.innerHTML=\"Waiting....\"\r\n"
                            + "   startButton.disabled=true;  //disable the start button\r\n"
                            + "   await delay(3000) ;         //wait 3s after the start button is pressed\r\n"
                            + "   info.innerHTML=\"Computers turn\"\r\n"
                            + "   playingGame=true;           //we are playing the game\r\n"
                            + "   \r\n"
                            + "   startGame();                //start the game\r\n"
                            + "\r\n"
                            + "  \r\n"
                            + " })\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "async function endGame()\r\n"
                            + "{\r\n"
                            + "\r\n"
                            + "   info.innerHTML=\"Game Over\" //display game over\r\n"
                            + "   document.getElementById(\"led\").style.background=\"red\"; //change led to red\r\n"
                            + "   computerSequence=[];//reset the comp sequence\r\n"
                            + "   userSequence=[];\r\n"
                            + "   playingGame=false;\r\n"
                            + "   level=0;\r\n"
                            + "   timer=1000;\r\n"
                            + "   score=0;\r\n"
                            + "   scoreDisplay.value=score;\r\n"
                            + "\r\n"
                            + "   for(let i=0;i<5;i++)//flasg buttons 5 times\r\n"
                            + "   {\r\n"
                            + "   flashColor();\r\n"
                            + "   await delay(500);\r\n"
                            + "   clearColor(); \r\n"
                            + "   await delay(500);\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + "startButton.disabled=false;\r\n"
                            + "info.innerHTML=\"Press start to begin\"\r\n"
                            + "\r\n"
                            + "}\r\n"
                            + "  function flashColor() {\r\n"
                            + "  green.style.backgroundColor = \"#AAFF00\";\r\n"
                            + "  red.style.backgroundColor = \"#FF5733\";\r\n"
                            + "  yellow.style.backgroundColor = \"#FFFF00\";\r\n"
                            + "  blue.style.backgroundColor = \"#0096FF\";\r\n"
                            + "  \r\n"
                            + "}\r\n"
                            + "function clearColor() {\r\n"
                            + "      green.style.backgroundColor = \"green\";\r\n"
                            + "      red.style.backgroundColor =  \"darkred\"\r\n"
                            + "      yellow.style.backgroundColor = \"#8B8000\";\r\n"
                            + "      blue.style.backgroundColor =  \"darkblue\";\r\n"
                            + "   }\r\n"
                            + "async function startGame()\r\n"
                            + "{\r\n"
                            + "   while(playingGame) //game loop\r\n"
                            + "   {\r\n"
                            + "\r\n"
                            + "      var rand=Math.floor(Math.random() * 4); //generate a random number 0-green,1-red,2-yellow,3-blue\r\n"
                            + "      computerSequence.push(rand);//add to computerSequence\r\n"
                            + "      //increase the flash speed at level 5,9 and 13\r\n"
                            + "      if(level==5)\r\n"
                            + "      {\r\n"
                            + "         timer=timer-200;\r\n"
                            + "      }\r\n"
                            + "      else if(level==9)\r\n"
                            + "      {\r\n"
                            + "         timer=timer-300;\r\n"
                            + "      }\r\n"
                            + "       else  if(level==13)\r\n"
                            + "      {\r\n"
                            + "         timer=timer-400;\r\n"
                            + "      }\r\n"
                            + "     //This for loop represents the computers turn. Goes thru comp sequence and flashes buttons\r\n"
                            + "     info.innerHTML=\"Computers turn\";\r\n"
                            + "      for (let i = 0; i < computerSequence.length; i++) {\r\n"
                            + "         \r\n"
                            + "         if(computerSequence[i]==0){\r\n"
                            + "            document.getElementById(\"green\").style.background=\"#AAFF00\";\r\n"
                            + "            await delay(timer);\r\n"
                            + "            document.getElementById(\"green\").style.background=\"green\";\r\n"
                            + "         }\r\n"
                            + "         else if(computerSequence[i]==1)\r\n"
                            + "         {\r\n"
                            + "            document.getElementById(\"red\").style.background=\"#FF5733\";\r\n"
                            + "            await delay(timer);\r\n"
                            + "            document.getElementById(\"red\").style.background=\"darkred\";\r\n"
                            + "         }\r\n"
                            + "         else if(computerSequence[i]==2)\r\n"
                            + "         {\r\n"
                            + "            document.getElementById(\"yellow\").style.background=\"#FFFF00\";\r\n"
                            + "            await delay(timer);\r\n"
                            + "            document.getElementById(\"yellow\").style.background=\"#8B8000\";\r\n"
                            + "         }\r\n"
                            + "         else if(computerSequence[i]==3)\r\n"
                            + "         {\r\n"
                            + "            document.getElementById(\"blue\").style.background=\"#0096FF\";\r\n"
                            + "            await delay(timer);\r\n"
                            + "            document.getElementById(\"blue\").style.background=\"darkblue\";\r\n"
                            + "           \r\n"
                            + "         }\r\n"
                            + "            await delay(timer/2);\r\n"
                            + "      }\r\n"
                            + "      //After the computer is done we start the user phase here\r\n"
                            + "      //Every user phase we clear the user sequence\r\n"
                            + "      userSequence=[];\r\n"
                            + "      \r\n"
                            + "    \r\n"
                            + "         myVar = setTimeout( endGame, 5000);//start a global 5s timer\r\n"
                            + "         info.innerHTML=\"Users turn\"\r\n"
                            + "         await userPicks();//wait for the user to pick \r\n"
                            + "         clearTimeout(myVar);//restart the timer\r\n"
                            + "         \r\n"
                            + "      level++; \r\n"
                            + "    \r\n"
                            + "   }\r\n"
                            + "\r\n"
                            + "}\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "async function userPicks() {\r\n"
                            + "   // this function listens for which button is pressed and waits until the user has pressed enough buttons to match the current\r\n"
                            + "   // sequence.\r\n"
                            + "   green.addEventListener('click',btnResolverGreen);\r\n"
                            + "   red.addEventListener('click',btnResolverRed);\r\n"
                            + "   yellow.addEventListener('click',btnResolverYellow);\r\n"
                            + "   blue.addEventListener('click',btnResolverBlue);\r\n"
                            + "\r\n"
                            + "   for (let c = 0; c < computerSequence.length; c ++) {\r\n"
                            + "    \r\n"
                            + "    \r\n"
                            + "      await waitForPress();//wait for user press to match comp \r\n"
                            + "    }\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "   green.removeEventListener('click', btnResolverGreen);\r\n"
                            + "   red.removeEventListener('click', btnResolverRed);\r\n"
                            + "   yellow.removeEventListener('click', btnResolverYellow);\r\n"
                            + "   blue.removeEventListener('click', btnResolverBlue);\r\n"
                            + "  \r\n"
                            + " }\r\n"
                            + "\r\n"
                            + " //adapted from https://stackoverflow.com/questions/65915371/how-do-i-make-the-program-wait-for-a-button-click-to-go-to-the-next-loop-iterati\r\n"
                            + "let waitForPressResolve;\r\n"
                            + "\r\n"
                            + "function waitForPress() {\r\n"
                            + "   \r\n"
                            + "         return new Promise(resolve => waitForPressResolve = resolve); \r\n"
                            + "}\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "//if green has been pressed store this in the sequence array and check if the user made a mistake with the input sequence\r\n"
                            + "async function btnResolverGreen() {\r\n"
                            + "   userSequence.push(0);\r\n"
                            + "   document.getElementById(\"green\").style.background=\"#AAFF00\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   document.getElementById(\"green\").style.background=\"green\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   check();\r\n"
                            + "   if (waitForPressResolve) waitForPressResolve();\r\n"
                            + " }\r\n"
                            + " //if red has been pressed store this in the sequence array and check if the user made a mistake with the input sequence\r\n"
                            + " async function btnResolverRed() {\r\n"
                            + "   userSequence.push(1);\r\n"
                            + "   document.getElementById(\"red\").style.background=\"#FF5733\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   document.getElementById(\"red\").style.background=\"darkred\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   check();\r\n"
                            + "   if (waitForPressResolve) waitForPressResolve();\r\n"
                            + " }\r\n"
                            + " //if yellow has been pressed store this in the sequence array and check if the user made a mistake with the input sequence\r\n"
                            + " async function btnResolverYellow() {\r\n"
                            + "   userSequence.push(2);\r\n"
                            + "   document.getElementById(\"yellow\").style.background=\"#FFFF00\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   document.getElementById(\"yellow\").style.background=\"#8B8000\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   check();\r\n"
                            + "   if (waitForPressResolve) waitForPressResolve();\r\n"
                            + " }\r\n"
                            + " //if blue has been pressed store this in the sequence array and check if the user made a mistake with the input sequence\r\n"
                            + " async function btnResolverBlue() {\r\n"
                            + "   userSequence.push(3);\r\n"
                            + "   document.getElementById(\"blue\").style.background=\"#0096FF\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   document.getElementById(\"blue\").style.background=\"darkblue\";\r\n"
                            + "   await delay(50);\r\n"
                            + "   check();\r\n"
                            + "   if (waitForPressResolve) waitForPressResolve();\r\n"
                            + " }\r\n"
                            + "\r\n"
                            + " \r\n"
                            + " function check()\r\n"
                            + " {\r\n"
                            + "   //checks if the user has inputed a matching sequence if so we increase the score else we end the game\r\n"
                            + "   let pass=true;\r\n"
                            + "   for (let i = 0; i < userSequence.length; i ++) {\r\n"
                            + "\r\n"
                            + "      if(computerSequence[i]!=userSequence[i])\r\n"
                            + "      {\r\n"
                            + "         endGame();\r\n"
                            + "         pass=false;\r\n"
                            + "      }\r\n"
                            + "      \r\n"
                            + "    }\r\n"
                            + "    //keeping track of the score\r\n"
                            + "    if(pass)\r\n"
                            + "    {\r\n"
                            + "      score++;\r\n"
                            + "      if(topScore<score)\r\n"
                            + "      {\r\n"
                            + "         topScore=score;\r\n"
                            + "      }\r\n"
                            + "    }\r\n"
                            + "   scoreDisplay.value=score;//display current score\r\n"
                            + "   topScoreDisplay.value=topScore;//display top socre\r\n"
                            + " }\r\n"
                            + " //END of Assignment\r\n"
                            + " /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////\r\n"
                            + "\r\n"
                            + "    \r\n"
                            + "\r\n"
                            + "\r\n"
                            + " \r\n"
                            + "\r\n"
                            + "    </script>\r\n"
                            + "</body>\r\n"
                            + "</html>").getBytes());//encode to bytes
                            
                            clientOutput.flush();//empty the built up buffer
                            
                        }
                        else {
                            //if the resource is not found output 404
                            //OutputStream clientOutput=socket.getOutputStream();//output all responses
                            clientOutput.write(("HTTP/1.1 404 Not Found\r\n").getBytes());//encode to bytes
                            clientOutput.write(("\r\n").getBytes());//blank line
                            clientOutput.write(("404").getBytes());//encode to bytes
                            clientOutput.flush();//empty the built up buffer
                        }
                        
                    }
    //				/*if the method is post we need to get the info stored in the body. The body comes after the header. W
    //				 * we need to check the content length to know when we are done and we need to check content type so we know 
    //				 * which parser to use. currently only Content-Type: application/x-www-form-urlencoded . is supported but need to look into this more*/
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
                                
                                body+=""+(char)in.read();//check the next char
                                
                                //System.out.println(body);
                            }
                            System.out.println(body+"out");
                            Pattern pattern2 = Pattern.compile("person\\d=([^&]+)");
    
                            // Create a matcher with the input string
                            Matcher matcher2 = pattern2.matcher(body);
    
                            // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
                            while (matcher2.find()) {
                                String name = matcher2.group(1);
                                System.out.println(name);
                                people.add(name);
                                people.size();
                            }
                            //OutputStream clientOutput=socket.getOutputStream();//output all responses
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
                            clientOutput.write(("\r\n").getBytes());//blank line
                            
                            
                            clientOutput.flush();//empty the built up buffer
            
                  
                    
                    }
                    else if(resource.equals("/login")) {
					
                        int contentLength=0;
                        
                        Pattern pattern = Pattern.compile("Content-Length: (\\d+)");
                        Matcher matcher = pattern.matcher(header);
    
                        // Check if the pattern is found
                        if (matcher.find()) {
                          
                            contentLength= Integer.parseInt(matcher.group(1));
                           
                        } else {
                            System.out.println("Content-Length not found in the headers.");
                        }
                        
                        String body="";
                    
                        while (contentLength > 0)  {
                            
                        
                            contentLength--;
                            
                            body+=""+(char)in.read();//check the next char
                            
                            //System.out.println(body);
                        }
                        //System.out.println(body+"out");
                         pattern = Pattern.compile("username=([^&]+)&password=([^&]+)");
    
                        // Create a matcher with the input string
                         matcher = pattern.matcher(body);
                         String username="";
                         String pwd ="";
                        // Find the names this only works for the following format person1=mark&person2=rob&person3=khate&person4=dan&person5=dorian
                        while (matcher.find()) {
                            username = matcher.group(1);
                            //System.out.println(username);
                            pwd = matcher.group(2);
                           // System.out.println(pwd);
                            
                        }
                        if(username.equals("admin")&pwd.equals("admin"))
                        {
                            //System.out.println("here");
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());//encode to bytes
                            clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());//blank line
                            clientOutput.write(("200").getBytes());
                            clientOutput.flush();//empty the built up buffer
                            
                        }
                        else
                        {
                            //OutputStream clientOutput=socket.getOutputStream();//output all responses
                            clientOutput.write(("HTTP/1.1 401 OK\r\n").getBytes());//encode to bytes
                            clientOutput.write(("Login for "+username+" failed. Invalid user name or password\r\n").getBytes());//encode to bytes
                            clientOutput.write(("Access-Control-Allow-Origin: *\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());//blank line
                            
                            
                            clientOutput.flush();//empty the built up buffer
                        }
                    
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
    

