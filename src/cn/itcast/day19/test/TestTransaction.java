package cn.itcast.day19.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.itcast.day19.utils.JdbcUtils;

public class TestTransaction {

	// 没有事务，第一条成功，第二条失败
	@Test
	public void test1() {
		DataSource ds = new ComboPooledDataSource();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();

			// user001减钱
			stmt.executeUpdate("update tb_account set money=money-1000 where id='user001'");

			// 模块抛出异常
			int i = 10 / 0;

			// user002加钱
			stmt.executeUpdate("update tb_account set money=money+1000 where id='user002'");

			// 提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 回滚事务
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtils.releaseResource(stmt, conn);
		}
	}

	//事务正常执行，提交
	@Test
	public void test2() {
		DataSource ds = new ComboPooledDataSource();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			// 开启事务
			conn.setAutoCommit(false);// 如果不开启事务，则第一条更新会成功，第二条更新会失败

			// user001减钱
			stmt.executeUpdate("update tb_account set money=money-1000 where id='user001'");

			// user002加钱
			stmt.executeUpdate("update tb_account set money=money+1000 where id='user002'");

			// 提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 回滚事务
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtils.releaseResource(stmt, conn);
		}
	}

	//事务执行失败，回滚
	@Test
	public void test3() {
		DataSource ds = new ComboPooledDataSource();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			// 开启事务
			conn.setAutoCommit(false);// 如果不开启事务，则第一条更新会成功，第二条更新会失败

			// user001减钱
			stmt.executeUpdate("update tb_account set money=money-1000 where id='user001'");

			// 模块抛出异常
			int i = 10 / 0;

			// user002加钱
			stmt.executeUpdate("update tb_account set money=money+1000 where id='user002'");

			// 提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 回滚事务
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtils.releaseResource(stmt, conn);
		}
	}
	
	//测试DBUtils的事务的实现
	@Test
	public void test4(){
		Connection conn = null;
		Statement stmt = null;
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		try{
			//取得连接对象
			conn = ds.getConnection();
			//用DBUtils实现事务
			conn.setAutoCommit(false);
			//创建 QueryRunner对象时，不能提供连接池对象，
			QueryRunner qr = new QueryRunner();
			
			//在使用QueryRunner对象执行SQL语句时，使用程序中手动取得并开启了事务的连接对象
			qr.update( conn , "update tb_account set money=money-1000 where id='user001'");
			qr.update( conn , "update tb_account set money=money+1000 where id='user002'");
			
			//提交事务
			DbUtils.commitAndClose(conn);
		}catch(Exception e){
			e.printStackTrace();
			//回滚事务
			DbUtils.rollbackAndCloseQuietly(conn);
		}finally{
			
		}
		
	}
	//测试DBUtils的事务的实现(回滚)
	@Test
	public void test5(){
		Connection conn = null;
		Statement stmt = null;
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		try{
			//取得连接对象
			conn = ds.getConnection();
			//用DBUtils实现事务
			conn.setAutoCommit(false);
			//创建 QueryRunner对象时，不能提供连接池对象，
			QueryRunner qr = new QueryRunner();
			
			//在使用QueryRunner对象执行SQL语句时，使用程序中手动取得并开启了事务的连接对象
			qr.update( conn , "update tb_account set money=money-1000 where id='user001'");
			int i = 10/0;
			qr.update( conn , "update tb_account set money=money+1000 where id='user002'");
			
			//提交事务
			DbUtils.commitAndClose(conn);
		}catch(Exception e){
			e.printStackTrace();
			//回滚事务
			DbUtils.rollbackAndCloseQuietly(conn);
		}finally{
			
		}
		
	}
	
	
}
