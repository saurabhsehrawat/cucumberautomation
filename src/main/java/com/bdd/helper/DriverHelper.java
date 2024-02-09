package com.bdd.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;

public class DriverHelper {
	protected static WebDriver driver;
	protected TestTraceHelper base_util = new TestTraceHelper();
	ExcelReaderHelper excelreader = new ExcelReaderHelper();
	protected static List<Map<String, String>> testdata;
	
//	protected static String project_name;
	protected static String USERNAME;
	protected static String PASSWORD;
	protected static Properties prop;
	protected static Session session;
	protected static Store outlook_store;
//	protected static Folder folder;

	public static String getDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		return df.format(new Date());
	}

	public String getBaseUrl() throws InvalidFormatException, IOException {
		String base = "";
		//testdata = excelreader.getData("TestData.xlsx", "Env");
		//String b360_qa = testdata.get(0).get("URL");
		//String b360_prod = testdata.get(1).get("URL");
		String environment = System.getProperty("env", "planit");
		switch (environment) {
		case "planit":
			base="http://jupiter.cloud.planittesting.com";
			break;
		case "spicejet":
			base = "https://www.spicejet.com/";
			break;
		}
		return base;
	}
}
