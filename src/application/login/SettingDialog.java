package application.login;

import java.util.regex.Pattern;

import application.dialog.StyleDialog;
import application.utils.Toast;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingDialog extends StyleDialog
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
				ipAddr.setUserData(ip);
				port.setUserData(portID);
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
		if(ipAddr.getUserData() != null) {
			ipAddr.setText(ipAddr.getUserData().toString());
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
		if(port.getUserData() != null) {
			port.setText(port.getUserData().toString());
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
