package application.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import application.utils.CloseUtil;

/**
 * 发送数据线程
 * @author 赵政
 *
 */
public class Send implements Runnable{
	//控制台输入流
	private BufferedReader console;
	//管道输出流
	private DataOutputStream dos;
	//控制线程
	private boolean isRunning = true;
	//用户名
	private String name;
	//构造器初始化
	public Send() {
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	public Send(Socket client,String name) {
		this();
		try {
			dos = new DataOutputStream(client.getOutputStream());
			this.name = name;
			send(this.name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			isRunning = false;
			CloseUtil.closeAll(dos,console);
		}
	}
	//从控制台接受数据
	private String getMsgFromConsole() {
		try {
			return console.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "";
		}
	}
	/**
	 * 从控制台接受数据
	 * 发送数据
	 */
	public void send(String msg) {
		if(null!=msg && !msg.equals("")) {
			try {
				dos.writeUTF(msg);
				dos.flush(); // 强制刷新
			} catch (IOException e) {
				isRunning = false;
				CloseUtil.closeAll(dos,console);
			}
		}
	}
	@Override
	public void run() {
		//线程体
		while(isRunning) {
			send(getMsgFromConsole());
		}
	}

}
