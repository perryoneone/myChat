package application.client;

import application.dao.UserDAO;
import application.model.User;
import application.utils.Toast;
import javafx.stage.Stage;

public class HandleRegister {
	public HandleRegister(Stage stage, User user) throws Exception {
		Toast toast = new Toast(stage);
		UserDAO userDAO = new UserDAO();
		if(userDAO.findByName(user.getNickname()) != null) {
			Toast.Level level = Toast.Level.values()[2];
			toast.show(level, 1000, "用户已被注册!");
		}
		else if(userDAO.insert(user) > 0) {
			//System.out.println("注册成功");
			
			Toast.Level level = Toast.Level.values()[0];
			toast.show(level, 1000, "注册成功!");
			Thread.sleep(1000);
			stage.close();
		}else {
			//System.out.println("注册失败");
			Toast.Level level = Toast.Level.values()[2];
			toast.show(level, 1000, "登陆失败!");
		}
	}
}
