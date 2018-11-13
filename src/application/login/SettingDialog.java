package application.login;

import application.dialog.StyleDialog;
import application.utils.Toast;
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
		
		// 点击确定按钮时，关闭对话框
		buttonOK.setOnAction((e)->{
			Toast toastr = new Toast(buttonOK.getScene().getWindow());
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
		VBox.setMargin(box1, new Insets(10, 40, 0, 40));
		
		label1.setAlignment(Pos.CENTER_RIGHT);
		label1.setPrefSize(70, 20);
		label1.setFont(new Font(14));
		HBox.setMargin(label1, new Insets(15, 0, 0, 0));
		
		ipAddr.setPrefSize(250, 35);
		HBox.setMargin(ipAddr, new Insets(8, 0, 0, 10));
		box1.getChildren().addAll(label1, ipAddr);
		
		HBox box2 = new HBox();
		box2.setPrefSize(370, 50);
		VBox.setMargin(box2, new Insets(10, 40, 0, 40));
		
		label2.setAlignment(Pos.CENTER_RIGHT);
		label2.setPrefSize(70, 20);
		label2.setFont(new Font(14));
		HBox.setMargin(label2, new Insets(15, 0, 0, 0));
		
		port.setPrefSize(250, 35);
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
