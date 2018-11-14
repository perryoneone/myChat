package application.chat;

import java.util.regex.Pattern;

import application.model.User;
import application.utils.Toast;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ChatController {

	@FXML
	Button closeBtn;
	
	@FXML
	TextArea showMsg;
	@FXML
	Label userNumber;
	@FXML
	ListView<User> users;
	@FXML
	HTMLEditor message;
	@FXML
	Button sendBtn;
	@FXML
	public void closeRoom() {
		Stage stage = (Stage)closeBtn.getScene().getWindow();
		stage.close();
	}
	@FXML
	public void sendMsg() {
		Toast toast = new Toast(closeBtn.getScene().getWindow());
		String msg = htmlRemoveTag(message.getHtmlText());
		if(msg.equals("")) {
			Toast.Level level = Toast.Level.values()[1];
			toast.show(level, 1000, "消息不能为空！!");
			message.requestFocus();
		}else {
			sendBtn.setFocusTraversable(false);
			showMsg.appendText(msg + "\n");
			message.setHtmlText("");
			message.requestFocus();
		}
		
	}
	
	
	/**
	 * 过滤HTML标签
	 */
	public String htmlRemoveTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
			//定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}

}
