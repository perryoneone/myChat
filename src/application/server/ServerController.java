package application.server;

import java.io.IOException;

import application.socket.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ServerController {
	@FXML
	TextField ipAddr;
	@FXML
	TextField port;
	@FXML
	Button start;
	@FXML
	Button stop;
	
	@FXML
	public void startServer() {
		System.out.println("服务器启动中。。。");
		ServerThread serverThread = new ServerThread();
		serverThread.start();
		start.setDisable(true);
		stop.setDisable(false);
	}
	@FXML
	public void stopServer() {
		System.out.println("服务器关闭中。。。");
		new Server().stop();
		start.setDisable(false);
		stop.setDisable(true);	
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
