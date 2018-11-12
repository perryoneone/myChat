package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.jdbc.DBConnection;
import application.model.User;

public class UserDAO {
	// 添加用户
	public int insert(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			//获得连接
			con = DBConnection.getConnection();
			//创建Statement
			String sql = "Insert into t_user(nickname,pwd,sex,birthday,address) values(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getNickname());
			pstmt.setString(2, user.getPwd());
			pstmt.setString(3, user.getSex());
			pstmt.setString(4, user.getBirthday());
			pstmt.setString(5, user.getAddress());
			//执行sql语句，
			int rows = pstmt.executeUpdate();
			return rows;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally{
			//关闭对象
			DBConnection.close(con, pstmt, null);
		}
	}
	
	// 根据用户名与密码查找用户
	public User findBy(String nickname,String pwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			//获得连接
			con = DBConnection.getConnection();
			//创建Statement
			String sql = "select nickname from t_user where nickname = ? and pwd = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, pwd);
			res = pstmt.executeQuery();
			
			User user = null;
			while(res.next()) {
				user = new User();
				user.setNickname(res.getString("nickname"));
			}
			
			return user;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			//关闭对象
			DBConnection.close(con, pstmt, res);
		}
	}
	
	// 根据用户名查找用户
	public User findByName(String nickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			//获得连接
			con = DBConnection.getConnection();
			//创建Statement
			String sql = "select * from t_user where nickname = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			res = pstmt.executeQuery();
			
			User user = null;
			while(res.next()) {
				user = new User();
				user.setNickname(res.getString("nickname"));
			}
			
			return user;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			//关闭对象
			DBConnection.close(con, pstmt, res);
		}
	}
		
}
