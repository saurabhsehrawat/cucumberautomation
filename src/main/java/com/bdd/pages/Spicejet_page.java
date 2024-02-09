package com.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.bdd.helper.DriverHelper;

public class Spicejet_page extends DriverHelper{
	Actions action=new Actions(driver);
	JavascriptExecutor jse = (JavascriptExecutor) driver;
	
	By location_element=By.xpath("//div[text()='AGR']/parent::div/parent::div");
	By next_month=By.xpath("(//*[@data-testid='undefined-calendar-picker']//*[local-name()='svg'])[1]");
	
	public void workflowActionToSelectTrip(String trip_type) throws Exception
	{
		By trip_element=By.xpath("//div[text()='"+trip_type+"']/parent::div/parent::div//*[local-name()='svg']");
		base_util.eWait().waitForElementPresent(trip_element);
		base_util.utobj().clickElement(trip_element);
	}
	public void workflowActionToSelectFromTo(String from_to) throws Exception
	{
		By from_to_element=By.xpath("//div[text()='"+from_to+"']/parent::div/div/input");
		base_util.eWait().waitForElementPresent(from_to_element);
		base_util.utobj().clickElement(from_to_element);
	}
	public void moveMouseToLocation(String location) throws Exception
	{
		By div_element=By.xpath("//div[contains(text(),'"+location+"')]/parent::div/parent::div");
		base_util.eWait().waitForElementPresent(div_element);
		base_util.utobj().moveToElement(div_element);
	}
	public void scrollMouseToLocation(String state) throws Exception
	{
		location_element=By.xpath("//div[text()='"+state+"']/parent::div/parent::div");
		WebElement state_element=driver.findElement(location_element);
		action.moveToElement(state_element, 0,250);
		action.build().perform();
		base_util.eWait().waitForElementPresent(location_element);
		base_util.utobj().clickElement(location_element);
	}
	public void workflowActionToSelectDate(String calendar) throws Exception
	{
		By date_element=By.xpath("(//div[text()='"+calendar+"']/following-sibling::div/div)[1]");
		base_util.eWait().waitForElementPresent(date_element);
		base_util.utobj().clickElement(date_element);
		if(calendar.equals("Departure Date"))
		{
			Thread.sleep(500);
			base_util.utobj().clickElement(date_element);
		}
		else if(calendar.equals("Return Date"))
		{
			base_util.eWait().waitForElementPresent(date_element);
			base_util.utobj().clickElement(date_element);
		}
	}
	public void selectReturnDate(Integer date) throws Exception
	{
		By return_date=By.xpath("(//*[@data-testid='undefined-calendar-picker']//*[contains(@data-testid,'undefined-month')])[3]//div[text()='"+date+"']/parent::div");
		next_month=By.xpath("(//*[@data-testid='undefined-calendar-picker']//*[local-name()='svg'])[1]");
		base_util.eWait().WaitForElementToBeClickable(next_month, 30);
		base_util.utobj().clickElement(next_month);
		base_util.eWait().WaitForVisibilityOfElementLocated(return_date, 30);
		base_util.utobj().clickElement(return_date);
	}
	public void selectPassengersDropdown(String dropdown_type) throws Exception
	{
		By passengers_dropdown=By.xpath("//*[text()='"+dropdown_type+"']/parent::div");
		base_util.eWait().waitForElementPresent(passengers_dropdown);
		base_util.utobj().clickElement(passengers_dropdown);
		if(dropdown_type.equals("Currency"))
		{
			WebElement currency_values=driver.findElement(By.xpath("//*[text()='"+dropdown_type+"']/parent::div/following-sibling::div"));
			action.moveToElement(currency_values, 0,250);
			action.build().perform();
		}
	}
	public void workflowActionToSelectPassengers(Integer no_of_passengers, String passenger_type) throws Exception
	{
		By passenger_element=By.xpath("//*[text()='"+passenger_type+"']/parent::div/following-sibling::div//*[contains(@data-testid,'plus-one')]");
		for(int i=0;i<no_of_passengers;i++)
		{
			base_util.eWait().WaitForElementToBeClickable(passenger_element, 10);
			base_util.utobj().clickElement(passenger_element);
		}
		if(passenger_type.equals("Adult"))
		{
			ExtentCucumberAdapter.addTestStepLog("No. of Adults selected will always be +1 as per website functionality..");
		}
	}
	public void selectCurrencyType(String currency) throws Exception
	{
		By currency_type=By.xpath("//*[text()='Currency']/parent::div/following-sibling::div//*[text()='"+currency+"']");
		base_util.eWait().waitForElementPresent(currency_type);
		base_util.utobj().clickElement(currency_type);
	}
	public void workflowActionToSelectTraveller(String traveller_type) throws Exception
	{
		By radio_button=By.xpath("//*[contains(text(),'"+traveller_type+"')]/parent::div/parent::div//*[local-name()='circle']");
		base_util.eWait().waitForElementPresent(radio_button);
		base_util.utobj().clickElement(radio_button);
	}
	public void clickOnSearchFlights(String button) throws Exception
	{
		WebElement button_element=driver.findElement(By.xpath("//*[text()='"+button+"']/parent::div[@data-testid='home-page-flight-cta']"));
		base_util.eWait().WaitForElementToBeClickable(button_element, 30);
		action.moveToElement(button_element);
		action.build().perform();
		base_util.utobj().clickElement(button_element);
	}
	public void workflowActionToAcceptTnC(String tnc) throws Exception
	{
		By tnc_element=By.xpath("//*[text()='"+tnc+"']/parent::a/parent::div/parent::div/preceding-sibling::div//*[local-name()='rect']");
		base_util.eWait().WaitForVisibilityOfElementLocated(tnc_element, 30);
		base_util.utobj().clickElement(tnc_element);
	}
}
