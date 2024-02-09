package com.bdd.pages;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.bdd.helper.DriverHelper;
//import com.bdd.helper.ExcelReaderHelper;

public class CommonPage extends DriverHelper {
	//ExcelReaderHelper excelreader = new ExcelReaderHelper();
	//protected static List<Map<String, String>> testdata;
	
	public void goTo(String app_name) throws Exception {
		if(app_name.equals("Spicejet")||app_name.equals("Planit Jupiter Toys"))
		{
			String geturl = getBaseUrl();
			driver.get(geturl);
		}
//		if(app_name.equals("B360") || app_name.equals("ET"))
//		{
//			String geturl = getBaseUrl();
//			testdata = excelreader.getData("TestData.xlsx", "Login");
//			String username = testdata.get(0).get("Username");
//			String password = testdata.get(0).get("Password");
//			if(geturl.equals("projects.bsifinancial.com"))		
//			{
//				driver.get("https://"+username+":"+password+"@"+geturl);//for B360 on PROD
//			}
//			else
//			{
//					driver.get("http://"+username+":"+password+"@"+geturl);//for B360 on QA
//			}
//			Thread.sleep(3000);
//		}
	}
	public String getLatestFileFromDir() throws InterruptedException {
		Thread.sleep(2000);
		File dir = new File(System.getProperty("user.dir") + "\\Download");
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			System.out.println("No any file downloaded");
			return "No File";
		}
		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile.getName();
	}
}

