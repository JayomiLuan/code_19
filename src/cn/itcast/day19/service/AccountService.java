package cn.itcast.day19.service;

import java.util.List;

import cn.itcast.day19.domain.Account;

public interface AccountService {
	boolean transfer( String outId , String inId , double Money ) throws Exception;
	List<Account> getAll() throws Exception;
}
