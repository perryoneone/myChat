package application.login;

import application.chat.ChatRoom;
import application.utils.Toast;
import javafx.stage.Stage;

public class HandleLogin {
    public HandleLogin(Stage stage,String userName,String pwd) throws Exception {
    	Toast toast = new Toast(stage);
    	if(true) {
    		Toast.Level level = Toast.Level.values()[0];
			toast.show(level, 1000, "登陆成功!");
    		stage.close();
            System.out.println(userName + " : " + pwd);
            ChatRoom room = new ChatRoom();
            room.showChat();
    	}
    }
}
