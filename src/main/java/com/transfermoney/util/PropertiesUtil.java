package com.transfermoney.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility class to handle properties.
 * 
 * @author pborsoni
 *
 */
public class PropertiesUtil {

	private static Properties properties = new Properties();
	private static final String PROPERTIES_FILE_NAME = "config.properties";
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

	private PropertiesUtil() {
		logger.debug("Utility class");
	}

	static {
		try {
			loadConfigProperties();
		} catch (IOException e) {
			logger.error("Error in loading config properties - " + e);
		}
	}

	/**
	 * this method is executed in the initialization block to load all properties
	 * from config.properties file.
	 * 
	 * @throws IOException
	 */
	private static void loadConfigProperties() throws IOException {

		try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(PROPERTIES_FILE_NAME)) {

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
			}

		} catch (Exception e) {
			logger.error("Exception: " + e);
		}
	}

	/**
	 * Gets the value for the provided <code>propertyName</code>.
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(String propertyName) {
		String value = null;

		if (properties != null && propertyName != null) {
			value = properties.getProperty(propertyName);
		}

		return value;
	}

}
