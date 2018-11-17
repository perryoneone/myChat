package application.chat;

/**
 * 聊天室主界面
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.regex.Pattern;
import application.login.HandleLogin.ClientThread;
import application.model.User;
import application.utils.Toast;

public class ChatRoom extends Application {
	double x1;
	double y1;
	double x_stage;
	double y_stage;
	Stage stage = new Stage();
	private String userName;
	private ClientThread clientThread;
	
	public ChatRoom(ClientThread clientThread,String userName) {
		this.clientThread = clientThread;
		this.userName = userName;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Parent root = FXMLLoader.load(getClass().getResource("ChatRoom.fxml"));
		primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景

		VBox root = new ChatPane(clientThread);
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
	

class ChatPane extends VBox{

	public ChatPane(ClientThread clientThread) {
		/**
		 * 自定义窗口头部
		 */
		System.out.println(userName+"hello");
		HBox box1 = new HBox();
		box1.setPrefSize(1300, 60);
		box1.setStyle("-fx-background-color: rgb(51,122,183); -fx-border-radius: 0.8;");
		
		Label title = new Label("聊天室");
		title.setStyle("-fx-text-fill:#fff");
		title.setFont(Font.font("微软雅黑", FontWeight.BOLD, 18));
		HBox.setMargin(title, new Insets(15, 0, 0, 20));
		
		Button closeBtn = new Button("退出");
		closeBtn.setStyle("-fx-background-color: rgb(91,192,222);-fx-text-fill:white");
		closeBtn.setFont(new Font(16));
		closeBtn.setCursor(Cursor.HAND);
		HBox.setMargin(closeBtn, new Insets(12, 0, 0, 1160));
		/**
		 * 退出聊天室事件
		 */
		closeBtn.setOnAction((e) -> {
			Stage stage = (Stage)closeBtn.getScene().getWindow();
			stage.close();
			clientThread.destroy();
		});
		
		box1.getChildren().addAll(title,closeBtn);
		
		/**
		 * 聊天窗口主体部分
		 */
		HBox box2 = new HBox();
		box2.setPrefSize(1300, 500);
		
		StackPane sp = new StackPane();
		sp.setPrefSize(900, 500);
		
		TextArea showMsg = new TextArea();
		showMsg.setPrefSize(900, 500);
		showMsg.setStyle("-fx-border-color: lightblue;");
		showMsg.setFont(new Font(14));
		StackPane.setMargin(showMsg, new Insets(10, 0, 10, 10));
		
		Button btn = new Button("查看历史消息");
		btn.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill:#1a29d0");
		btn.setCursor(Cursor.HAND);
		btn.setFont(new Font(14));
		StackPane.setAlignment(btn, Pos.TOP_RIGHT);
		StackPane.setMargin(btn, new Insets(20, 10, 0, 0));
		
		sp.getChildren().addAll(showMsg,btn);
		
		VBox vb = new VBox();
		vb.setPrefSize(360, 200);
		vb.setStyle("-fx-border-color: lightblue;");
		HBox.setMargin(vb, new Insets(10, 0, 10, 20));
		
		HBox hb = new HBox();
		hb.setPrefSize(360, 50);
		
		Label lb1 = new Label("在线人数：");
		lb1.setFont(new Font(14));
		HBox.setMargin(lb1, new Insets(15, 0, 0, 10));
		
		Label lb2 = new Label("1");
		lb2.setStyle("-fx-text-fill:red");
		lb2.setFont(new Font(14));
		HBox.setMargin(lb2, new Insets(15, 0, 0, 10));
		
		hb.getChildren().addAll(lb1,lb2);
		
		ObservableList<String> data = FXCollections.observableArrayList();
		ListView<String> list = new ListView<String>(data);
		list.setPrefSize(358, 429);
		list.setStyle("-fx-border-color: rgba(0,0,0,0);");
		
		data.add(userName);
		
		list.setItems(data);
		
		vb.getChildren().addAll(hb,list);
		
		box2.getChildren().addAll(sp,vb);
		
		/**
		 * 发送信息
		 */
		StackPane box3 = new StackPane();
		box3.setPrefSize(1300, 240);
		
		HTMLEditor message = new HTMLEditor();
		message.setPrefSize(1300, 240);
		
		Button sendBtn = new Button("发送");
		sendBtn.setPrefWidth(80);
		sendBtn.setStyle("-fx-background-color: rgb(91,192,222);-fx-text-fill:white");
		sendBtn.setFont(Font.font(16));
		sendBtn.setCursor(Cursor.HAND);
		StackPane.setAlignment(sendBtn, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(sendBtn, new Insets(0, 20, 10, 0));
		/**
		 * 发送消息事件
		 */
		sendBtn.setOnAction((e) -> {
			Toast toast = new Toast(closeBtn.getScene().getWindow());
			String msg = htmlRemoveTag(message.getHtmlText());
			if(msg.equals("")) {
				Toast.Level level = Toast.Level.values()[1];
				toast.show(level, 1000, "消息不能为空！!");
				message.requestFocus();
			}else {
				sendBtn.setFocusTraversable(false);
				showMsg.appendText(msg + "\n");
				message.setHtmlText("");
				message.requestFocus();
			}
		});
		
		box3.getChildren().addAll(message,sendBtn);
		
		this.getChildren().addAll(box1,box2,box3);
	}
	
	/**
	 * 过滤HTML标签
	 */
	public String htmlRemoveTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
			//定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
}
}
