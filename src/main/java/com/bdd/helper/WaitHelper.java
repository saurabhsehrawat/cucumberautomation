package com.bdd.helper;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


public class WaitHelper extends DriverHelper {

	By progressBar = By.id("loading-bar");

	public void WaitForTitle(String title, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.titleIs(title));
	}

	public void WaitForElementToBeClickable(By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void WaitForVisibilityOfElementLocated(By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	public void WaitForInvisibilityOfElementLocated(By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public void WaitForElementToBeSelected(By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeSelected(by));
	}

	private void checkPageIsLoaded() throws Exception {
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		try {
			driver.getCurrentUrl();
		} catch (Exception E) {
			throw new Exception(E.getMessage());
		}

	}

	/*
	 * public void fluentWaitMethod(){
	 * 
	 * @SuppressWarnings("deprecation") FluentWait<WebDriver> wait = new
	 * FluentWait<WebDriver>(driver).withTimeout(60,
	 * TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS);
	 * 
	 * }
	 */
	WebDriver switchFrame(WebElement frame) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));

		Thread.sleep(2000);

		return driver;
	}

	WebDriver switchFrame(String frame) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));

		return driver;
	}

	WebDriver switchFrameByClass(String className) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className(className)));

		return driver;

	}

	WebDriver switchFrameById(String frameId) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));

		return driver;
	}

	WebElement getElement(String locatorType, String value) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		try {

			if (locatorType.equalsIgnoreCase("id")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.id(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("name")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.name(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("className")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.className(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.linkText(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.partialLinkText(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("cssSelector") || locatorType.equalsIgnoreCase("css")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.cssSelector(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("tagName")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.tagName(value));
					}
				});
				return foo;
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(value));
					}
				});
				return foo;
			} else {
				throw new Exception("Element not defined properly : " + locatorType + " : " + value);
			}
		} catch (Exception e) {
			throw new Exception("Element not found : " + locatorType + " : " + value + "\n" + e.getMessage());
		} finally {

		}
	}

	WebElement getElementOnClickable(String locatorType, String value) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		try {

			if (locatorType.equalsIgnoreCase("id")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.id(value)));
			} else if (locatorType.equalsIgnoreCase("name")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.name(value)));
			} else if (locatorType.equalsIgnoreCase("className")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.className(value)));
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value)));
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(value)));
			} else if (locatorType.equalsIgnoreCase("cssSelector")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(value)));
			} else if (locatorType.equalsIgnoreCase("tagName")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.tagName(value)));
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
			} else {
				throw new Exception("Element not defined properly : " + locatorType + " : " + value);
			}
		} catch (Exception e) {
			throw new Exception("Element not found : " + locatorType + " : " + value + "\n" + e.getMessage());
		} finally {
		}
	}

	WebElement getElementOnClickable(WebElement element) throws Exception {

		checkPageIsLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	Alert getAlert(WebDriver driver) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 60, 100);
		try {
			Alert foo = wait.until(new Function<WebDriver, Alert>() {
				@Override
				public Alert apply(WebDriver driver) {
					return driver.switchTo().alert();
				}
			});
			return foo;
		} catch (Exception e) {
			throw new Exception("Test case Expected an alert message to appear, which is not found");
		} finally {
		}
	}

	public void waitForElementPresent(By by) throws Exception {
		WebElement element = driver.findElement(by);
		waitForElementPresent(element);
	}

	public void waitForElementPresent(WebElement element) throws Exception {
		checkPageIsLoaded();
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, 60, 100)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				element.getText();
				return element != null && (element.isDisplayed() || element.isEnabled());
			}
		});
	}

	public void WaitForAttributeToBe(By by, String attribute, String value, int waitTime) {
		WebElement element = driver.findElement(by);
		WaitForAttributeToBe(element, attribute, value, waitTime);
	}

	public void WaitForAttributeToBe(WebElement element, String attribute, String value, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
	}

	public void WaitForAttributeContains(By by, String attribute, String value, int waitTime) {
		WebElement element = driver.findElement(by);
		WaitForAttributeContains(element, attribute, value, waitTime);
	}

	public void WaitForAttributeContains(WebElement element, String attribute, String value, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.attributeContains(element, attribute, value));
	}

	public void waitForProgressBarToComplete(int visibileWaitTime, int invisibileWaitTime) {
		try {
			WaitForVisibilityOfElementLocated(progressBar, visibileWaitTime);
			WaitForInvisibilityOfElementLocated(progressBar, invisibileWaitTime);
		} catch (NoSuchElementException nsee) {

		} catch (TimeoutException to) {

		}

	}

	public void WaitForElementToBeClickable(WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		
	}

}
