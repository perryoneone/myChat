package application.login;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Login extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
	        primaryStage.setResizable(false); // 设置窗口不可改变大小
	        
			Pane root = new LoginPane();
			
	        Scene scene = new Scene(root, 430, 330);
	        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
