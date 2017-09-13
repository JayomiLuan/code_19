package cn.itcast.day19.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import cn.itcast.day19.dao.AccountDao;
import cn.itcast.day19.dao.impl.AccountDaoImpl;
import cn.itcast.day19.domain.Account;
import cn.itcast.day19.service.AccountService;
import cn.itcast.day19.utils.JdbcUtils;

public class AccountServiceImpl implements AccountService {
	
	//持有DAO的实现类对象，用于执行数据库操作
	private AccountDao accountDao = new AccountDaoImpl();
	
	@Override
	public boolean transfer(String outId, String inId, double money) throws Exception {
		//在业务方法中开启事务
		//得到连接对象并开启事务
		Connection conn = null;
		boolean flag = false;
		
		try{
			conn = JdbcUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			
			//调用DAO实现业务操作(取得汇款人账户信息，比较，如果余额充足，则进行转账，outId减去money,inId加上money)
			//取得汇款人账户信息
			Account account = accountDao.get(conn, outId);
			//判断余额是否充足
			if( account.getMoney() > money ){
				accountDao.update(conn, outId, -money);
				//int i = 10/0;//用于测试事务回滚
				accountDao.update(conn, inId, money);
			}
			
			//提交事务
			conn.commit();
			//事务成功执行完成，则设置返回值为true
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			DbUtils.close(conn);
		}
		
		return flag;
	}

	//用于取得所有账户信息
	@Override
	public List<Account> getAll() throws Exception {			
		return accountDao.getAll();
	}

}
