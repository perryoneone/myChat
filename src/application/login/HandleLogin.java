package application.login;

import application.chat.ChatRoom;
import application.dao.UserDAO;
import application.model.User;
import application.utils.Toast;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HandleLogin {
	String ipAddr = "";
	int port = 0;
	public HandleLogin() {
	}
    public HandleLogin(Stage stage,Button btn, String userName,String pwd) throws Exception {
    	Toast toast = new Toast(stage);
    	if(ipAddr.equals("")) {
    		this.settingDialog(btn);
    	}else {
    		UserDAO userDAO = new UserDAO();
        	User user = userDAO.findBy(userName, pwd);
        	if(user != null) {
    			Thread.sleep(2000);
        		stage.close();
                ChatRoom room = new ChatRoom();
                room.showChat();
        	}else {
        		Toast.Level level = Toast.Level.values()[2];
    			toast.show(level, 1000, "用户名或密码错误！!");
        	}
    	}
    	
    }
    
    /**
	 * 登陆设置对话框
	 */
	public void settingDialog(Button btn)
	{
		SettingDialog dlg = new SettingDialog(btn);
		
		// dlg.exec() 返回true或false
		if(dlg.exec())
		{
			ipAddr = dlg.ipAddr.getText();
			port = Integer.parseInt(dlg.port.getText());
			System.out.println(ipAddr+":"+port);
		}
	}
}
