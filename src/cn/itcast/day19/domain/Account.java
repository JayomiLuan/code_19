package cn.itcast.day19.domain;

/**
 * 账户信息的JavaBean
 * @author Administrator
 *
 */
public class Account {
	private String id;
	private String name;
	private double money;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
}
