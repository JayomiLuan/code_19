package cn.itcast.day19.test;

import cn.itcast.day19.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestThreadLocal {

	@Test
	public void test1() throws SQLException{
		Connection conn = JdbcUtils.getConnection();
		
		Thread currentThread = Thread.currentThread();
		System.out.println( currentThread );
		
//		JdbcUtils.local.set( conn );
		System.out.println("conn: " + conn);
		test2();
		
		
	}
	
	public void test2(){
		Thread currentThread = Thread.currentThread();
		System.out.println( currentThread );

		//用当前线程对象当Key，去ThreadLocal的Map中取得对应的连接对象
//		Connection conn = JdbcUtils.local.get();
//		System.out.println("conn: " + conn);

	}
}
