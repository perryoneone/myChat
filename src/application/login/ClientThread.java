package application.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


//内部类.客户端线程，负责与服务器交互

public class ClientThread extends Thread {
	/**
	 * 通信套接字
	 */
	private Socket socket;

	/**
	 * 基本数据输入流
	 */
	private DataInputStream dis;

	/**
	 * 基本数据输出流
	 */
	private DataOutputStream dos;

	/**
	 * 是否登录
	 */
	private boolean isLogged;

	/**
	 * 连接服务器并登录
	 */
	private void login() throws IOException {

//连接服务器，获取套接字IO流
		socket = new Socket("127", 9999);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
//获取用户名，构建、发送登录报文
		String username = "";
		String msgLogin = "LOGIN#" + username;
		dos.writeUTF(msgLogin);
		dos.flush();
//读取服务器返回的信息，判断是否登录成功
		String response = dis.readUTF();

//登录失败
		if (response.equals("FAIL")) {
			addMsg("登录服务器失败");
//登录失败，断开连接，结束客户端线程
			socket.close();
			return;
		}
//登录成功
		else {
			addMsg("聊天室服务器登录成功");
			isLogged = true;
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
		} catch (IOException e) {
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
