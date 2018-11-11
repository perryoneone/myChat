package application.server;

import java.io.IOException;

import application.socket.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerController {
	@FXML
	TextField ipAddr;
	@FXML
	TextField port;
	
	@FXML
	public void startServer() {
		System.out.println("服务器启动中。。。");
		ServerThread serverThread = new ServerThread();
		serverThread.start();
	}
	@FXML
	public void stopServer() {
		try {
			System.out.println("服务器关闭中。。。");
			new Server().stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private class ServerThread extends Thread{

		
		@Override
		public void run() {
			try {
				new Server().start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
