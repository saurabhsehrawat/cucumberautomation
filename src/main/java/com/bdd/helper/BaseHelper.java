package com.bdd.helper;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.StringUtil;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.SkipException;


public class BaseHelper extends DriverHelper {
	
	public boolean isElementPresent(By by) {
		if (!driver.findElements(by).isEmpty()) {
			WebElement element = driver.findElement(by);
			return isElementPresent(element);
		} else {
			return false;
		}
	}

	public boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementNotPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementActive(By by) {
		WebElement element = driver.findElement(by);
		return isElementActive(element);
	}

	public boolean isElementActive(WebElement element) {
		try {
			sleep(500);
			moveToElement(element);
			if (element.isDisplayed() && element.isEnabled())
				;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementSelected(By by) {
		WebElement element = driver.findElement(by);
		try {
			moveToElement(element);
			element.isSelected();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * This method is used to move to element on the page.
	 */
	public WebElement moveToElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		base_util.eWait().waitForElementPresent(element);
		moveToElement(element);
		return element;
	}

	public WebElement moveToElement(WebElement element) throws Exception {
		try {
			// sleep(500);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100);window.scrollBy(-75,0);", element);
		} catch (Exception e) {
			Actions act = new Actions(driver);
			act.moveToElement(element).perform();
		}
		return element;
	}

	/*
	 * This method is used to enter data in field or date in date field
	 */

	public void sendKeys(By by, String value) throws Exception {
		base_util.eWait().WaitForVisibilityOfElementLocated(by, 60);
		WebElement element = driver.findElement(by);
		// moveToElement(by);
		try {
			clear(element);
			sendKeys(element, value);
		} catch (Exception e) {
			sendKeysByAction(element, value);
		}
	}

	public void sendKeysByAction(By by, String value) throws Exception {
		WebElement element = driver.findElement(by);
		sendKeysByAction(element, value);
	}

	public void sendKeysByAction(WebElement element, String value) throws Exception {
		Actions action = new Actions(driver);
		action.sendKeys(element, value);
	}

	public void sendKeys(WebElement element, String value) throws Exception {
		if (value != null) {
			moveToElement(element);
			boolean isFieldFound = false;
			try {
				element.click();
			} catch (Exception e) {
				Reporter.log(e.getMessage());
			}
			try {
				element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				element.sendKeys(Keys.DELETE);
			} catch (Exception e) {
				Reporter.log(e.getMessage());
			}
			try {
				element.clear();
			} catch (Exception e2) {
				Reporter.log(e2.toString());
			}
			try {
				element.sendKeys(value);
				isFieldFound = true;
			} catch (Exception e) {
				Reporter.log(e.getMessage());
				throwsException("Not able to enter data : " + element);
			}
			if (isFieldFound == false) {
				throwsException("Unable to enter data in field " + element.getText());
			}
		} else {
			throwsException("Supported Data not found, to be entered into the field : " + element);
		}
	}

	/*
	 * This method is used to sleep for 5 seconds
	 */
	public void sleep() throws Exception {
		sleep(5000);
	}

	/*
	 * This method is used to sleep for provided mili seconds
	 */
	public void sleep(int val) throws Exception {
		Thread.sleep(val);
		Reporter.log("Waited for " + val + " milliseconds");
	}

	public void logReport(String customMsg, WebElement elm) {
		String elementName = altElementNameForTestng(elm);
		int maxPadLength = 40;
		String paddingCharacter = " ";
		customMsg = StringUtil.join(customMsg, maxPadLength, paddingCharacter);
		// StringUtils.rightPad(customMsg, maxPadLength, paddingCharacter);
		Reporter.log(customMsg + " : " + elementName);
	}

	/*
	 * Click Enter on Any Element through Keys.Return
	 */
	public void clickEnterOnElement(WebElement element) throws Exception {
		moveToElement(element);
		try {
			click(element);
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
		sleep(500);
		logReport("Pressing Enter on Element : ", element);
		element.sendKeys(Keys.RETURN);
	}

	/*
	 * This method clicks on element without move instruction.
	 */
	public void clickElementWithoutMove(WebElement element) throws Exception {
		try {
			click(element);
		} catch (Exception e) {
			throwsException("Unable to click Element : " + element);
		}
	}

	public void clickElementByJS(WebElement element) throws Exception {
		boolean isElementClickable = false;
		for (int x = 0; x < 3; x++) {
			try {
				if (isElementClickable == false) {
					sleep(1000);
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
					isElementClickable = true;
				}
			} catch (Exception e) {
				Reporter.log(e.getMessage());
				isElementClickable = false;
			}
		}
		if (isElementClickable == false) {
			throwsException("Element not clickable : " + element);
		}
	}

	private void click(WebElement element) throws Exception {
		try {
			element.click();
		} catch (Exception e) {
			clickElementByJS(element);
		}
	}

	/*
	 * This method clicks on element Actions Class instruction.
	 */
	public void clickElementWithActions(WebElement element) throws Exception {
		try {
			Actions builder = new Actions(driver);
			element = base_util.eWait().getElementOnClickable(element);
			builder.moveToElement(element).click(element).build().perform();
		} catch (Exception e) {
			throwsException("Unable to click Element : " + element);
		}
	}

	public void clickElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		clickElementWithoutMove(element);
	}

	public void clickElement(WebElement element) throws Exception {
		base_util.eWait().getElementOnClickable(element);
		// element = moveToElement(element);
		try {

			click(element);
		} catch (Exception e) {
			clickElementByJS(element);
		}
	}

	// To handle stale element exception
	public void waitForElement(WebElement element) throws Exception {
		try {
			base_util.eWait().waitForElementPresent(element);
		} catch (Exception e) {
			throwsException("Unable to click Element : " + element);
		}
	}

	public String throwsException(String exceptionMsg) throws Exception {

		Reporter.log("");
		throw new Exception(exceptionMsg);
	}

	public Exception throwsException1(String exceptionMsg) throws Exception {
		Reporter.log("");
		throw new Exception(exceptionMsg);
	}

	public String throwsSkipException(String exceptionMsg) throws Exception {
		Reporter.log("");
		throw new SkipException(exceptionMsg);
	}

	private String altElementNameForTestng(WebElement elm) {
		String elementName = elm.toString();
		if (elementName.contains("[[")) {
			elementName = elementName.substring((elementName.indexOf("->") + 3), elementName.lastIndexOf("]"));
		}
		return elementName;
	}

	/*
	 * This method is created to Take screenshot
	 * 
	 * public void takeScreenshots() { try {
	 * 
	 * String src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	 * 
	 * // Reporter.addScreenCaptureFromPath("data:image/jpeg;base64," + src);
	 * 
	 * } catch (WebDriverException e) { e.getMessage(); } }
	 * 
	 * /* This method Double Clicks on Element
	 */
	public void doubleClickElement(WebElement element) throws Exception {
		Actions action = new Actions(driver);
		moveToElement(element);
		sleep(200);
		try {
			action.doubleClick(element).perform();
		} catch (Exception e) {
			Reporter.log(e.getMessage());
			printBugStatus("Error in finding element : " + element);
			try {
				throwsException("element not visible : " + element);
			} catch (Exception e1) {
				Reporter.log(e1.toString());
				e1.printStackTrace();
			}
			action.doubleClick(element).perform();
		}
	}

	public void doubleClickElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		doubleClickElement(element);
	}

	private WebElement getElementOnVisible(String locatorType, String value) throws Exception {
		return base_util.eWait().getElement(locatorType, value);
	}

//	private WebElement getElementOnClickable(String locatorType, String value) throws Exception {
//		return espl.eWait().getElementOnClickable(locatorType, value);
//	}

	public WebElement getElementByXpath(String xpath) throws Exception {
		return getElementOnVisible("xpath", xpath);
	}

	public WebElement getElementByID(String id) throws Exception {
		return getElementOnVisible("id", id);
	}

	public WebElement getElementByLinkText(String linkText) throws Exception {
		return getElementOnVisible("linkText", linkText);
	}

	public WebElement getElementByPartialLinkText(String partialLinkText) throws Exception {
		return getElementOnVisible("partialLinkText", partialLinkText);
	}

	public WebElement getElementByName(String name) throws Exception {
		return getElementOnVisible("name", name);
	}

	public WebElement getElementByCssSelector(String cssSelector) throws Exception {
		return moveToElement(getElementOnVisible("cssSelector", cssSelector));
	}

	public WebElement getElementByTagName(String tagName) throws Exception {
		return getElementOnVisible("tagName", tagName);
	}

	public WebElement getElementByClassName(String className) throws Exception {
		return getElementOnVisible("className", className);
	}

	public WebElement getElement(WebElement element) throws Exception {
		try {
			if (element.isDisplayed() == true || element.isEnabled() == true) {
				return element;
			}
		} catch (Exception e) {

		}
		return element;
	}

	/*
	 * This method is used to get String from web element list
	 */
	public List<String> getElementList(By by) throws Exception {
		List<WebElement> list = driver.findElements(by);
		if (list.size() > 0) {
			List<String> elementList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				moveToElement(list.get(i));
				String listText = list.get(i).getText();
				elementList.add(listText);
			}
			return elementList;
		} else {
			throw new Exception("Element not found : " + list);
		}
	}

	public List<String> getListAttributeColId(By by, String attributeType) throws Exception {
		List<WebElement> list = driver.findElements(by);
		if (list.size() > 0) {
			List<String> elementList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				moveToElement(list.get(i));
				String listText = list.get(i).getAttribute(attributeType);
				elementList.add(listText);
			}
			return elementList;
		} else {
			throw new Exception("Element not found : " + list);
		}
	}

