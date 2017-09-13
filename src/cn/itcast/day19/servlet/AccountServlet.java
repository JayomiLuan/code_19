package cn.itcast.day19.servlet;

import cn.itcast.day19.domain.Account;
import cn.itcast.day19.service.AccountService;
import cn.itcast.day19.service.impl.AccountServiceImpl2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountServlet extends HttpServlet {

	//Servlet持有Service的实现类对象，用于调用业务逻辑
	//private AccountService accountService = new AccountServiceImpl();
	private AccountService accountService = new AccountServiceImpl2();
	
	@Override
	//如果以Get方式访问Servlet，则进行页面转跳，到转账页面
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//为什么非要用Servlet转一下：可以进行显示页面的数据准备工作
		//取得所有账户信息，存到req,转跳页面
		List<Account> list = null;
		try {
			list = accountService.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//把所有账户的信息存入req
		req.setAttribute("accountList", list);
		
		req.getRequestDispatcher("/transfer.jsp").forward(req, resp);
	}

	@Override
	//对POST方式访问Servlet，说明是表单提交，则进行转账业务的操作
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接收请求参数
		String outId = req.getParameter("outId");
		String inId = req.getParameter("inId");
		double money = Double.parseDouble( req.getParameter("money") );
		
		//调用业务逻辑
		boolean flag = false;
		try {
			flag = accountService.transfer(outId, inId, money);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//转跳页面
		String msg;
		if(flag){
			//业务成功
			msg = "转账成功！";
		}else{
			//业务失败
			msg = "转账失败！";
		}
		//保存消息字符串到request
		req.setAttribute("msg", msg);
		//内部转发到msg.jsp显示信息
		req.getRequestDispatcher("/msg.jsp").forward(req, resp);		
	}

}
