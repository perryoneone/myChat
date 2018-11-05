package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Login extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景
	        primaryStage.setResizable(false); // 设置窗口不可改变大小
	        
			Pane root = new Pane();
			
			VBox vb = new VBox();
			root.getChildren().add(vb);
			vb.setPrefSize(430, 330);
			
			StackPane stack = new StackPane();
			stack.setPrefSize(430, 127);
			
			
			//顶部背景
			ImageView topBg = new ImageView(new Image("/application/res/background.jpg"));
			topBg.setFitHeight(127);
			topBg.setFitWidth(430);
			//顶部icon
			ImageView loginIcon = new ImageView(new Image("/application/res/qq.png"));
			loginIcon.setFitHeight(30);
			loginIcon.setFitWidth(30);
			StackPane.setMargin(loginIcon, new Insets(-75, 375, 0, 0));
			
			//顶部logo
			Label logo = new Label("QQ");
			StackPane.setMargin(logo, new Insets(-75, 310, 0, 0));
			
			//最小化按钮
			Button minimizeWindow = new Button();
			minimizeWindow.setPrefSize(30, 30);
			minimizeWindow.setId("minimizeWindow");
			/**
			 * 最小化按钮事件
			 */
			minimizeWindow.setOnMouseClicked((e)->{
				primaryStage.setIconified(true);
			});
			StackPane.setMargin(minimizeWindow, new Insets(-85, 0, 0, 330));
			
			//关闭窗口
			Button closeWindow = new Button();
			closeWindow.setPrefSize(30, 30);
			closeWindow.setId("closeWindow");
			/**
			 * 关闭窗口按钮事件
			 */
			closeWindow.setOnMouseClicked((e)->{
				primaryStage.close();
			});
			StackPane.setMargin(closeWindow, new Insets(-85, 0, 0, 390));
			
			stack.getChildren().addAll(topBg,loginIcon,logo,minimizeWindow,closeWindow);
			
			//账号输入框
			HBox nameBox = new HBox();
			nameBox.setPrefSize(430, 40);
			
			Label nameLabel = new Label("账号：");
			nameLabel.setPrefSize(40, 40);
			TextField userName = new TextField();
			userName.setPrefSize(280, 40);
			userName.setPromptText("账号");
			
			nameBox.getChildren().addAll(nameLabel,userName);
			VBox.setMargin(nameBox, new Insets(20, 50, 0, 50));
			
			//密码输入框
			HBox pwdBox = new HBox();
			pwdBox.setPrefSize(430, 40);
			
			Label pwdLabel = new Label("密码：");
			pwdLabel.setPrefSize(40, 40);
			TextField password = new TextField();
			password.setPrefSize(280, 40);
			password.setPromptText("账号");
			
			pwdBox.getChildren().addAll(pwdLabel,password);
			VBox.setMargin(pwdBox, new Insets(15, 50, 0, 50));
			
			
			//登陆
			HBox loginBox = new HBox();
			loginBox.setPrefSize(200, 30);
			
			Button loginBtn = new Button("登陆");
			loginBtn.setPrefSize(230, 30);
			loginBtn.getStyleClass().add("login-button");
			
			loginBox.getChildren().add(loginBtn);
			HBox.setMargin(loginBtn, new Insets(0, 0, 0, 50));
			VBox.setMargin(loginBox, new Insets(20, 50, 0, 50));
			
			//注册
			HBox regBox = new HBox();
			regBox.setPrefSize(200, 30);
			
			Label regBtn = new Label("注册账号");
			HBox.setMargin(regBtn, new Insets(8, 0, 0, 15));
			
			ImageView erweima = new ImageView(new Image("/application/res/icon_erweima.png"));
			erweima.setFitHeight(30);
			erweima.setFitWidth(30);
			regBox.getChildren().addAll(regBtn,erweima);
			HBox.setMargin(erweima, new Insets(-5, 0, 0, 325));
			
			vb.getChildren().addAll(stack,nameBox,pwdBox,loginBox,regBox);
			
	        Scene scene = new Scene(root, 430, 330);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
