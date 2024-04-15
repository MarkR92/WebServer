  //**********************GLOBAL VARIABLES:*********************** */ 


   var host = "http://localhost:8000";
   var currentPort;

//**********************JQUERY AJAX SIDE OF THE CODE:*********************** */

   //var elements = document.getElementsByClass("selectPort");

   // POST FUNCTION
   $(document).ready(function(){
    
    $('.upload').unbind('submit').submit(function (e) {
        
        //-----------POST SECTION----------:
        if($.isNumeric($('.selectPort').val()) == false)
        {
        alert("Website created.");
        $.ajax({

            type: "POST",
            url: "http://localhost:8000/upload",
            crossDomain: true,
            data:  new FormData(this),
            dataType: "json",
            contentType: "multipart/form-data",
            processData: false,
            // contentType: false,
            headers: {
            "Accept": "application/json"
        }
        
    })
    .done(function (data) {
        alert("Post");
        $('input[name=title]').val('');
    });
    }
    //-----------PUT SECTION----------:
    else{
      alert("Website on port "+currentPort+" updated.");
      $.ajax({

        type: "PUT",
        url: "http://localhost:8000/update/"+currentPort,
        crossDomain: true,
        data:  new FormData(this),
        dataType: "json",
        contentType: "multipart/form-data",
        processData: false,
        // contentType: false,
        headers: {
        "Accept": "application/json"
        }
      })
      .done(function (data) {
          alert("Post");
          $('input[name=title]').val('');
      });

    }
    e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                    // meand that clicked submit button will not take the browser to a new URL.
                
                }); 
                
// RETRIEVE FUNCTTON
$('.refresh').unbind('refresh').submit(function (e) {
    var port = $('input[name=quantity]').val();
    var myUrl = host+"/url";
    $.ajax({
        type: "GET",
        url: myUrl,
    })
    .done(function (data) {
       // console.log(data);
        let text = data; 
        document.getElementById('port').innerHTML = text; 
        //document.createElement(data);
    });
    e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                        // meand that clicked submit button will not take the browser to a new URL.
    });

});

//**********************REGULAR JAVASCRIPT STARTS HERE:*********************** */

// DELETE FUNCTION IN HERE TO TRY 0_0
setInterval(getRegular, 1000);
function getRegular()
{
var myUrl = host+"/url";
    $.ajax({
        type: "GET",
        url: myUrl,
    })
    .done(function (data) {
       // console.log(data);
        let text = data; 
        //var elements = document.getElementsByClass("selectPort");
        document.getElementById('port').innerHTML = text; 

    });
}
function test( port)
{

console.log(port);

                var myUrl = host+"/delete/"+port;

                $.ajax({
                    type: "DELETE",
                    url: myUrl,
                })
                // .done(function (data) {
                //     alert("Deleted WebPage on: "+port);
                //     $('input[name=title]').val('');
                // });
               // e.preventDefault(); // when method is called, the default action of the event will not be triggered.
                                    // meand that clicked submit button will not take the browser to a new URL.
}

  function select(port){
    
    document.querySelector('.selectPort').style.background = "red";

    currentPort = ""+port+"";

    document.getElementById(port).style.background = "yellow";
    
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