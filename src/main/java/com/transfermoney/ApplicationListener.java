package com.transfermoney;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.transfermoney.dao.ConnectionFactory;

public class ApplicationListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ApplicationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		logger.info("App started!");
		ConnectionFactory.populateTestData();

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		logger.info("App stopped!");

	}

}
