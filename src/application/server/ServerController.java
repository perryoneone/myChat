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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController {
	/**
	 * 判断服务器是否在运行
	 */
	private boolean isRunning;
	// 用户名不能相同，使用HashMap<String, ClientHandler>存放每个用户和客户端套接字的映射。
	private HashMap<String, ClientHandler> clientHandlerMap = new HashMap<String, ClientHandler>();
	private ServerSocket server;
	@FXML
	TextField ipAddr;
	@FXML
	TextField port;
	@FXML
	Button start;
	@FXML
	Button stop;

	@FXML
	TextArea log;
	@FXML
	ListView<String> list;
	@FXML
	public void unchange() {
		log.setEditable(false);
	}
	
	@FXML
	public void startServer() {
		System.out.println("服务器启动中。。。");
		// 创建、启动服务器通信线程
		//Thread serverThread = new Thread(new ServerThread());
		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();
		start.setDisable(true);
		stop.setDisable(false);
		ipAddr.setEditable(false);
		port.setEditable(false);
		System.out.println("服务器启动成功");
	}

	@FXML
	public void stopServer() {
		
		try {
			System.out.println("服务器关闭中。。。");
			isRunning = false;
			// 关闭服务器套接字，清空客户端映射
			server.close();
			clientHandlerMap.clear();
			start.setDisable(false);
			stop.setDisable(true);
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
				addMsg("服务器启动成功");
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

		public Object getInetAddress() {
			// TODO Auto-generated method stub
			return null;
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
							// 将此客户端处理线程的信息添加到clientHandlerMap中
							clientHandlerMap.put(loginUsername, this);
							// 将现有用户的信息发给新用户
							StringBuffer msgUserList = new StringBuffer();
							msgUserList.append("USERLIST#");
							for (String username : clientHandlerMap.keySet()) {
								msgUserList.append(username + "#");
								System.out.println(username);
							}
							dos.writeUTF(msgUserList.toString());
							// 将新登录的用户信息广播给其他用户
							String msgLogin = "LOGIN#" + loginUsername;

							broadcastMsg(loginUsername, msgLogin);
							// updateUserTbl();
							// 存储登录的用户名
							this.username = loginUsername;
							updateUserTbl();
						}
						break;
					// 处理退出报文
					case "LOGOUT":
						clientHandlerMap.remove(username);
						addMsg("用户"+username+"退出登陆，退出时间：" + getTimeStr());
						String msgLogout = "LOGOUT#" + username;
						broadcastMsg(username, msgLogout);
						isConnected = false;
						socket.close();
						updateUserTbl();
						break;
					case "TALKTO_ALL":
						String msgTalkToAll = "TALKTO_ALL#" + username + "#" + parts[1];
						broadcastMsg(username, msgTalkToAll);

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
	 * 添加消息到文本框textAreaRecord
	 * 
	 * @param msg，要添加的消息
	 */
	private void addMsg(String msg) {
		// 在文本区中添加一条消息，并加上换行
		log.appendText(msg + "\n");
		
		// 自动滚动到文本区的最后一行
		log.positionCaret(log.getText().length());
	}

	public void updateUserTbl() {
		// TODO Auto-generated method stub

	}

	public Object getInetAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//获取格式化的当前时间字符串形式
	public String getTimeStr() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		return fm.format(new Date());

	}

}