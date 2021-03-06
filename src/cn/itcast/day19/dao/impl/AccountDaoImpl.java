package cn.itcast.day19.dao.impl;

import cn.itcast.day19.dao.AccountDao;
import cn.itcast.day19.domain.Account;
import cn.itcast.day19.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
	
	/**
	 * 取得指定账户的信息
	 */
	@Override
	public Account get(Connection conn , String id) throws Exception {
		//在DAO层只做数据库操作，不能关闭连接等操作
		QueryRunner qr = new QueryRunner();
		Account account = (Account) qr.query(conn, "select * from tb_account where id=?",id , new BeanHandler(Account.class) );
		
		return account;
	}

	/**
	 * 修改账户余额
	 */
	@Override
	public void update( Connection conn , String id, double money) throws Exception {
		QueryRunner qr = new QueryRunner();
		qr.update(conn , "update tb_account set money=money+? where id=?" , money , id );
	}

	@Override
	public List<Account> getAll() throws Exception {
		Connection conn = JdbcUtils.getConnection();
		QueryRunner qr = new QueryRunner();
		return (List<Account>) qr.query( conn ,"select * from tb_account", new BeanListHandler(Account.class));
	}

}
