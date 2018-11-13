package application.chat;
/**
 * 聊天室界面
 * @author 赵政
 *
 */
import application.register.RegisterPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Chat extends Application{
	Stage stage = new Stage();
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
        primaryStage.setResizable(false); // 设置窗口不可改变大小
        
		VBox root = new RegisterPane();
		
        Scene scene = new Scene(root, 430, 500);
        scene.getStylesheets().add(getClass().getResource("register.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void  showRegister() throws Exception {
        start(stage);
    }

}
