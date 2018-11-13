package application.chat;

/**
 * 聊天室主界面
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChatRoom extends Application {
	double x1;
	double y1;
	double x_stage;
	double y_stage;
	Stage stage = new Stage();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ChatRoom.fxml"));
		primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景

		Scene scene = new Scene(root, 1300, 800);
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		//设置窗口可拖动
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {

				// 计算
				stage.setX(x_stage + m.getScreenX() - x1);
				stage.setY(y_stage + m.getScreenY() - y1);

			}
		});
		scene.setOnDragEntered(null);
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent m) {
				// 按下鼠标后，记录当前鼠标的坐标
				x1 = m.getScreenX();
				y1 = m.getScreenY();
				x_stage = stage.getX();
				y_stage = stage.getY();
			}
		});

	}

	public void showChat() throws Exception {
		start(stage);
	}
}
