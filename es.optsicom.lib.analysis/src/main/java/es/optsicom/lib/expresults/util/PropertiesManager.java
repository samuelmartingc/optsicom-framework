package es.optsicom.lib.expresults.util;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesManager {

	private static final Log log = LogFactory.getLog(PropertiesManager.class);

	private static final String OPTSICOM_PROPERTIES_LOCATION_PROPERTY = "optsicom.properties.location";

	private static final String PROPERTIES_FILE = "optsicom.properties";

	private static PropertiesManager instance;

	private static Properties properties;

	private PropertiesManager() {
	}

	public static PropertiesManager getInstance() {
		if (instance == null) {
			instance = new PropertiesManager();
			loadProperties();
		}
		return instance;
	}

	public String getProperty(String key) {
		String property = System.getProperty(key);

		if (property == null) {
			property = properties.getProperty(key);
			System.out.println("PropertiesManager.file.get." + key + "="
					+ property);
		} else {
			System.out.println("PropertiesManager.system.get." + key + "="
					+ property);
		}

		return property;
	}

	private static void loadProperties() {
		properties = new Properties();

		Path propertiesFile = null;
		try {

			String location = System
					.getProperty(OPTSICOM_PROPERTIES_LOCATION_PROPERTY);

			if (location != null) {
				propertiesFile = Paths.get(location);

				if (Files.isDirectory(propertiesFile)) {
					propertiesFile = propertiesFile.resolve(PROPERTIES_FILE);
				}

				if (!Files.exists(propertiesFile)) {
					log.warn("Optsicom properties not found in path '"
							+ propertiesFile + "'. Searching in workdir: '"
							+ Paths.get("").toAbsolutePath() + "'");
				}
			}

			if (propertiesFile == null) {
				propertiesFile = Paths.get(PROPERTIES_FILE);
				if (!Files.exists(propertiesFile)) {
					propertiesFile = Paths.get(System.getProperty("user.home"),
							".optsicom", PROPERTIES_FILE);
				}
			}

			if (Files.exists(propertiesFile)) {
				properties.load(Files.newBufferedReader(propertiesFile,
						Charset.forName("utf-8")));
			} else {
				log.debug("File optsicom.properties not found. Resorting to default values");
			}

		} catch (Exception e) {
			throw new Error("Couldn't load properties from file '"
					+ propertiesFile + "'", e);
		}
	}
}
