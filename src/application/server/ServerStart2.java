package application.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class ServerStart2 extends Application
{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("ServerFrame2.fxml"));
		primaryStage.setTitle("TCP聊天室服务器"); // 设置窗口标题
	    primaryStage.getIcons().add(new Image("/application/res/qq_icon.png")); // 设置窗口icon图标
	    primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show(); 
	}

}
