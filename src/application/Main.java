package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			VBox vb = new VBox();
			root.getChildren().add(vb);
			vb.setPrefSize(430, 330);
			
			StackPane stack = new StackPane();
			stack.setPrefSize(430, 127);
			
			vb.getChildren().add(stack);
			
			ImageView topBg = new ImageView(new Image("file:///C:/Users/%E8%B5%B5%E6%94%BF/Desktop/%E4%B8%8B%E8%BD%BD.jpg"));
			topBg.setFitHeight(127);
			topBg.setFitWidth(430);
			
			ImageView loginIcon = new ImageView(new Image("file:///D:/360%E5%AE%89%E5%85%A8%E6%B5%8F%E8%A7%88%E5%99%A8%E4%B8%8B%E8%BD%BD/QQ%20(3).png"));
			loginIcon.setFitHeight(30);
			loginIcon.setFitWidth(50);
			
			StackPane.setMargin(loginIcon, new Insets(-75, 375, 0, 0));
			
			Label logo = new Label("QQ");
			StackPane.setMargin(logo, new Insets(-75, 310, 0, 0));
			
			ImageView minimizeWindow = new ImageView(new Image("file:///D:/360%E5%AE%89%E5%85%A8%E6%B5%8F%E8%A7%88%E5%99%A8%E4%B8%8B%E8%BD%BD/%E6%9C%80%E5%B0%8F%E5%8C%96%20(2).png"));
			minimizeWindow.setFitHeight(15.0);
			minimizeWindow.setFitWidth(15.0);
			StackPane.setMargin(minimizeWindow, new Insets(-85, 330, 0, 0));
			
			ImageView closeWindow = new ImageView(new Image("file:///D:/360%E5%AE%89%E5%85%A8%E6%B5%8F%E8%A7%88%E5%99%A8%E4%B8%8B%E8%BD%BD/%E5%85%B3%E9%97%AD.png"));
			closeWindow.setFitHeight(15);
			closeWindow.setFitWidth(15);
			StackPane.setMargin(closeWindow, new Insets(-85, 390, 0, 0));
			
			stack.getChildren().addAll(topBg,loginIcon,logo,minimizeWindow,closeWindow);
			
			primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
		      
	        primaryStage.setResizable(false); // 设置窗口不可改变大小
	        
	        Scene scene = new Scene(root, 430, 330);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        //scene.getStylesheets().add("my/application.css");
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
