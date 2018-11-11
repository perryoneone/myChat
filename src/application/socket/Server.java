package application.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import application.utils.CloseUtil;

/**
 * 创建服务器
 * 写出数据：输出流
 * 读取数据：输入流
 * @author 赵政
 *
 */
public class Server {
	private List<MyChannel> all = new ArrayList<MyChannel>();
	private ServerSocket server;
	public static void main(String[] args) throws IOException {
			new Server().start();
	}
	
	public void start() throws IOException {
		server = new ServerSocket(9999);
		while(true) {
			Socket client = server.accept();
			
			MyChannel channel = new MyChannel(client);
			all.add(channel); // 统一管理
			new Thread(channel).start(); // 一条道路
		}	
	}
	
	public void stop() throws IOException {
		server.close();
		System.out.println("服务器已关闭。。。");
	}
	
	/**
	 * 一个客户端一条道路
	 * 1.输入流
	 * 2.输出流
	 * 3.接受数据
	 * 4.发送数据
	 * @author 赵政
	 *
	 */
	class MyChannel implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;
		private String name;
		//构造器初始化信息
		public MyChannel(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				
				this.name = dis.readUTF();
				System.out.println(this.name + "登陆成功!");
				this.send("欢迎您进入聊天室！");
				this.sendOthers(this.name + "进入了聊天室", true);
			} catch (IOException e) {
				//e.printStackTrace();
				isRunning = false;
				CloseUtil.closeAll(dis,dos);
			}
		}
		/**
		 * 读取数据
		 * @return
		 */
		private String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				//e.printStackTrace();
				CloseUtil.closeAll(dis);
				isRunning = false;
				all.remove(this); // 移除自身
				this.sendOthers(this.name + "退出了聊天室", true);
			}
			return msg;
		}
		/**
		 * 发送数据
		 */
		private void send(String msg) {
			if(msg == null || msg.equals("")) {
				return ;
			}
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				//e.printStackTrace();
				CloseUtil.closeAll(dos);
				isRunning = false;
				all.remove(this); // 移除自身
			}
		}
		/**
		 * 发送给其他客户端
		 */
		private void sendOthers(String msg, boolean sys) {
			/**
			 * 是否为私聊
			 */
			if(!msg.equals(null) && msg != "") {
				if(msg.startsWith("@") && msg.indexOf(":") > -1) {//私聊
					//获取私聊用户名
					String name = msg.substring(1, msg.indexOf(":"));
					String content = msg.substring(msg.indexOf(":")+1);
					for(MyChannel other: all) {
						if(other.name.equals(name)) {
							other.send(this.name + "对您悄悄地说：" + content);
						}
					}
				}else {//所有人
					//遍历容器
					for(MyChannel other: all) {
						//排除自身
						if(other == this) {
							continue;
						}
						if(sys) { // 系统信息
							other.send("系统信息：" + msg);
							System.out.println(12);
						}else {
							//发送给其他客户端
							System.out.println(34);
							other.send(this.name + "说：" + msg);
						}
					}
				}
			}
			
		}
		@Override
		public void run() {
			while(isRunning) {
				sendOthers(receive(), false);
			}
		}
		
	}
}