package application.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import application.chat.ChatRoom;
import application.dao.UserDAO;
import application.model.User;
import application.utils.Toast;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HandleLogin {
	private String ipAddr;
	private int port;
	private ClientThread clientThread;
	private TextField userName;
	
    public HandleLogin(Stage stage,Button btn, TextField userName,PasswordField pwd) throws Exception {
    	Toast toast = new Toast(stage);
    	
    	if(userName.getUserData() == null) {
    		Toast.Level level = Toast.Level.values()[2];
			toast.show(level, 1000, "请确认登陆设置！!");
    	}else {
    		this.ipAddr = (String)userName.getUserData();
        	this.port = Integer.parseInt((String)pwd.getUserData());
        	this.userName = userName;
        	System.out.println(this.ipAddr + ":" + this.port);
    		UserDAO userDAO = new UserDAO();
        	User user = userDAO.findBy(userName.getText(), pwd.getText());
        	if(user != null) {
    			Thread.sleep(2000);
    			clientThread = new ClientThread();
    			clientThread.start();
    			stage.close();
    			ChatRoom room = new ChatRoom(clientThread,userName.getText());
                room.showChat();
				
        	}else {
        		Toast.Level level = Toast.Level.values()[2];
    			toast.show(level, 1000, "用户名或密码错误！!");
        	}
    	}
    	
    }
    
	
	public class ClientThread extends Thread {
		
		// 通信套接字
		private Socket socket;
		//基本数据输入流
		private DataInputStream dis;
		//基本数据输出流
		private DataOutputStream dos;
		//是否登录
		//private boolean isLogged;

		/**
		 * 连接服务器并登录
		 */
		private void login() throws IOException {

	//连接服务器，获取套接字IO流
			socket = new Socket(ipAddr, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
	//获取用户名，构建、发送登录报文
			String username = userName.getText();
			String msgLogin = "LOGIN#" + username;
			dos.writeUTF(msgLogin);
			dos.flush();
	//读取服务器返回的信息，判断是否登录成功
			String response = dis.readUTF();
	//登录失败
			if (response.equals("FAIL")) {
				addMsg("登录服务器失败");
				System.out.println("登陆服务器失败");
	//登录失败，断开连接，结束客户端线程
				socket.close();
				return;
			}
	//登录成功
			else {
				addMsg("聊天室服务器登录成功");
				//isLogged = true;
				//btnConnect.setText("退出");
				//btnSend.setEnabled(true);
	//更新Jlist列表信息
				String[] self = { username };
				//updateJList(listUsers, self, "ADD");
	//获取在线人数
				//lblRoomInfo.setText("目前在线人数" + listUsers.getModel().getSize() + "人");

			}
		}

	//线程主体
		@Override
		public void run() {
	//连接服务器并登录
			try {
				login();
			} catch (Exception e) {
				addMsg("连接登录服务器时出现异常");
				e.printStackTrace();
				return;
			}
		}

		/**
		 * 添加消息到文本框textAreaRecord
		 * 
		 * @param msg，要添加的消息
		 */
		private void addMsg(String msg) {
	//在文本区中添加一条消息，并加上换行

	//自动滚动到文本区的最后一行

		}
	}
	
	static //将消息报文的发送、接受封装在Utils工具类中
	public class Utils {
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

	//添加消息到文本记录框JTextArea ，并且滚动显示到最后一行
		public static void addMsgRec(JTextArea textArea, String msg) {
			textArea.append(msg + "\n");
			textArea.setCaretPosition(textArea.getText().length());
		}
	}
}
