package controllers;

import play.*;
import play.mvc.*;
import assets.SimpleChat;
import assets.user;
import views.html.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import play.data.Form;

public class Application extends Controller {
  
    final static Form<user> userForm = Form.form(user.class);
    public static Result index() 
    {
        if(session("connected") == null)
        {
            return signin();
        }
        else
        {
            return ok(index.render(session("connected")));
        }
    }

    public static Result signin() 
    {
        return ok(signin.render(""));
    }

    public static Result logout()
    {
        if(session("connected") != null)
            session().remove("connected");
        System.out.println("here");
        return ok(signin.render(""));   
    }

    public static Result submit()
    { 
        Form<user> filledForm = userForm.bindFromRequest();
        user created = filledForm.get();
        if(created.password .equals("devilisalie") && (created.username.equals("parth") || created.username.equals("kofti")))
        {
            session("connected" , created.username );
            return redirect(controllers.routes.Application.index());
        }
        else
        {
            return ok(signin.render("Invalid Username or Password"));
        }
    }
        public static Result wsJs() {
        return ok(views.js.ws.render());
    }
    
    public static Result audiomp3() {
    File wavFile = new File("/home/tyson/Try_websockets/public/audio/notify.mp3");
    return ok(wavFile);
    }

    public static Result audioogg() {
    File wavFile = new File("/home/tyson/Try_websockets/public/audio/notify.ogg");
    return ok(wavFile);
    }

    public static Result audiowav() {
    File wavFile = new File("/home/tyson/Try_websockets/public/audio/notify.wav");
    return ok(wavFile);
    }


   public static WebSocket<String> wsInterface(){
        return new WebSocket<String>(){
                public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out){
                SimpleChat.start(in, out);
            }
        };   
}

}
