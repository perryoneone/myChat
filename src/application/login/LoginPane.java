package application.login;

/**
 * 登陆界面布局容器
 */
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.register.Register;
import application.utils.Toast;

public class LoginPane extends Pane{
	Stage stage;
	public LoginPane() {
		VBox vb = new VBox();
		this.getChildren().add(vb);
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
		logo.getStyleClass().add("logo");
		StackPane.setMargin(logo, new Insets(-75, 310, 0, 0));
		
		//最小化按钮
		Button minimizeWindow = new Button();
		minimizeWindow.setPrefSize(30, 30);
		minimizeWindow.setId("minimizeWindow");
		minimizeWindow.setCursor(Cursor.HAND);
		/**
		 * 最小化按钮事件
		 */
		minimizeWindow.setOnMouseClicked((e)->{
			stage = (Stage) minimizeWindow.getScene().getWindow();
			stage.setIconified(true);
		});
		StackPane.setMargin(minimizeWindow, new Insets(-85, 0, 0, 330));
		
		//关闭窗口
		Button closeWindow = new Button();
		closeWindow.setPrefSize(30, 30);
		closeWindow.setId("closeWindow");
		closeWindow.setCursor(Cursor.HAND);
		/**
		 * 关闭窗口按钮事件
		 */
		closeWindow.setOnMouseClicked((e)->{
			stage = (Stage) closeWindow.getScene().getWindow();
			stage.close();
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
		userName.setTooltip(new Tooltip("请输入账号"));
		
		nameBox.getChildren().addAll(nameLabel,userName);
		VBox.setMargin(nameBox, new Insets(20, 50, 0, 50));
		
		//密码输入框
		HBox pwdBox = new HBox();
		pwdBox.setPrefSize(430, 40);
		
		Label pwdLabel = new Label("密码：");
		pwdLabel.setPrefSize(40, 40);
		PasswordField password = new PasswordField();
		password.setPrefSize(280, 40);
		password.setPromptText("密码");
		password.setTooltip(new Tooltip("请输入密码"));
		pwdBox.getChildren().addAll(pwdLabel,password);
		VBox.setMargin(pwdBox, new Insets(15, 50, 0, 50));
		
		
		//登陆
		HBox loginBox = new HBox();
		loginBox.setPrefSize(200, 30);
		
		Button loginBtn = new Button("登陆");
		loginBtn.setPrefSize(230, 30);
		loginBtn.getStyleClass().add("login-button");
		
		loginBox.getChildren().add(loginBtn);
		/**
		 * 登陆点击事件处理
		 *
		 */
		loginBtn.setOnAction(event -> {
			Toast toastr = new Toast(loginBtn.getScene().getWindow());
			try {
				if(userName.getText().equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "账号不能为空!");
					
				}else if(password.getText().equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "密码不能为空!");
				}else {
					new doLogin((Stage)loginBtn.getScene().getWindow(),userName.getText(),password.getText());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		HBox.setMargin(loginBtn, new Insets(0, 0, 0, 50));
		VBox.setMargin(loginBox, new Insets(20, 50, 0, 50));
		
		//注册
		HBox regBox = new HBox();
		regBox.setPrefSize(200, 30);
		
		Button regBtn = new Button("注册账号");
		regBtn.getStyleClass().add("reg");
		regBtn.setPrefSize(70, 30);
		regBtn.setCursor(Cursor.HAND);
		/**
		 * 注册按钮事件
		 */
		regBtn.setOnAction((e) -> {
			try {
				Stage stage = (Stage)regBtn.getScene().getWindow();
				stage.close();
				new Register().showRegister();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		HBox.setMargin(regBtn, new Insets(0, 0, 0, 6));
		
		ImageView erweima = new ImageView(new Image("/application/res/icon_erweima.png"));
		erweima.setFitHeight(30);
		erweima.setFitWidth(30);
		regBox.getChildren().addAll(regBtn,erweima);
		HBox.setMargin(erweima, new Insets(-5, 0, 0, 315));
		
		vb.getChildren().addAll(stack,nameBox,pwdBox,loginBox,regBox);
	}
}
