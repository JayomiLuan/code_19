package cn.itcast.day19.dao;

import java.sql.Connection;
import java.util.List;

import cn.itcast.day19.domain.Account;

public interface AccountDao2 {
	//取得指定账户信息
	Account get( String id) throws Exception;
	
	//修改账户余额
	void update( String id , double money ) throws Exception;
	
	//取得所有账户信息
	List<Account> getAll() throws Exception;
}
