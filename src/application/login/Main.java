package application.login;

/**
 * 聊天室主界面
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Stage stage = new Stage();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
        primaryStage.setResizable(true); // 设置窗口不可改变大小

        //主容器
        Pane root = new MainPane();

        
        Scene scene = new Scene(root,360,718);
        scene.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void  showChat() throws Exception {
        start(stage);
    }
}
