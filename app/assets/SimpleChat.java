package assets;     

import play.mvc.*;
import play.libs.*;
import play.libs.F.*;

import java.util.*;

public class SimpleChat{

  
    private static List<WebSocket.Out<String>> connections = new ArrayList<WebSocket.Out<String>>();  
    public static void start(WebSocket.In<String> in, WebSocket.Out<String> out)
    {
        
        connections.add(out);    
        in.onMessage(new Callback<String>()
        {
            public void invoke(String event){
                SimpleChat.notifyAll(event);
            }
        });
        
        in.onClose(new Callback0()
        {
            public void invoke(){
                SimpleChat.notifyAll("A connection closed");
            }
        });
    }
        public static void notifyAll(String message){
        for (WebSocket.Out<String> out : connections) {
            out.write(message);
        }
    }
}