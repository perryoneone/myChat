package application.login;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 * 登陆界面布局容器
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Pattern;

import application.dialog.StyleDialog;
import application.register.Register;
import application.utils.Toast;

public class LoginPane extends Pane{
	Stage stage;
	TextField userName;
	PasswordField password;
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
		
		//登陆设置按钮
		Button setting  = new Button();
		setting.setPrefSize(30, 30);
		setting.setId("setting");
		setting.setCursor(Cursor.HAND);
		StackPane.setMargin(setting, new Insets(-85, 0, 0, 270));
		
		/**
		 * 登陆设置按钮事件
		 */
		setting.setOnAction((e) -> {
			this.settingDialog(setting);
		});
		
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
		
		stack.getChildren().addAll(topBg,loginIcon,logo,setting,minimizeWindow,closeWindow);
		
		//账号输入框
		HBox nameBox = new HBox();
		nameBox.setPrefSize(430, 40);
		
		Label nameLabel = new Label("账号：");
		nameLabel.setPrefSize(40, 40);
		userName = new TextField();
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
		password = new PasswordField();
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
					userName.requestFocus();
				}else if(password.getText().equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "密码不能为空!");
					password.requestFocus();
				}else {
					new HandleLogin((Stage)loginBtn.getScene().getWindow(),loginBtn,userName,password);
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
	/**
	 * 登陆设置对话框
	 */
	public void settingDialog(Button btn)
	{
		SettingDialog dlg = new SettingDialog(btn);
		
		// dlg.exec() 返回true或false
		if(dlg.exec())
		{
			System.out.println(dlg.ipAddr.getText() + "::" + Integer.parseInt(dlg.port.getText()));
			userName.setUserData(dlg.ipAddr.getText());
			password.setUserData(dlg.port.getText());
		}
	}
	
	class SettingDialog extends StyleDialog
	{
		Label label1 = new Label("ip地址：");
		TextField ipAddr = new TextField();
		
		Label label2 = new Label("端口号：");
		TextField port = new TextField();
		Button buttonCancel  = new Button("取消");
		Button buttonOK  = new Button("确定");
		
		// 
		public SettingDialog(Node anchor)
		{
			super(anchor);
			
			//布局
			initLayout();
			
			/**
			 * 文本框监听事件，用户只能输入数字
			 */
			ipAddr.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("[\\d*|\\.]")) { 
						ipAddr.setText(newValue.replaceAll("[^\\d|\\.]", "")); 
					} 
					
				}
			});
			
			port.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")) { 
						port.setText(newValue.replaceAll("[^\\d]", "")); 
					} 
					
				}
			});
			
			// 点击确定按钮时，关闭对话框
			buttonOK.setOnAction((e)->{
				Toast toastr = new Toast(buttonOK.getScene().getWindow());
				String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; // ip地址正则
				String portRegex = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";
				String ip = ipAddr.getText();
				String portID = port.getText();
				if(ip.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "ip地址不能为空!");
					ipAddr.requestFocus();
				}else if(portID.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "端口号不能为空!");
					port.requestFocus();
				}else if(!Pattern.compile(ipRegex).matcher(ip).matches()) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "ip地址不合法!!");
					ipAddr.requestFocus();
				}else if(!Pattern.compile(portRegex).matcher(portID).matches()) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "端口号不合法!!");
					port.requestFocus();
				}else {
					accept();
				}	
			});
			
			// 点击取消按钮时，关闭对话框
			buttonCancel.setOnAction((e)->{
				
				cancel();
				
			});
		}
		
		private void initLayout()
		{	
			this.setDialogTitle("登陆设置");
			
			VBox root = new VBox();
			this.setContentView(root); // 设置根容器
			root.setPrefWidth(400); // 根容器的大小决定窗口大小
			root.setPrefHeight(180);
			
			HBox box1 = new HBox();
			box1.setPrefSize(370, 50);
			VBox.setMargin(box1, new Insets(0, 40, 0, 25));
			
			label1.setAlignment(Pos.CENTER_RIGHT);
			label1.setPrefSize(70, 20);
			label1.setFont(new Font(14));
			HBox.setMargin(label1, new Insets(15, 0, 0, 0));
			
			ipAddr.setPrefSize(250, 35);
			
			if(userName.getUserData() != null) {
				ipAddr.setText((String)userName.getUserData());
			}else {
				ipAddr.setText("127.0.0.1"); // 设置默认ip地址
			}
			HBox.setMargin(ipAddr, new Insets(8, 0, 0, 10));
			box1.getChildren().addAll(label1, ipAddr);
			
			HBox box2 = new HBox();
			box2.setPrefSize(370, 50);
			VBox.setMargin(box2, new Insets(10, 40, 0, 25));
			
			label2.setAlignment(Pos.CENTER_RIGHT);
			label2.setPrefSize(70, 20);
			label2.setFont(new Font(14));
			HBox.setMargin(label2, new Insets(15, 0, 0, 0));
			
			port.setPrefSize(250, 35);
			if(password.getUserData() != null) {
				port.setText((String)password.getUserData());
			}else {
				port.setText("9999"); // 设置默认端口号
			}
			HBox.setMargin(port, new Insets(8, 0, 0, 10));
			box2.getChildren().addAll(label2, port);
			
			HBox box3 = new HBox();
			box3.setPrefSize(400, 50);
			VBox.setMargin(box3, new Insets(10, 0, 0, 0));
			buttonCancel.setPrefSize(90, 40);
			buttonCancel.setStyle("-fx-background-color: rgb(6,105,178);-fx-text-fill: white;");
			buttonCancel.setFont(new Font(14));
			buttonCancel.setCursor(Cursor.HAND);
			HBox.setMargin(buttonCancel, new Insets(5, 0, 0, 40));
			
			
			buttonOK.setPrefSize(90, 40);
			buttonOK.setStyle("-fx-background-color: rgb(6,105,178);-fx-text-fill: white;");
			buttonOK.setFont(new Font(14));
			buttonOK.setCursor(Cursor.HAND);
			HBox.setMargin(buttonOK, new Insets(5, 0, 0, 150));
			box3.getChildren().addAll(buttonCancel,buttonOK);
			
			root.getChildren().addAll( box1,box2,box3 );
		}
	}
}
