package com.Atos.Webaccess.Commun;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import com.Atos.Webaccess.Technical.FTP.FTPProperties;

/**
 * @author A689184
 *
 */
public class Commun {

	final long MILISECOND_PER_DAY = 24 * 60 * 60 * 1000;

	public int differenceBetweenTwoDate(Date d1) {

		int nbJour = 0;
		Date ToDay = new Date();
		nbJour = Math.round(Math.abs((ToDay.getTime() - d1.getTime()) / MILISECOND_PER_DAY));

		return nbJour;
	}

	public String readProp(String SearchedKey) {

		String returnValue = null;
		try {
			Properties prop = new Properties();
			String userDirectory = System.getProperty("user.dir");
			System.out.println("Current working directory: " + userDirectory);
			prop.load(FTPProperties.class.getResourceAsStream("/application.properties"));

			//InputStream input = new FileInputStream("./config/application.properties");
			//prop.load(input);
			// prop.load(FTPProperties.class.getResourceAsStream("/application.properties"));
			//input.close();

			returnValue = prop.getProperty(SearchedKey);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

}