	/*
	 * This method is used to select value in Action Menu
	 */
	public void selectElementByVisibleText(By by, String option) throws Exception {
		WebElement element = driver.findElement(by);
		selectElementByVisibleText(element, option);
	}

	public void selectElementByVisibleText(WebElement element, String option) throws Exception {
		Select select = new Select(element);
		moveToElement(element);
		select.selectByVisibleText(option);
	}

	/*
	 * This method is used to select option by index value
	 */
	public void selectElementByIndex(WebElement element, int option) throws Exception {
		Select select = new Select(element);
		moveToElement(element);
		select.selectByIndex(option);
	}

	/*
	 * This method is used to select option by value
	 */
	public void selectElementByValue(WebElement element, String option) throws Exception {
		Select select = new Select(element);
		moveToElement(element);
		select.selectByValue(option);
	}

	public void dragAndDropElement(WebElement element1, WebElement element2) throws Exception {
		Actions builder = new Actions(driver);
		org.openqa.selenium.interactions.Action dragAndDrop = builder.clickAndHold(element1).moveToElement(element2)
				.release(element2).build();
		dragAndDrop.perform();
	}

	/*
	 * This method is to report the error in the html report
	 */
	public void printBugStatus(String bugDescription) {
		Reporter.log("---------------------------------------------------------------");
		Reporter.log("Defect ## : " + bugDescription);
		Reporter.log("");
	}

