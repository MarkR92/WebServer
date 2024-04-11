

var sequence = []; //The sequence of buttons that lit up
var userInput = []; //The sequence of buttons the user pressed
let button = 0;
let count = 0; //Keeps track of how many times the sequence has ran.
let num = 0; 
let userTurn = false; //Whether or not the user is allowed push buttons
let userCount = 0; //How many buttons the user has pushed.
let gameRunning = false; //Keeps track if a game is currently in progress.
let lose = 0; //Variable for flashes when you lose.
var currentScore = 0; //The score from the user's last game
var highScore = 0; //The user's overall highest score.
var timeID;

let interLength = 2000; //The length of the game.

//Make an array of the 4 buttons:
var buttons = document.getElementsByClassName('button');

//Function when you hit the start button:
function start()
{
    //Makes sure the game is not already running.
    if(gameRunning == false)
    {
    //Reset all variables.
    gameRunning = true;
    currentScore = 0;
    interLength = 2000; //Sets sequence interval to every 2 seconds.
    sequence = [];
    
    document.getElementById("signal").style.backgroundColor = "rgb(122, 255, 82)";
    setTimeout(() =>
    {
        
        runSequence(); //Begin the first sequence.
    },1000)   
    }
    else{
        alert("Game Already on!")
    }
}
function runSequence()
{
    
    interLength = 2000;
    //Generate a random number between 0 and 3 to add a button to the sequence:
    button = Math.floor(Math.random() * 4);
    sequence.push(button);
    //Sets status indicator from red to green.

    count = 0; //Keeps count of how many flashes have occured.
    //Flash a button once interLength has passed:
    let flashInterval = setInterval(() =>
    {
        flashButton(sequence[count]);
        count++;
        //Speed up the game after the 5th, 9th and 13th sequence.
        if(count == 5 | count == 9 | count == 13)
        {
            interLength = interLength - 500;
        }

        if(count >= sequence.length) //When the sequence has been completed.
        {      
            clearInterval(flashInterval) 
            userInput = [];
            userTurn = true;
            userCount = 0;
            timeLimit(); //Start the five second timer.
        }      
        
    },interLength);   
}
function flashButton(button)
{
    if(button == 0)
    {
        buttons[button].style.backgroundImage = "linear-gradient(rgb(208, 255, 206) ,rgb(86, 229, 143))";
    }
    else if(button == 1)
    {
        buttons[button].style.backgroundImage = "linear-gradient(rgb(255, 200, 192) ,rgb(204, 61, 99))";
    }
    else if(button == 2)
    {
        buttons[button].style.backgroundImage = "linear-gradient(rgb(255, 255, 230) ,rgb(240, 183, 108)  )";
    }
    else if(button == 3)
    {
        buttons[button].style.backgroundImage = "linear-gradient(rgb(223, 255, 253) ,rgb(143, 113, 249)  )";
    }
    
    setTimeout(() =>
    {
            buttons[button].style.backgroundImage = "";
    },250)   
}
//Function for when the user pushes a button.
function push(num) //num is called from the html and identifies which button was pushed.
{    
    if(userTurn == true)
    {
        buttonPress(num) //buttonpress animation.

        userInput.push(num); //push the button onto the array.
        
        if(userInput[userCount] == sequence[userCount]) //Checks if button user pushed corresponds to one in sequence.
        {
            userCount++;
            stopTimer();

            timeLimit();

            if(userInput.length == sequence.length) //Checks to see if sequence has finished.
            {
                stopTimer();
                userTurn = false;
                currentScore = currentScore + 1;
                runSequence();
            }
        }
        else{ 
            stopTimer();     
            gameLost();      
        }
    }
}
//Function for when 
function gameLost()
{ 
    //A check to make sure the game displays 01 as score instead of just 1, but not 010
    if(currentScore < 10) 
    {
        document.getElementById("current").innerHTML = "0"+currentScore;
    }
    else{
        document.getElementById("current").innerHTML = currentScore;
    }
    if(currentScore > highScore)
    {
        highScore = currentScore;
        if(currentScore < 10){
            document.getElementById("high").innerHTML = "0"+highScore;
        }
        else{
            document.getElementById("high").innerHTML = "0"+highScore;
        }
    }
    //Reset variables.
    gameRunning = false;
    userTurn = false;

    //Flash the buttons 5 times when you lose!
    lose = 0;
    let stopInterval = setInterval(() =>
    {
        flashButton(0);
        flashButton(1);
        flashButton(2);
        flashButton(3);
        lose++;
        if(lose >= 5)
        {
            clearInterval(stopInterval);
        }
    },400);
    //Reset the signal color!
    document.getElementById("signal").style.backgroundColor = "red";    
}
//This method changes a buttons appearance when you press it.
function buttonPress(num)
{
    buttons[num].style.height = "155px"
    buttons[num].style.width = "155px"
    setTimeout(() =>
    {
            buttons[num].style.height = "";
            buttons[num].style.width = "";
    },250) 
}
//Timelimit function of five seconds for when the user's turn.
function timeLimit()
{ 
    clearTimeout(timeID);
    timeID = null;
    timeID = setTimeout(() =>
    {
            if(userTurn == true)
            {
                gameLost(); 
            }
    },5000)   
}
//Function to stop the timer:
function stopTimer()
{
    clearTimeout(timeID);
}
