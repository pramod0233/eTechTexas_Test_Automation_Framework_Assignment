package com.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.constants.Environments;

public class PropertiesUtil {

	public static String readProperty(Environments evn, String propertyName) {

		System.out.println(System.getProperty("user.dir"));
		File file = new File(
				System.getProperty("user.dir") + File.separator + "config" + File.separator + evn + ".properties");
		FileReader fileReader = null;
		Properties properties = new Properties();
		try {
			fileReader = new FileReader(file);
			properties.load(fileReader);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		String value = properties.getProperty(propertyName.toUpperCase());
		return value;

	}
}
