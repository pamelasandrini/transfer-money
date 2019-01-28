package com.transfermoney;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.transfermoney.dao.ConnectionFactory;

public class ApplicationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("App started!");
		ConnectionFactory.populateTestData();

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
