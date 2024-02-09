package com.bdd.stepdefinations;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.bdd.helper.BrowserHelper;
import com.bdd.helper.DriverHelper;

public class Hooks extends DriverHelper {

	BrowserHelper browser = new BrowserHelper();

	@Before
	public void openBrowser() throws Exception {
		browser.openbrowser();
	}

	@After
	public void embedScreenshotAfter(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			String src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			try {
				ExtentCucumberAdapter.addTestStepScreenCaptureFromPath("data:image/jpeg;base64," + src);
				//Reporter.addScreenCaptureFromPath("data:image/jpeg;base64," + src);
			} catch (Exception e) {
				e.getMessage();
			}
			finally {
				driver.quit();
			}
		}
		else
			driver.quit();
	}
}

