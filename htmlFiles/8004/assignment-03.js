

// import data from './assignment-03.json';

let tableData = []; //Keeps track of data in the table
let rowData = [];
let saveData = []; //For saving and restoring table data 


var noColumns = 8; //Number of noColumns
var noRows = 10; //Number of Rows
var table; //The table element.
var rowElement; //Specific row elements

let saveRows; //Saves rows
let saveColumns; //Saves columns

var index = 0;
var hasSaved = false;//Only alows restore if previous save exists
var count;
let counting = 0;

table = document.getElementById("newTable");


fillTable();
//Creating a table at the start of the program:
newTable();

var sum = 0;
var add = 0;
let time = 0; //for setTimeout function
var gradeStyle = 0; //Keeps track of grade average style
var grade;

//Fill the table with values
function fillTable(){
      tableData = [];
      for(var i = 0; i < noRows; i++)
      {
      rowData = [];
      sum = 0;


            for(var  j = 0; j< noColumns; j++)
            {
                  switch (j) {
                        case 0:
                              //Fill in student name
                              rowData.push(randomNameGenerator()); break; 
                        case 1:
                              //Fill in student ID
                              rowData.push(randomStudentNumber()); break;

                        case noColumns - 1:
                              //Fill in average
                              sum = Math.trunc(sum/(noColumns - 3));
                              rowData.push(sum); break; 
                        default:
                              //Fill in grade
                              add = "";
                              sum += add;
                              rowData.push(add);    
                  }
            }

      tableData.push(rowData);
      }     
}

