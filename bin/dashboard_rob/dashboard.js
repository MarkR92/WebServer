

   //**********************GLOBAL VARIABLES:*********************** */ 


   var host = "http://localhost:8000";
   var currentPort;

//**********************JQUERY AJAX SIDE OF THE CODE:*********************** */

   //var elements = document.getElementsByClass("selectPort");
  

   //var myurl="http://localhost:8000/"
   getRegular();
   $(document).ready(function(){
   
    $('.upload').unbind('submit').submit(function (e) {
        
      //-----------POST SECTION----------:
      if($.isNumeric($('.selectPort').val()) == false)
      {
        //alert("Website created.");
        $.ajax({

            type: "POST",
            url: host+"/upload",
            crossDomain: true,
            async: false,
            data:  new FormData(this),
            contentType: "multipart/form-data",
            processData: false,
          
        
      }).done(function (data) {
        // console.log(data);
        let text = data; 
        document.getElementById('port').innerHTML = text; 
     });
     }
  //-----------PUT SECTION----------:
    else{
      alert("Website on port "+currentPort+" updated.");
      $.ajax({

        type: "PUT",
        url: host+"/update/"+currentPort,
        crossDomain: true,
        data:  new FormData(this),
        //dataType: "json",
        contentType: "multipart/form-data",
        processData: false,
        // contentType: false,
        headers: {
        "Accept": "application/json"
        }
      })
    }
  e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                  // meand that clicked submit button will not take the browser to a new URL.
              
    }); 
                
// RETRIEVE FUNCTTON
  
$('.refresh').unbind('refresh').submit(function (e) {
    var port = $('input[name=quantity]').val();
  //  var myUrl = "http://20.84.89.246:8000/url";
    $.ajax({
        type: "GET",
        url: host+"/url",
    })
    .done(function (data) {
        console.log(data);
        let text = data; 
        document.getElementById('port').innerHTML = text; 
    });
    e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                        // meand that clicked submit button will not take the browser to a new URL.
    });
});


// DELETE FUNCTION IN HERE TO TRY 0_0
//setInterval(getRegular, 4000);
function getRegular()
{
//var myUrl = "http://20.84.89.246:8000/url";
    $.ajax({
        type: "GET",
        url: host+"/url",
    })
    .done(function (data) {
       // console.log(data);
        let text = data; 
        document.getElementById('port').innerHTML = text; 
    });
}
function test( port)
{

console.log(port);

            //    var myUrl = "http://20.84.89.246:8000/delete/"+port;

                $.ajax({
                    type: "DELETE",
                    url: host+"/delete/"+port,
                    processData: false,
        // contentType: false,
                  headers: {
                  "Accept": "application/json"
                  }
                    // crossDomain: true,
                    // async: false,
                    //dataType: "json",
                    //contentType: "multipart/form-data",
                    //processData: false,
                    // contentType: false,
              
                })
                .done(function (data) {
                  
                    let text = data; 
                    document.getElementById('port').innerHTML = text; 
                    
                });
               //e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                                    // meand that clicked submit button will not take the browser to a new URL.
}

function select(port){
    //alert(port);
  
  var elements = document.getElementsByClassName('selectPort');

  for (var i = 0; i < elements.length; i++) {
      elements[i].style ="";
  }

  currentPort = ""+port+"";

  document.getElementById(port).style.background = "rgb(223, 194, 107)";
  document.getElementById(port).style.color = "white";
  
  $(document).ready(function(){

    $('.selectPort').val(currentPort);  

  });
}


    // js script to make the drop functional, im not sure if it works tbh
  const dropContainer = document.getElementById("dropcontainer")
  const fileInput = document.getElementById("file")

    dropContainer.addEventListener("dragover", (e) => {
      // prevent default to allow drop
      e.preventDefault()
    }, false)

    dropContainer.addEventListener("dragenter", () => {
      dropContainer.classList.add("drag-active")
    })

    dropContainer.addEventListener("dragleave", () => {
      dropContainer.classList.remove("drag-active")
    })

    dropContainer.addEventListener("drop", (e) => {
      e.preventDefault()
      dropContainer.classList.remove("drag-active")
      fileInput.files = e.dataTransfer.files
    })