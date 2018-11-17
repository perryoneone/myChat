package application.register;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 用户注册界面
 * @author 赵政
 *
 */
public class Register extends Application{
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
		primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
        primaryStage.setResizable(false); // 设置窗口不可改变大小
        
		VBox root = new RegisterPane();
		
        Scene scene = new Scene(root, 430, 500);
        scene.getStylesheets().add(getClass().getResource("register.css").toExternalForm());
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
	
	public void  showRegister() throws Exception {
        start(stage);
    }

}
