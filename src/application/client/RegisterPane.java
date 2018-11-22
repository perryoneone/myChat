package application.client;
/**
 * 注册界面布局容器
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import application.model.User;
import application.utils.Toast;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegisterPane extends VBox{
	Stage stage;
	public RegisterPane() {
		HBox top = new HBox();
		top.setPrefSize(430, 50);
		top.setStyle("-fx-background-color: lightblue;");
		
		Label logo = new Label("用户注册");
		logo.setFont(new Font(16));
		
		HBox.setMargin(logo, new Insets(10, 0, 0, 10));
		
		//关闭窗口
		Button closeWindow = new Button();
		closeWindow.setPrefSize(30, 30);
		closeWindow.setId("closeWindow");
		closeWindow.setCursor(Cursor.HAND);
		HBox.setMargin(closeWindow, new Insets(10, 0, 0, 315));
		/**
		 * 关闭窗口按钮事件
		 */
		closeWindow.setOnMouseClicked((e)->{
			stage = (Stage) closeWindow.getScene().getWindow();
			stage.close();
		});
		
		top.getChildren().addAll(logo,closeWindow);
		
		VBox main = new VBox();
		main.setPrefSize(430, 450);
		main.setStyle("-fx-background-color: rgba(0,0,255,0.2);");
		
		/**
		 * 昵称
		 */
		HBox box1 = new HBox();
		box1.setPrefSize(370, 50);
		VBox.setMargin(box1, new Insets(30, 40, 0, 40));
		
		Label label1 = new Label("昵称：");
		label1.setAlignment(Pos.CENTER_RIGHT);
		label1.setPrefSize(70, 20);
		label1.setFont(new Font(14));
		label1.requestFocus();
		HBox.setMargin(label1, new Insets(15, 0, 0, 0));
		
		TextField name = new TextField();
		name.setPrefSize(250, 35);
		HBox.setMargin(name, new Insets(8, 0, 0, 10));
		
		box1.getChildren().addAll(label1, name);
		
		/**
		 * 密码
		 */
		HBox box2 = new HBox();
		box2.setPrefSize(370, 50);
		VBox.setMargin(box2, new Insets(10, 40, 0, 40));
		
		Label label2 = new Label("密码：");
		label2.setAlignment(Pos.CENTER_RIGHT);
		label2.setPrefSize(70, 20);
		label2.setFont(new Font(14));
		HBox.setMargin(label2, new Insets(15, 0, 0, 0));
		
		PasswordField pwd = new PasswordField();
		pwd.setPrefSize(250, 35);
		HBox.setMargin(pwd, new Insets(8, 0, 0, 10));
		
		box2.getChildren().addAll(label2, pwd);
		/**
		 * 确认密码
		 */
		HBox box3 = new HBox();
		box3.setPrefSize(370, 50);
		VBox.setMargin(box3, new Insets(10, 40, 0, 40));
		
		Label label3 = new Label("确认密码：");
		label3.setAlignment(Pos.CENTER_RIGHT);
		label3.setPrefSize(70, 20);
		label3.setFont(new Font(14));
		HBox.setMargin(label3, new Insets(15, 0, 0, 0));
		
		PasswordField confirmPwd = new PasswordField();
		confirmPwd.setPrefSize(250,35);
		HBox.setMargin(confirmPwd, new Insets(8, 0, 0, 10));
		
		box3.getChildren().addAll(label3, confirmPwd);
		
		/**
		 * 性别
		 */
		HBox box4 = new HBox();
		box4.setPrefSize(370, 50);
		VBox.setMargin(box4, new Insets(10, 40, 0, 40));
		
		Label label4 = new Label("性别：");
		label4.setAlignment(Pos.CENTER_RIGHT);
		label4.setPrefSize(70, 20);
		label4.setFont(new Font(14));
		HBox.setMargin(label4, new Insets(15, 0, 0, 0));
		
		ToggleGroup group = new ToggleGroup();
	    RadioButton button1 = new RadioButton("男");
	    button1.setFont(new Font(14));
	    button1.setToggleGroup(group);
	    button1.setUserData("男");
	    button1.setSelected(true);
	    HBox.setMargin(button1, new Insets(15, 0, 0, 30));
	    RadioButton button2 = new RadioButton("女");
	    button2.setToggleGroup(group);
	    button2.setFont(new Font(14));
	    button2.setUserData("女");
	    HBox.setMargin(button2, new Insets(15, 0, 0, 50));
		
		box4.getChildren().addAll(label4, button1, button2);
		
		/**
		 * 生日
		 */
		HBox box5 = new HBox();
		box5.setPrefSize(370, 50);
		VBox.setMargin(box5, new Insets(10, 40, 0, 40));
		
		Label label5 = new Label("生日：");
		label5.setAlignment(Pos.CENTER_RIGHT);
		label5.setPrefSize(70, 20);
		label5.setFont(new Font(14));
		HBox.setMargin(label5, new Insets(15, 0, 0, 0));
		
		DatePicker birthday = new DatePicker();
		birthday.setPrefSize(250, 35);
		birthday.setValue(LocalDate.now());
		HBox.setMargin(birthday, new Insets(8, 0, 0, 10));
		
		box5.getChildren().addAll(label5, birthday);
		
		/**
		 * 所在地
		 */
		HBox box6 = new HBox();
		box6.setPrefSize(370, 50);
		VBox.setMargin(box6, new Insets(10, 40, 0, 40));
		
		Label label6 = new Label("所在地：");
		label6.setAlignment(Pos.CENTER_RIGHT);
		label6.setPrefSize(70, 20);
		label6.setFont(new Font(14));
		HBox.setMargin(label6, new Insets(15, 0, 0, 0));
		
		TextField address = new TextField();
		address.setPrefSize(250, 35);
		HBox.setMargin(address, new Insets(8, 0, 0, 10));
		
		box6.getChildren().addAll(label6, address);
		
		/**
		 * 注册按钮
		 */
		HBox box7 = new HBox();
		box7.setPrefSize(430, 50);
		VBox.setMargin(box7, new Insets(10, 0, 0, 0));
		// 取消按钮
		Button cancel = new Button("取消");
		cancel.setPrefSize(90, 40);
		cancel.setStyle("-fx-background-color: rgb(6,105,178);-fx-text-fill: white;");
		cancel.setFont(new Font(14));
		cancel.setCursor(Cursor.HAND);
		/**
		 * 取消事件处理
		 */
		cancel.setOnAction((e) -> {
			Stage stage = (Stage)cancel.getScene().getWindow();
			stage.close();
		});
		HBox.setMargin(cancel, new Insets(5, 0, 0, 40));
		
		
		// 注册按钮
		Button register = new Button("注册");
		register.setPrefSize(90, 40);
		register.setStyle("-fx-background-color: rgb(6,105,178);-fx-text-fill: white;");
		register.setFont(new Font(14));
		register.setCursor(Cursor.HAND);
		
		/**
		 * 登陆点击事件处理
		 *
		 */
		register.setOnAction(event -> {
			Toast toastr = new Toast(register.getScene().getWindow());
			try {
				String nickname = name.getText();
				String password = pwd.getText();
				String confirmPassword = confirmPwd.getText();
				String sex = (String)group.getSelectedToggle().getUserData();
				String birth = birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String addr = address.getText();
				if(nickname.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "昵称不能为空!");
					name.requestFocus();
				}else if(password.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "密码不能为空!");
					pwd.requestFocus();
				}else if(confirmPassword.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "请再次确认密码!");
					confirmPwd.requestFocus();
				}else if(addr.equals("")) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "居住地不能为空!");
					address.requestFocus();
				}else if(!password.equals(confirmPassword)) {
					Toast.Level level = Toast.Level.values()[1];
					toastr.show(level, 1000, "两次密码不一致!");
					pwd.requestFocus();
				}else {
					User user = new User();
					user.setNickname(nickname);
					user.setPwd(password);
					user.setSex(sex);
					user.setBirthday(birth);
					user.setAddress(addr);
					System.out.println(nickname+"-"+sex);
					new HandleRegister((Stage)register.getScene().getWindow(),user);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		HBox.setMargin(register, new Insets(5, 0, 0, 170));
		
		box7.getChildren().addAll(cancel, register);
		
		main.getChildren().addAll(box1,box2,box3,box4,box5,box6,box7);
		
		this.getChildren().addAll(top,main);
	}
}
