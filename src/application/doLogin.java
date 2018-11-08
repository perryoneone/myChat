package application;

import javafx.stage.Stage;

public class doLogin {
    public doLogin(Stage stage,String userName,String pwd) throws Exception {
        stage.close();
        System.out.println(userName + " : " + pwd);
        Main main = new Main();
        main.showChat();
    }
}