	public boolean assertPageSource(String testData) throws Exception {
		boolean isTextPresent = false;
		String caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String small = "abcdefghijklmnopqrstuvwxyz";
		try {
			moveToElement(driver.findElement(By.xpath(".//*[contains(translate(text(),'" + caps + "', '" + small
					+ "'),'" + testData.toLowerCase() + "')]")));
			moveToElement(getElementOnVisible("xpath", ".//*[contains(translate(text(),'" + caps + "', '" + small
					+ "'),'" + testData.toLowerCase() + "')]"));
			isTextPresent = true;
		} catch (Exception e) {
			isTextPresent = false;
		}
		if (isTextPresent == false) {
			try {
				String pageSource = driver.findElement(By.tagName("body")).getText();
				pageSource = pageSource.toLowerCase();
				sleep();
				isTextPresent = pageSource.contains(testData.trim().toLowerCase());
				if (isTextPresent == false) {
					sleep();
					pageSource = driver.findElement(By.xpath("//html/body")).getText();
					pageSource = pageSource.toLowerCase();
					isTextPresent = pageSource.contains(testData.trim().toLowerCase());
				}
			} catch (Exception e) {
				isTextPresent = false;
			}
		}
		return isTextPresent;
	}

	public boolean assertPageSourceWithMultipleRecords(List<String> listItems) throws Exception {
		boolean isPresent = false;
		sleep();
		String pageSource = driver.findElement(By.tagName("body")).getText().toLowerCase();
		sleep();
		for (int x = 0; x < listItems.size(); x++) {
			isPresent = pageSource.contains(listItems.get(x).trim().toLowerCase());
			if (isPresent == false) {
				sleep();
				pageSource = driver.findElement(By.xpath("//html/body")).getText().toLowerCase();
				isPresent = pageSource.contains(listItems.get(x).trim().toLowerCase());
			}
			if (isPresent == false) {
				Reporter.log(listItems.get(x) + " Not Found in Page ");
				return false;
			} else {
				isPresent = true;
			}
		}
		return isPresent;
	}

