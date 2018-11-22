package application.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import application.login.HandleLogin.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ServerStart extends Application
{

	/**
	 * 判断服务器是否在运行
	 */
	private boolean isRunning;
	// 用户名不能相同，使用HashMap<String, ClientHandler>存放每个用户和客户端套接字的映射。
	private HashMap<String, ClientHandler> clientHandlerMap = new HashMap<String, ClientHandler>();
	private ServerSocket server;
	TextField ipAddr;
	TextField port;
	Button startBtn;
	Button stopBtn;
	TextArea log;
	ListView<String> list;
	ObservableList<String> data;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//Parent root = FXMLLoader.load(getClass().getResource("ServerFrame.fxml"));
		VBox root = new VBox();
		root.setStyle("-fx-background-color: #0ff;");
		
		HBox box1 = new HBox();
		box1.setPrefSize(900, 100);
		
		Label lb3 = new Label("服务器IP地址：");
		lb3.setFont(new Font(18));
		HBox.setMargin(lb3, new Insets(40, 0, 0, 20));
		
		ipAddr = new TextField();
		ipAddr.setText("127.0.0.1");
		ipAddr.setPrefSize(200, 40);
		ipAddr.setFont(new Font(16));
		HBox.setMargin(ipAddr, new Insets(30, 0, 0, 20));
		
		Label lb4 = new Label("端口号：");
		lb4.setFont(new Font(18));
		HBox.setMargin(lb4, new Insets(40, 0, 0, 40));
		
		port = new TextField();
		port.setText("9999");
		port.setPrefSize(100, 40);
		port.setFont(new Font(16));
		HBox.setMargin(port, new Insets(30, 0, 0, 10));
		
		startBtn = new Button("启动");
		startBtn.setStyle("-fx-background-color: skyblue;");
		startBtn.setPrefSize(100, 40);
		startBtn.setFont(new Font(18));
		startBtn.setCursor(Cursor.HAND);
		HBox.setMargin(startBtn, new Insets(30, 0, 0, 80));
		/**
		 * 启动服务器
		 */
		startBtn.setOnAction((e) -> {
			startServer();
		});
		
		
		stopBtn = new Button("停止");
		stopBtn.setStyle("-fx-background-color: red;");
		stopBtn.setDisable(true);
		stopBtn.setPrefSize(100, 40);
		stopBtn.setFont(new Font(18));
		stopBtn.setCursor(Cursor.HAND);
		HBox.setMargin(stopBtn, new Insets(30, 20, 0, 10));
		/**
		 * 关闭服务器
		 */
		stopBtn.setOnAction((e) -> {
			stopServer();
		});
		
		box1.getChildren().addAll(lb3,ipAddr,lb4,port,startBtn,stopBtn);
		
		Label lb1 = new Label("消息记录");
		lb1.setFont(new Font(18));
		VBox.setMargin(lb1, new Insets(0, 0, 0, 20));
		
		log = new TextArea();
		log.setPrefSize(860, 200);
		log.setEditable(false);
		log.setFont(new Font(14));
		VBox.setMargin(log, new Insets(10, 20, 0, 20));
		
		Label lb2 = new Label("在线用户列表");
		lb2.setFont(new Font(18));
		VBox.setMargin(lb2, new Insets(20, 0, 0, 20));
		
		data = FXCollections.observableArrayList();
		list = new ListView<String>(data);
		list.setItems(data);
		list.setPrefSize(200, 200);
		VBox.setMargin(list, new Insets(10, 20, 0, 20));
		
		root.getChildren().addAll(box1,lb1,log,lb2,list);
		primaryStage.setTitle("TCP聊天室服务器"); // 设置窗口标题
	    primaryStage.getIcons().add(new Image("/application/res/qq_icon.png")); // 设置窗口icon图标
	    primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show(); 
	}
	
	public void startServer() {
		System.out.println("服务器启动中。。。");
		// 创建、启动服务器通信线程
		//Thread serverThread = new Thread(new ServerThread());
		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();
		startBtn.setDisable(true);
		stopBtn.setDisable(false);
		ipAddr.setEditable(false);
		port.setEditable(false);
		System.out.println("服务器启动成功");
	}
	
	public void stopServer() {		
		try {
			System.out.println("服务器关闭中。。。");
			isRunning = false;
			// 关闭服务器套接字，清空客户端映射
			server.close();
			clientHandlerMap.clear();
			startBtn.setDisable(false);
			stopBtn.setDisable(true);
			ipAddr.setEditable(true);
			port.setEditable(true);
			log.appendText("服务器关闭成功！" + "\n");
			System.out.println("服务器关闭成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 内部类，服务器后台线程
	class ServerThread implements Runnable {
		/**
		 * 启动服务
		 */
		public void start(String serverIp, int serverPort) {
			try {
				// 创建套接字地址
				SocketAddress socketAddress = new InetSocketAddress(serverIp, serverPort);
				// 创建ServerSocket，绑定套接字地址
				server = new ServerSocket();
				server.bind(socketAddress);
				// 修改判断服务器是否运行的标识变量
				isRunning = true;
				addMsg("服务器启动成功！");
			} catch (IOException e) {
				// e.printStackTrace();
				addMsg("服务器启动失败，请检查端口是否被占用");
				isRunning = false;
			}
		}

		/**
		 * 线程主体
		 */
		@Override
		public void run() {
			start(ipAddr.getText(), Integer.parseInt(port.getText()));
			// 当服务器处于运行状态时，循环监听客户端的连接请求
			while (isRunning) {
				try {
					Socket socket = server.accept();
					// 创建与客户端交互的线程
					Thread thread = new Thread(new ClientHandler(socket));
					thread.start();
				} catch (IOException e) {

				}
			}
		}

	}

	// 内部类，用于和客户端交互
	class ClientHandler implements Runnable {
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isConnected;
		private String username;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				this.dis = new DataInputStream(socket.getInputStream());
				this.dos = new DataOutputStream(socket.getOutputStream());
				isConnected = true;
			} catch (IOException e) {
				isConnected = false;
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (isRunning && isConnected) {
				try {
					// 读取客户端发送的报文
					String msg = dis.readUTF();
					String[] parts = msg.split("#");
					switch (parts[0]) {
					// 处理登录报文
					case "LOGIN":
						String loginUsername = parts[1];
						// 如果该用户名已登录，则返回失败报文，否则返回成功报文
						if (clientHandlerMap.containsKey(loginUsername)) {
							dos.writeUTF("FAIL");
						} else {
							dos.writeUTF("SUCCESS");
							addMsg("用户"+loginUsername+"登陆成功，登陆时间：" + getTimeStr());
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									data.add(loginUsername);
									
								}
							});
							// 将此客户端处理线程的信息添加到clientHandlerMap中
							clientHandlerMap.put(loginUsername, this);
							// 将现有用户的信息发给新用户
							StringBuffer msgUserList = new StringBuffer();
							msgUserList.append("USERLIST#");
							
							for (String username : clientHandlerMap.keySet()) {
								msgUserList.append(username + "#");
							}
							dos.writeUTF(msgUserList.toString());
							// 将新登录的用户信息广播给其他用户
							String msgLogin = "LOGIN#" + loginUsername;
							broadcastMsg(loginUsername, msgLogin);
							// 存储登录的用户名
							this.username = loginUsername;
						}
						break;
					// 处理退出报文
					case "LOGOUT":
						clientHandlerMap.remove(username);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								data.remove(username);
								
							}
						});
						
						addMsg("用户"+username+"退出登陆，退出时间：" + getTimeStr());
						String msgLogout = "LOGOUT#" + username;
						broadcastMsg(username, msgLogout);
						isConnected = false;
						socket.close();
						break;
					case "TALKTO_ALL":
						if(parts[2].equals("false")) {
							System.out.println("用户消息");
							addMsg(Utils.getTimeStr() + " 【"+username + "】 跟所有人说：" + parts[1]);
							String msgTalkToAll = "TALKTO_ALL#" + username + "#" + parts[1] + "#" + parts[2];
							broadcastMsg(username, msgTalkToAll);
						}else if(parts[2].equals("true")){
							System.out.println("系统消息");
							String msgTalkToAll = "TALKTO_ALL#" + username + "#" + parts[1] + "#" + parts[2];
							broadcastMsg(username, msgTalkToAll);
						}
						break;
					case "TALKTO":
						ClientHandler clientHandler = clientHandlerMap.get(parts[1]);
						if (null != clientHandler) {
							String msgTalkTo = "TALKTO#" + username + "#" + parts[2];
							clientHandler.dos.writeUTF(msgTalkTo);
							clientHandler.dos.flush();
						}
						break;

					default:
						break;
					}
				} catch (IOException e) {
					isConnected = false;
					e.printStackTrace();
				}
			}
		}

		/**
		 * 将某个用户发来的消息广播给其它用户
		 * 
		 * @param fromUsername 发来消息的用户
		 * @param msg          需要广播的消息
		 */
		private void broadcastMsg(String fromUsername, String msg) throws IOException {
			for (String toUserName : clientHandlerMap.keySet()) {
				if (fromUsername.equals(toUserName) == false) {
					DataOutputStream dos = clientHandlerMap.get(toUserName).dos;
					dos.writeUTF(msg);
					dos.flush();
				}
			}
		}
	}

	/**
	 * 添加消息到文本框
	 * 
	 * @param msg，要添加的消息
	 */
	private void addMsg(String msg) {
		// 在文本区中添加一条消息，并加上换行
		log.appendText(msg + "\n");
		
		// 自动滚动到文本区的最后一行
		log.positionCaret(log.getText().length());
	}

	//获取格式化的当前时间字符串形式
	public String getTimeStr() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		return fm.format(new Date());

	}
}
