package application.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import application.dao.UserDAO;
import application.model.User;
import application.utils.Toast;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HandleLogin {
	private String ipAddr;
	private int port;
	private static ClientThread clientThread;
	private static TextArea showMsg;
	private static String msg;
	// 数据源
	private static ObservableList<String> data;
	// 创建ListView，指定数据项类型
	private static ListView<String> list;
	// 统计在线用户数量
	private static Label userNum;
	public static final String username = null;
	public static String privateChatUser;
	// 默认是群聊
	private static boolean isPublic = true;
	
    public HandleLogin(Stage stage,Button btn, TextField userName,PasswordField pwd) throws Exception {
    	Toast toast = new Toast(stage);
    	
    	if(userName.getUserData() == null) {
    		Toast.Level level = Toast.Level.values()[2];
			toast.show(level, 1000, "请确认登陆设置！!");
    	}else {
    		this.ipAddr = (String)userName.getUserData();
        	this.port = Integer.parseInt((String)pwd.getUserData());
        	System.out.println(this.ipAddr + ":" + this.port);
    		UserDAO userDAO = new UserDAO();
        	User user = userDAO.findBy(userName.getText(), pwd.getText());
        	if(user != null) {
    			Thread.sleep(2000);
    			stage.close();
    			ChatRoom room = new ChatRoom(userName.getText());
                room.showChat();
    			clientThread = new ClientThread(userName.getText());
    			clientThread.start();
    			
				
        	}else {
        		Toast.Level level = Toast.Level.values()[2];
    			toast.show(level, 1000, "用户名或密码错误！!");
        	}
    	}
    	
    }
    static class ChatRoom extends Application {
    	double x1;
    	double y1;
    	double x_stage;
    	double y_stage;
    	Label title;
    	Stage stage = new Stage();
    	String userName;
    	public static void main(String[] args) {
    		launch(args);
    	}
    	public ChatRoom(String userName) {
    		this.userName = userName;
    	}

    	@Override
    	public void start(Stage primaryStage) throws Exception {
    		//Parent root = FXMLLoader.load(getClass().getResource("ChatRoom.fxml"));
    		primaryStage.initStyle(StageStyle.UNDECORATED); // 设置窗口风格为没有操作系统平台装饰的白色背景

    		VBox root = new VBox();
    		
    		/**
    		 * 自定义窗口头部
    		 */
    		AnchorPane box1 = new AnchorPane();
    		box1.setPrefSize(1300, 60);
    		box1.setStyle("-fx-background-color: rgb(51,122,183); -fx-border-radius: 0.8;");
    		
    		title = new Label();
    		title.setText("聊天室[用户："+userName+"]");
    		title.setStyle("-fx-text-fill:#fff");
    		title.setFont(Font.font("微软雅黑", FontWeight.BOLD, 18));
    		AnchorPane.setTopAnchor(title, 15.0);
    		AnchorPane.setLeftAnchor(title, 20.0);
    		//HBox.setMargin(title, new Insets(15, 0, 0, 20));
    		
    		Button closeBtn = new Button("退出");
    		closeBtn.setStyle("-fx-background-color: rgb(91,192,222);-fx-text-fill:white");
    		closeBtn.setFont(new Font(16));
    		closeBtn.setCursor(Cursor.HAND);
    		AnchorPane.setTopAnchor(closeBtn, 12.0);
    		AnchorPane.setRightAnchor(closeBtn, 20.0);
    		//HBox.setMargin(closeBtn, new Insets(12, 0, 0, 1060));
    		/**
    		 * 退出聊天室事件
    		 */
    		closeBtn.setOnAction((e) -> {
    			Stage stage = (Stage)closeBtn.getScene().getWindow();
    			stage.close();
    			clientThread.logout(userName);;
    		});
    		
    		box1.getChildren().addAll(title,closeBtn);
    		
    		/**
    		 * 聊天窗口主体部分
    		 */
    		HBox box2 = new HBox();
    		box2.setPrefSize(1300, 500);
    		
    		StackPane sp = new StackPane();
    		sp.setPrefSize(900, 500);
    		
    		showMsg = new TextArea();
    		showMsg.setPrefSize(900, 500);
    		showMsg.setStyle("-fx-border-color: lightblue;");
    		showMsg.setFont(new Font(14));
    		showMsg.setEditable(false);
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
    		
    		userNum = new Label();
    		userNum.setStyle("-fx-text-fill:red");
    		userNum.setFont(new Font(14));
    		HBox.setMargin(userNum, new Insets(15, 0, 0, 10));
    		
    		hb.getChildren().addAll(lb1,userNum);
    		
    		data = FXCollections.observableArrayList();
    		list = new ListView<String>(data);
    		list.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent click) {
					if(click.getClickCount() == 2) {
						privateChatUser = list.getSelectionModel().getSelectedItem();
						if(!privateChatUser.equals(userName)) {
							title.setText("聊天室[用户："+userName+"]--正在与"+privateChatUser+"私聊中...");
							isPublic = false;
						}else {
							title.setText("聊天室[用户："+userName+"]");
							isPublic = true;
						}
					}
					
				}
			});
    		list.setPrefSize(358, 429);
    		list.setStyle("-fx-border-color: rgba(0,0,0,0);");
    		
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
    			msg = htmlRemoveTag(message.getHtmlText());
    			if(msg.equals("")) {
    				Toast.Level level = Toast.Level.values()[1];
    				toast.show(level, 1000, "消息不能为空！!");
    				message.requestFocus();
    			}else {
    				sendBtn.setFocusTraversable(false);
    				if(null!=clientThread){
    					clientThread.sendChatMsg();//ClientThread中的sendChatMsg方法,发送消息
    				}
    					
    				message.setHtmlText("");
    				message.requestFocus();
    			}
    		});
    		
    		box3.getChildren().addAll(message,sendBtn);
    		root.getChildren().addAll(box1,box2,box3);
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

    	public void showChat() throws Exception {
    		start(stage);
    	}
    	
    }
	
	public class ClientThread extends Thread {
		
		// 通信套接字
		public Socket socket;
		//基本数据输入流
		private DataInputStream dis;
		//基本数据输出流
		private DataOutputStream dos;
		//是否登录
		private boolean isLogged;
		//登陆用户名
		private String userName;
		public ClientThread(String userName) {
			this.userName = userName;
		}
		/**
		 * 连接服务器并登录
		 */
		private void login() throws IOException {

			//连接服务器，获取套接字IO流
			socket = new Socket(ipAddr, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			//获取用户名，构建、发送登录报文
			String username = userName;
			String msgLogin = "LOGIN#" + username;
			dos.writeUTF(msgLogin);
			dos.flush();
			//读取服务器返回的信息，判断是否登录成功
			String response = dis.readUTF();
			//登录失败
			if (response.equals("FAIL")) {
				System.out.println("登陆服务器失败");
				//登录失败，断开连接，结束客户端线程
				socket.close();
				return;
			}
			//登录成功
			else {
				addMsg("欢迎您进入聊天室！");
				this.sendSysMsg("进入了聊天室！");
				isLogged = true;
				//更新Jlist列表信息
				String[] self = { username };
				updateUserList(list, self, "ADD");
				//获取在线人数
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						userNum.setText(String.valueOf(data.size()));		
					}
				});
			}
		}
		
		//退出聊天室功能实现
		public void logout(String username) {
			try {
				this.sendSysMsg("退出了聊天室！");
				Utils.sendMsg(socket, "LOGOUT#" + username);
				isLogged=false;
				String[] self = { username };
				updateUserList(list, self, "ADD");
				//获取在线人数
				userNum.setText(String.valueOf(data.size()));
				//lblRoomInfo.setText("目前在线人数" + listUsers.getModel().getSize() + "人");
				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		
		//发送用户消息
		public void sendChatMsg() {
			String msgChat = null;
			String dstUser = "所有人";
			if (isPublic) {
				msgChat = "TALKTO_ALL#" + msg + "#false";
			}
			else {
				dstUser = privateChatUser;
				msgChat = "TALKTO#" + dstUser + "#" + msg;
			}
			//发送聊天报文到服务器
			Utils.sendMsg(socket, msgChat);
			//添加到消息记录框
			addMsg(Utils.getTimeStr() + " 【我】对" + dstUser + "说：\n" + msg);
		}
		
		//发送系统消息
		public void sendSysMsg(String msg) {
			String msgChat = null;
			msgChat = "TALKTO_ALL#" + msg + "#true";
			//发送聊天报文到服务器
			Utils.sendMsg(socket, msgChat);

		}

		//更新用户列表
		@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
		public void updateUserList(ListView list, String[] items, String op) {
			switch (op) {
			case "ADD": // 添加新数据
				for (int i = 0; i < items.length; i++) {
					data.add(items[i]);
					break;
				}
			case "DEL":// 删除数据
				for (int i = 0; i < items.length; i++) {
					data.remove(items[i]);
					break;

				}
			default:
				break;
			}
			list.setItems(data);// 更新数据

		}
		

		//线程主体
		@Override
		public void run() {
			//连接服务器并登录
			try {
				login();
			} catch (Exception e) {
				System.out.println("连接登录服务器时出现异常");
				e.printStackTrace();
				return;
			}
			while (isLogged) {
				try {
					String msg = dis.readUTF();
					// String msg=Utils.recvMsg(socket);
					String[] parts = msg.split("#");
					switch (parts[0]) {
					//处理服务器发来的用户列表报文
					case "USERLIST":
						String[] self = { username };
						updateUserList(list, self, "ADD");
						//获取在线人数
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								userNum.setText(String.valueOf(data.size()));
							}
						});
						
						for (int i = 1; i < parts.length; i++) {
							data.add(parts[i]);
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								userNum.setText(String.valueOf(data.size()));	
							}
						});
						System.out.println("USERLIST");
						System.out.println(data.size());
						break;
					//处理服务器发来的新用户登录表报文
					case "LOGIN":
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								data.add(parts[1]);	
								userNum.setText(String.valueOf(data.size()));	
							}
						});		
						break;
					case "LOGOUT":
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								data.remove(parts[1]);
							}
						});				
						String[] logoutUser={parts[1]};
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								updateUserList(list,logoutUser,"DEL");
								userNum.setText(String.valueOf(data.size()));
							}
						});
						break;
					case "TALKTO_ALL":
						if(parts[3].equals("false")) {
							addMsg(Utils.getTimeStr() + " 【"+parts[1] + "】 跟所有人说：\n" + parts[2]);
						}else if(parts[3].equals("true")){
							addMsg("【系统消息】 " + parts[1] + parts[2]);
						}
						break;
					case "TALKTO":

						addMsg(Utils.getTimeStr()+ " 【" + parts[1] + "】 跟我说：" + parts[2]);
						break;
					default:
						break;
					}
				} catch (IOException e) {
					// TODO 处理异常
					isLogged = false;
					e.printStackTrace();
				}
			}
		}

		/**
		 * 添加消息到文本框
		 * 
		 * 
		 * @param msg，要添加的消息
		 */
		public void addMsg(String msg) {
			//在文本区中添加一条消息，并加上换行
			showMsg.appendText(msg + "\n");
			//自动滚动到文本区的最后一行
			showMsg.positionCaret(showMsg.getText().length());
		}
	}
	
	//将消息报文的发送、接受封装在Utils工具类中
	public static class Utils {
		//通过套接字S发送字符串
		public static void sendMsg(Socket s, String msg) {
			try {
				//字符流
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//在套接字S上读取字符串，如果TCP连接关闭，返回null
		@SuppressWarnings("resource")
		public static String recvMsg(Socket s) throws IOException {
			String msg = null;
			DataInputStream dis = (DataInputStream) new java.io.DataInputStream(s.getInputStream());
			msg = ((java.io.DataInputStream) dis).readUTF();
			return msg;
	
		}

		//获取格式化的当前时间字符串形式
		public static String getTimeStr() {
			SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	
			return fm.format(new Date());
	
		}
	}
}