	public boolean assertNotInPageSource(String testData) throws Exception {
		sleep(2000);
		String pageSource = driver.findElement(By.tagName("body")).getText();
		Boolean isPresent = pageSource.contains(testData);
		if (isPresent == false) {
			sleep();
			pageSource = driver.findElement(By.xpath("//html/body")).getText().toLowerCase();
			isPresent = pageSource.contains(testData.trim().toLowerCase());
		} else {
			return isPresent;
		}
		return isPresent;
	}

	/*
	 * This method is used to switch pop window
	 */

	public void switchToPopUwaitindow() throws InterruptedException {
		String parentWinHandle = driver.getWindowHandle();
		Set<String> winHandles = driver.getWindowHandles();
		for (String handle : winHandles) {
			if (!handle.equals(parentWinHandle)) {
				driver.switchTo().window(handle);
			}
		}
	}

	/*
	 * This method is used to Switch the frame
	 */
	public void switchFrame(WebElement frame) throws Exception {
		base_util.eWait().switchFrame(frame);
	}

	public void switchFrame(String frame) throws Exception {
		base_util.eWait().switchFrame(frame);
	}

	/*
	 * This method is used to Switch the frame
	 */
	public void switchFrameByClass(String className) throws Exception {
		base_util.eWait().switchFrameByClass(className);
	}

	/*
	 * This method is used to Switch the frame by frame id
	 */
	public WebDriver switchFrameById(String frameId) throws Exception {
		return base_util.eWait().switchFrameById(frameId);
	}

	/*
	 * This method is used to switch frame to defaults
	 */
	public WebDriver switchFrameToDefault(WebDriver driver) throws Exception {
		sleep(2000);
		driver.switchTo().defaultContent();
		sleep(2000);
		return driver;
	}

	// SWITCH TO PARENT FRAME // switches back to just one higher frame
	public WebDriver switchToParentFrame(WebDriver driver) throws Exception {
		sleep(2500);
		driver.switchTo().parentFrame();
		sleep(2500);
		return driver;
	}

	/* This method is used to get current date in MM/dd/yyyy format */
	public String currentDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String currentDateTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String currentDateyyyymmddFormat() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String lastDateOfMonth() {
		String currentDateSplitDD[] = currentDate().split("/");
		int result = Integer.parseInt(currentDateSplitDD[1]);
		Calendar aCalendar = Calendar.getInstance();
		if (result >= 12) {
			aCalendar.add(Calendar.MONTH, -1);
		} else {
			aCalendar.add(Calendar.MONTH, -2);
		}
		int max = aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		aCalendar.set(Calendar.DAY_OF_MONTH, max);
		Date lastDateOfMonth = aCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String systemDate = sdf.format(lastDateOfMonth);
		return systemDate;
	}

