package application.client;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ClientStart extends Application {
	double x1;
	double y1;
	double x_stage;
	double y_stage;
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
	        
	      //设置窗口可拖动
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent m) {

					// 计算
					primaryStage.setX(x_stage + m.getScreenX() - x1);
					primaryStage.setY(y_stage + m.getScreenY() - y1);

				}
			});
			scene.setOnDragEntered(null);
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent m) {
					// 按下鼠标后，记录当前鼠标的坐标
					x1 = m.getScreenX();
					y1 = m.getScreenY();
					x_stage = primaryStage.getX();
					y_stage = primaryStage.getY();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
