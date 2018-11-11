package application;

import application.utils.Toast;
import javafx.stage.Stage;

public class doLogin {
    public doLogin(Stage stage,String userName,String pwd) throws Exception {
    	Toast toast = new Toast(stage);
    	if(true) {
    		Toast.Level level = Toast.Level.values()[0];
			toast.show(level, 1000, "登陆成功!");
    		stage.close();
            System.out.println(userName + " : " + pwd);
            Main main = new Main();
            main.showChat();
    	}
    }
}