	public String lastDateOfMonthWithout12thDayLogic() {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.MONTH, -1);
		int max = aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		aCalendar.set(Calendar.DAY_OF_MONTH, max);
		Date lastDateOfMonth = aCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String systemDate = sdf.format(lastDateOfMonth);
		return systemDate;
	}

	public String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String dateTime = dateFormat.format(new Date()).toString();
		return dateTime;
	}

	public String getDDfromDate() {
		DateFormat dateFormat = new SimpleDateFormat("d");
		String dateTime = dateFormat.format(new Date()).toString();
		return dateTime;
	}

	/*
	 * This method is used to get current date us format
	 */
	public String getCurrentDateUSFormat() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String dateFormtter(String format) throws ParseException {
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		Date d = sdf.parse(format);
		sdf.applyPattern("MM/dd/yyyy HH:mm:ss");
		newDateString = sdf.format(d);
		return newDateString;
	}

	public String dateFormet(String format) throws ParseException {
		String newDateString;

		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		Date d = sdf.parse(format);
		sdf.applyPattern("MM/dd/yyyy");
		newDateString = sdf.format(d);
		return newDateString;
	}

	// Actual USA Time Zone Date
	public String getCurrentDateUSTimeZone() {
		TimeZone obj = TimeZone.getTimeZone("GMT-5");
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
		sdfDate.setTimeZone(obj);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	// Actual UTC Time Zone Date
	public String getCurrentDateUtcTime() {
		TimeZone obj = TimeZone.getTimeZone("GMT-2");
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		sdfDate.setTimeZone(obj);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String CurrentUtcDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss aa");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String UTCDate = dateFormat.format(date);

		return UTCDate;
	}

	public String currentDateTimeFormat(String formatType) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(formatType);
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public String getSelectedValue(By by) {
		WebElement element = driver.findElement(by);
		Select sel = new Select(element);
		String selectedValue = sel.getFirstSelectedOption().getText();
		return selectedValue;

	}

	public String getText(By by) throws Exception {
		WebElement element = driver.findElement(by);
		// moveToElement(element);
		return getText(element);
	}

	public String getText(WebElement element) throws Exception {
		moveToElement(element);
		return element.getText();
	}

	public String getTextWithoutMove(By by) throws Exception {
		WebElement element = driver.findElement(by);
		return getTextWithoutMove(element);
	}

	public String getTextWithoutMove(WebElement element) throws Exception {
		return element.getText();
	}

	public String getAttributeByValue(By by) throws Exception {
		WebElement element = driver.findElement(by);
		return getAttributeByValue(element);
	}

	public String getAttributeByValue(WebElement element) throws Exception {
		moveToElement(element);
		return element.getAttribute("value");
	}

	public void point(By by) {
		WebElement element = driver.findElement(by);
		point(element);
	}

	public void point(WebElement element) {
		Actions action = new Actions(driver);
		Point point = element.getLocation();
		int xCard = point.getX();
		int yCard = point.getY();
		action.moveToElement(element, xCard, yCard).click().build().perform();
	}

	public void scrollHorizontallyQuerySelector(int xpixel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.querySelector(\".ag-body-horizontal-scroll-viewport\").scroll(" + xpixel + ", 0)");
	}

	public String getAttributeByClass(By by) throws Exception {
		WebElement element = driver.findElement(by);
		return getAttributeByClass(element);
	}

	public String getAttributeByClass(WebElement element) throws Exception {
		moveToElement(element);
		return element.getAttribute("class");
	}

	public String getAttribute(By by, String attribute) throws Exception {
		WebElement element = driver.findElement(by);
		return getAttribute(element, attribute);
	}

	public String getAttribute(WebElement element, String attribute) throws Exception {
		// moveToElement(element);
		return element.getAttribute(attribute);
	}

	public String getBackgroundColor(By by) {
		String bckgclr = driver.findElement(by).getCssValue("background-color");
		return bckgclr;
	}

	public void contextClick(By by) throws Exception {
		WebElement element = driver.findElement(by);
		contextClick(element);
	}

	public void contextClick(WebElement element) throws Exception {
		moveToElement(element);
		Actions action = new Actions(driver);
		action.contextClick(element).perform();
	}

	public void actionSendKeys(By by, String value) throws InterruptedException {
		WebElement element = driver.findElement(by);
		actionSendKeys(element, value);
	}

	public void actionSendKeys(WebElement element, String value) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(value, Keys.TAB);
		actions.build().perform();
	}

	public void actionSendKeysEnter(By by, String value) throws InterruptedException {
		WebElement element = driver.findElement(by);
		actionSendKeysEnter(element, value);
	}

	public void actionSendKeysTab(By by, String value) throws InterruptedException {
		WebElement element = driver.findElement(by);
		actionSendKeysTab(element, value);
	}

	public void actionSendKeysEnter(WebElement element, String value) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(Keys.BACK_SPACE).sendKeys(value, Keys.ENTER).build().perform();
	}

	public void actionSendKeysTab(WebElement element, String value) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(Keys.BACK_SPACE).sendKeys(value, Keys.TAB).build().perform();
	}

	public void backSpaceKey(By by, int count) throws InterruptedException {
		WebElement element = driver.findElement(by);
		backSpaceKey(element, count);
	}

	public void backSpaceKey(WebElement element, int count) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		// actions.click();
		for (int i = 0; i < count; i++) {
			actions.sendKeys(Keys.BACK_SPACE);
			actions.build().perform();
		}
	}

	public void downKey(By by, int count) throws InterruptedException, AWTException {
		WebElement element = driver.findElement(by);
		downKey(element, count);
	}

	public void downKey(WebElement element, int count) throws AWTException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		for (int i = 0; i < count; i++) {
			actions.sendKeys(Keys.ARROW_DOWN);
			actions.build().perform();
		}
	}

	public void upKey(By by, int count) throws AWTException {
		WebElement element = driver.findElement(by);
		upKey(element, count);
	}

	public void upKey(WebElement element, int count) throws AWTException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		for (int i = 0; i < count; i++) {
			actions.sendKeys(Keys.ARROW_UP);
			actions.build().perform();
		}
	}

	public void cleanByJS(By by) {
		WebElement element = driver.findElement(by);
		cleanByJS(element);
	}

	public void cleanByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='';", element);
	}

	public void isDisplayed_fn(By by) {
		ExpectedConditions.visibilityOfAllElementsLocatedBy(by);
		driver.findElement(by).isDisplayed();
		Assert.assertTrue("Element " + by + " is not displayed on the application:", true);
	}

	public void sendKeys_fn(By by, String text) throws Exception {
		isDisplayed_fn(by);
		ExpectedConditions.elementToBeClickable(by);
		WebElement element = driver.findElement(by);
		try {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
		driver.findElement(by).sendKeys(text);
		Assert.assertTrue("Value " + text + " is not entered in the " + by + " field", true);
	}

	public void click_fn(By by) {
		isDisplayed_fn(by);
		ExpectedConditions.elementToBeClickable(by);
		driver.findElement(by).click();
		Assert.assertTrue("Element " + by + " is not clicked on the application", true);
	}

	public String getText_fn(By by) {
		isDisplayed_fn(by);
		return driver.findElement(by).getText();
	}

	public void getCurrentUrl(By by, String pageName) throws Exception {
		base_util.eWait().waitForElementPresent(by);
		String expected_url = getBaseUrl() + pageName;
		String actual_url = driver.getCurrentUrl();
		Assert.assertTrue(pageName + "is not redirect correctly" + actual_url,
				expected_url.equalsIgnoreCase(actual_url));
	}

	public void mouseHover(By by) throws Exception {
		WebElement element = driver.findElement(by);
		mouseHover(element);
	}

	public void mouseHover(WebElement element) throws Exception {
		moveToElement(element);
		Actions act = new Actions(driver);
		Action mouseHover = act.moveToElement(element).build();
		mouseHover.perform();
	}

	public String getLastDayOfCurrentMonth() throws Exception {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date lastDayOfMonth = calendar.getTime();
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String format = sdf.format(lastDayOfMonth);
		return format;
	}

	public void takeScreenshot() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
		String screenShotName = "target/screenshot/" + dateFormat.format(GregorianCalendar.getInstance().getTime());
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(String.format("%s.png", screenShotName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clear(WebElement element) throws Exception {
		try {
			element.clear();
		} catch (Exception e) {
			cleanByJS(element);
		}
	}

	public void clearElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		clear(element);
	}

	public void assertTrue(String message, boolean condition) {
		Assert.assertTrue(message, condition);
	}

	public String replaceString(String string) {
		String result = string.replaceAll("[$,% ]", "");
		return result;
	}

	public String decimalformat(String string) {
		double amount = Double.parseDouble(string);
		DecimalFormat formatter = new DecimalFormat("#.000");
		return formatter.format(amount);
	}

	public String decimalformat00(String string) {
		double amount = Double.parseDouble(string);
		DecimalFormat formatter = new DecimalFormat("#.00");
		return formatter.format(amount);
	}

	public String firstChar(String str) {
		if (str.charAt(0) == ' ') {
			str = str.substring(1);
		}
		return str;

	}

	public String createRandomCode(int codeLength, String id) {
		List<Character> temp = id.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
		Collections.shuffle(temp, new SecureRandom());
		return temp.stream().map(Object::toString).limit(codeLength).collect(Collectors.joining());
	}

	public long getLongRandomNumber() {
		Random rand = new Random();
		return rand.nextLong();
	}
	public long getShortRandomNumber() {
		Random rand = new Random();
		return rand.nextInt();
	}
	public boolean isAlertPresent() throws InterruptedException
	{
		Thread.sleep(5000);
		boolean presentFlag = false;
		  try {
			  for(int i=0;i<10;i++)
			  {
				  Alert alert = driver.switchTo().alert();
				  presentFlag = true;
				  alert.dismiss(); 
			  }
		  } catch (NoAlertPresentException ex) {
		   ex.printStackTrace();
		  }
		  return presentFlag;
	}
	public void createConnectionWithOutlook()
	{	
		try {
			testdata = excelreader.getData("TestData.xlsx", "Login");
			USERNAME = testdata.get(0).get("Username");
			PASSWORD = testdata.get(0).get("Password");
			prop = new Properties();
			prop.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        prop.put("mail.pop3.socketFactory.fallback", "false");
	        prop.put("mail.pop3.socketFactory.port", "995");
	        prop.put("mail.pop3.port", "995");
	        prop.put("mail.pop3.host", "outlook.office365.com");
	        prop.put("mail.pop3.user", USERNAME);
	        prop.put("mail.store.protocol", "pop3");
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			};
			session = Session.getDefaultInstance(prop, auth);
			outlook_store = session.getStore("pop3");
		    outlook_store.connect("outlook.office365.com", USERNAME, PASSWORD);
			}
		catch(Exception ex)
		{
			System.out.println("Connection to Office365 failed..");
		}
	}
	public String getLatestFilefromDir() throws InterruptedException {
		Thread.sleep(2000);
		File dir = new File(System.getProperty("user.dir") + "\\Download");
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			System.out.println("No file downloaded");
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
//	public void deleteFilesDirectory(File filename)
//	{
//		for (File subfile : filename.listFiles()) {
//            if (subfile.isDirectory()) {
//            	deleteFilesDirectory(subfile);
//            }
//            subfile.delete();
//        }
//	}
//	public String getFilename(File filename)
//	{
//		for (File subfile : filename.listFiles())
//		{
//           if(subfile.getName().contains("Transfer"))
//           {
//        	  getFilename(subfile);
//           }
//           else
//        	   continue;
//        }
//		return filename.getName();
//	}
}