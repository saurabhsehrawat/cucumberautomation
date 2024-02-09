package com.bdd.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserHelper extends DriverHelper {

	public void openbrowser() throws Exception {
		String headlessoption = System.getProperty("mode", "visual");
		String browser = System.getProperty("browser", "chrome");

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			ChromeDriverService driverService =
			ChromeDriverService.createDefaultService();
			options.addArguments("--start-maximized");
			options.addArguments("--no-sandbox");
			options.addArguments("--enable-automation");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			

			switch (headlessoption) {
			case "headless":
				options.setHeadless(true);
				break;
			case "visual":
				options.setHeadless(false);
				break;
			}

			driver = new ChromeDriver(driverService, options);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			// driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			Map<String, Object> commandParams = new HashMap<>();
			commandParams.put("cmd", "Page.setDownloadBehavior");
			Map<String, String> params = new HashMap<>();
			params.put("behavior", "allow");
			params.put("downloadPath", System.getProperty("user.dir")+"\\Download");
			commandParams.put("params", params);
			ObjectMapper objectMapper = new ObjectMapper();
			HttpClient httpClient = HttpClientBuilder.create().build();
			String command = objectMapper.writeValueAsString(commandParams);
			String u = driverService.getUrl().toString() + "/session/" + ((RemoteWebDriver) driver).getSessionId()
					+ "/chromium/send_command";
			HttpPost request = new HttpPost(u);
			request.addHeader("content-type", "application/json");
			request.setEntity(new StringEntity(command));
			httpClient.execute(request);

			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefox = new FirefoxOptions();
			switch (headlessoption) {
			case "headless":
				firefox.setHeadless(true);
				break;
			case "visual":
				firefox.setHeadless(false);
				break;
			}
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

			driver = new FirefoxDriver(firefox);
			break;

		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

}
