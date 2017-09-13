package cn.itcast.day19.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	
	//ThreadLocal就相当于一个Map，只不过Map中key被规定必须是线程对象
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	
	private static DataSource ds = new ComboPooledDataSource();
	//有一个集合容器，用来存放每个访问用户自己的Connection对象
	//最好有一个Map<Thread,Connection>，以线程对象为Key，保存此线程对应的Connection对象
	
	//用ThreadLocal的方式处理Connection对象，使得Service和Dao之间不再传递Connection对象
	public static Connection getConnection2(){
		Connection conn = null;
		
		//以当前线程为Key从local中取得Connection对象
		conn = local.get();
		//如果取得的对象为空，则说明还没有保存当前线程对应的COnnection对象
		if( conn == null ){
			//则取得一个新的Connection对象，并保存到local
			try {
				conn = getConnection();
				local.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	//从连接池中取和连接对象，传统方式取得连接对象
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	
	public static void releaseResource( Statement stmt , Connection conn ){
		releaseResource( stmt , conn );
	}
	
	public static void releaseResource( ResultSet rs , Statement stmt , Connection conn ){
		if( rs != null ){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs = null;
		
		//释放资源
		if( stmt != null ){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stmt = null;
		
		if( conn != null ){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;

	}
}