//Function for creating a new table from scratch
function newTable(){     

//Delete already exisiting table:
while (table.firstChild) {
      table.removeChild(table.firstChild);
}

// Create the table header:
var tableHeader = document.createElement("tr");

//Set the header names:
for (var i = 0; i < noColumns; i++) {
      var th = document.createElement("th");

      switch (i) {
      case 0:
            th.textContent = "Student Name"; break; 
      case 1:
            th.textContent = "Student ID"; break; 
      case noColumns - 1:
            th.textContent = "Average (%)"; break; 
      default:
            th.textContent = "Assignment "+(i-1); 
      }
      tableHeader.appendChild(th);
}
//Add to table
table.appendChild(tableHeader);

      // Create the table's rows:
      for (var i = 0; i < noRows; i++) {
            // var rowData = [];

            var row = document.createElement("tr");
            for (var j = 0; j < noColumns; j++) {
            var rowCell = document.createElement("td");

            rowCell.textContent = tableData[i][j];
            //If row cell is a grade:
            if(j < noColumns - 1 && j > 1)
            {  
                  rowCell.addEventListener("input", updateCell);
                  rowCell.setAttribute("contenteditable", "true");
                  rowCell.setAttribute("class", "grades");

                  if(tableData[i][j] == "")
                  {
                  rowCell.textContent = "-";
                  rowCell.style.textAlign = "center";
                  rowCell.style.backgroundColor = "yellow";
                  }
                       
            }
            else if(j == noColumns - 1) //If rowCell is an average:
            {  
                  // rowCell.addEventListener("click", average);
                  // rowCell.textContent = average(i);  
                  rowCell.setAttribute("class", "grades");
                  rowCell.addEventListener("click", letters);
                  if(parseInt(tableData[i][j]) < 60)
                  {
                  rowCell.style.backgroundColor = "red";
                  }
                  
            }
            
            row.appendChild(rowCell);
            }
            table.appendChild(row);
      }     
      countBlank();
}
//Add in a new row:
function newRow(){
      rowData = [];
      noRows++;
      sum = 0;


      for(var  j = 0; j< noColumns; j++)
      {
            switch (j) {
                  case 0:
                        rowData.push(randomNameGenerator()); break; 
                  case 1:
                        rowData.push(randomStudentNumber()); break;

                  case noColumns - 1:
                        sum = Math.trunc(sum/(noColumns - 3));
                        rowData.push(sum); break; 
                  default:
                        add = "";
                        rowData.push(add);    
            }
      }
      tableData.push(rowData);
      var row = document.createElement("tr");
      for (var j = 0; j < noColumns; j++) {
            var rowCell = document.createElement("td");

            rowCell.textContent = rowData[j];
            if(j < noColumns - 1 && j > 1)
            {  
                  rowCell.addEventListener("input", updateCell);
                  rowCell.setAttribute("contenteditable", "true");
                  rowCell.setAttribute("class", "grades");
                  rowCell.textContent = "-";
                  rowCell.style.textAlign = "center";
                  rowCell.style.backgroundColor = "yellow";
                       
            }
            else if(j == noColumns - 1)
            {  
                  // rowCell.addEventListener("click", average);
                  // rowCell.textContent = average(i);  
                  rowCell.setAttribute("class", "grades");
                  rowCell.addEventListener("click", letters);
                  rowCell.style.backgroundColor = "red";
            }
            
            // rowData.push(rowCell);
            row.appendChild(rowCell);
      }
            table.appendChild(row);
            countBlank();
      
}
//Add in a new column:
function newColumn(){
      rowElement = table.rows;
      noColumns++;
      var rowCell;

      for (var i = 0; i < noRows + 1; i++) {
            
            if(i == 0)
            {
                  rowCell = document.createElement("th");
                  rowCell.textContent = "Assignment "+(noColumns - 3); 
            }
            else{
                  var rowCell = document.createElement("td");

                  rowCell.setAttribute("contenteditable", "true");
                  rowCell.setAttribute("class", "editable-cell");
                  rowCell.addEventListener("input", updateCell); 

                  rowCell.textContent = "-";
                  rowCell.style.textAlign = "center";
                  rowCell.style.backgroundColor = "yellow";
                  let s1 = tableData[i-1][noColumns - 2];
                  tableData[i-1][noColumns - 2] = "";
                  tableData[i-1].push(s1);
            }
            //Insert column cell before the average grade cell.
            rowElement[i].insertBefore(rowCell, rowElement[i].lastChild);   
      }  
      countBlank();
}
//Update the existing cell when user inputs:
function updateCell(event) {

var rowIndex = event.target.parentNode.rowIndex - 1;
var columnIndex = event.target.cellIndex;
var value = event.target.textContent.trim();

//If user input is invalid, set cell to "-" :
if(Number(value) > 100 | Number(value) < 0 | isNaN(value) | value == "")
{
      
      time = setTimeout(() =>
      {
      value = "-";
      event.target.textContent = "-";  
      event.target.style.textAlign = "center";
      event.target.style.backgroundColor = "yellow";
      },1000)
}
else
{    
      clearTimeout(time);
      event.target.style.textAlign = "";
      event.target.style.backgroundColor = "";
}

updateData(rowIndex, columnIndex, value);
average(rowIndex); //Update the average grade

}
//Update the table data array when a cell is updated.
function updateData(row, col, value) {
tableData[row][col] = value;
countBlank();
}
//Saves current table so it can be reset later.
function save()
{     
      saveData = JSON.stringify(tableData); //Convert array to JSON string
      saveRows = noRows;
      saveColumns = noColumns;
      hasSaved = true;
}
//Restore the table to a previous state:
function restore()
{
      if(hasSaved == true)
      {
      tableData = JSON.parse(saveData); //Convert JSON string back into an array
      noRows = saveRows;
      noColumns = saveColumns;

      newTable(); //Generate a new table using the saveData
      }
      else{
            alert("No previous table data has been saved!");
      }
}
function randomStudentNumber()
{
      return button = Math.floor(10000000 + Math.random() * 90000000);
}
function randomGrade()
{
      return button = Math.floor(Math.random() * 101);
}
//Generate the average grade
function average(rowIndex)
{
      // var rowIndex = event.target.parentNode.rowIndex - 1;
      rowElement = table.rows;

      var sum = 0;
      var j = 2;
      count = 0;

      while(j < noColumns - 1)
      {
            // while(!isNaN(tableData[rowIndex][j]))
            // {
            if(!isNaN(tableData[rowIndex][j]) && tableData[rowIndex][j] != "")
            {

            sum = sum + parseInt(tableData[rowIndex][j]);
            count++;
            }
            j++;    
      }
      sum = Math.trunc(sum/(count));
      
      //If average is less than 60 or is not a number, make the cell red with white font
      if(sum <60 | isNaN(sum))
      {
            rowElement[rowIndex + 1].lastChild.style.backgroundColor = "red";
            rowElement[rowIndex + 1].lastChild.style.color = "white";
      }
      else{
            rowElement[rowIndex + 1].lastChild.style.backgroundColor = "";
            rowElement[rowIndex + 1].lastChild.style.color = "";
      }
      rowElement[rowIndex + 1].lastChild.textContent = sum;
      tableData[rowIndex][noColumns - 1] = sum;

      //Makes sure gradeStyle doesn't change.
      gradeStyle = gradeStyle - 1;
      if(gradeStyle < 0)
      {
            gradeStyle == 2;
      }

      letters(); // Make sure average fits current letter format

      
      // table.rows[]
      
}
//This function will return a random combination of first name and last name:
function randomNameGenerator(){
      var firstName = ["Obunga","Joe","Mario","Luigi","Ludwig","Ashley","Robert","Shaggy","Charlie","Freddy","Barack","Skibidi","Walter","Big","Matpat","Doctor","Daniel","Luke","Thomas","Adam","Jesse","Jaiden","Michael","Saul","Sorin"];

      var lastName = ["Obama","Biden","Mario","Fazbear","Skibidi","White","Chungus","Pinkman","Amogus","Mooney","Clooney"];

      var n1 = firstName[(Math.floor(Math.random() * firstName.length))];
      var n2 = lastName[(Math.floor(Math.random() * lastName.length))];

      return n1 + " " +n2;
}
//Counts the number of unsubmitted assignments:
function countBlank()
{
      // alert("counting")
      counting = 0;

      for(var i = 0; i < noRows; i++){
            
            
            for(var j =2; j < noColumns - 1; j++){

                  if(tableData[i][j] === "")
                  {
                        // alert(counting)
                        counting++;
                  }
            }
      }
      document.getElementById("para").innerHTML = "Number of unsubmitted assignments: "+counting;
}
//Function for converting between American letter grade, percent
function letters(){
      
      rowElement = table.rows;
      grade = "";

      //Grade style keeps track of current grade format, 4.0 grade, percent grade.
      gradeStyle++;
      if(gradeStyle > 2)
      {
            gradeStyle = 0;
      }
      for (var i = 0; i < noRows + 1; i++) {
            
            var rowNum = parseInt(tableData[i][noColumns - 1]);
            grade = "";
            
            
            switch (Number(gradeStyle)) {
                  case 0:
                        grade = parseInt(rowNum);break;
                        
                  case 1:
                        grade = letterGrade(rowNum);break;
                        
                  case 2:
                        grade = gpaGrade(rowNum);                          
            }
            rowElement[i + 1].lastChild.innerText = grade;   
      }  


}
//The american letter grade:
function letterGrade(sum)
{
      // alert(sum);
      var grade = "";
      
      if(sum > 92) grade = "A";
      else if(sum > 89) grade = "A-";
      else if(sum > 86) grade = "B+";
      else if(sum > 82) grade = "B";
      else if(sum > 79) grade = "B-";
      else if(sum > 76) grade = "C+";
      else if(sum > 72) grade = "C";
      else if(sum > 69) grade = "C-";
      else if(sum > 66) grade = "D+";
      else if(sum > 62) grade = "D";
      else if(sum > 59) grade = "D-";
      else grade = "F";
              
      return grade;

}
//The GPA grade.
function gpaGrade(sum)
{
      var grade = "";
      // alert(sum);
      if(sum > 92) grade = "4.0";
      else if(sum > 89) grade = "3.7";
      else if(sum > 86) grade = "3.3";
      else if(sum > 82) grade = "3.0";
      else if(sum > 79) grade = "2.7";
      else if(sum > 76) grade = "2.3";
      else if(sum > 72) grade = "2.0";
      else if(sum > 69) grade = "1.7";
      else if(sum > 66) grade = "1.3";
      else if(sum > 62) grade = "1.0";
      else if(sum > 59) grade = "0.7";
      else grade = "0.0";

      return grade;
}
