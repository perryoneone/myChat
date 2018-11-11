package application.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 创建客户端：发送数据+接受数据
 * 写出数据：输出流
 * 读取数据：输入流
 * @author 赵政
 *
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("请输入用户名：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
		if(name.equals("")) {
			return ;
		}
		
		Socket client = new Socket("localhost", 9999); // 服务器地址及端口
		
		new Thread(new Send(client,name)).start(); // 一条路径
		
		new Thread(new Receive(client)).start(); // 一条路径
		
	}
}
