package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String URL="jdbc:mysql://localhost:3306/imooc?useUnicode=true&amp;characterEncoding=utf-8";//jdbc:mysql://localhost:3306/imooc?useUnicode=true&characterEncoding=utf-8";
	private static final String USER="root";
	private static final String PASSWORD="chen3612";
	private static Connection conn=null;
	
	static{
		try {	
			Class.forName("com.mysql.jdbc.Driver");//���d�ӳ���
			conn=DriverManager.getConnection(URL,USER,PASSWORD);//������ݿ�����
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
public static Connection getConnection(){
	return conn;
	}
}
