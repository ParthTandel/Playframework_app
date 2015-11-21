

$(function(){


    $('<audio id="chatAudio"><source src="@routes.Application.audiomp3()" type="audio/ogg"><source src="public/audio/notify.mp3" type="audio/mpeg"><source src="public/audio/notify.wav" type="audio/wav"></audio>').appendTo('body');
    // get websocket class, firefox has a different way to get it
    var WS = window['MozWebSocket'] ? window['MozWebSocket'] : WebSocket;
    
    // open pewpew with websocket
    var socket = new WS('@routes.Application.wsInterface().webSocketURL(request)');
   
    var writeMessages = function(event){
    var currentTime = new Date();
    var time = currentTime.getTime();
    var hours = currentTime.getHours();
        var res = event.data.split(" ");
        console.log(res[0]);
        if(res[0] != document.getElementById("name").value)
            $('#chatAudio')[0].play();
     //   $('#socket-messages').prepend('<p>'+currentTime+'</p>');
        $('#socket-messages').prepend('<p>'+event.data+'</p>');

    }   
    
    socket.onmessage = writeMessages;
    
    $('#socket-input').keyup(function(event){
        var charCode = (event.which) ? event.which : event.keyCode ;
       
        // if enter (charcode 13) is pushed, send message, then clear input field
        if(charCode === 13){
            socket.send(document.getElementById("name").value+'           :        '+$(this).val());
            $(this).val('');    
        }
    }); 
});
