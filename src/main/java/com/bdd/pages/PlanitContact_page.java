package com.bdd.pages;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.bdd.helper.DriverHelper;

public class PlanitContact_page extends DriverHelper{
	
	//unique elements from page
	By header_element=By.xpath("//a[contains(@href,'')]");
	By button_element=By.xpath("//a[text()='']");
	By error_element=By.xpath("//span[@id='']");
	By input_value_element=By.xpath("(//span[@id='']/preceding::input[@name=''])[1]");
	By alert_element=By.xpath("//div[contains(@class,'')]");
	
	//Saving Forename to String for validation purpose
	String forename;
	
	//navigating to header
	public void workflowActionToClickHeader(String header_name) throws Exception
	{
		header_element=By.xpath("//a[contains(@href,'"+header_name+"')]");
		base_util.eWait().WaitForElementToBeClickable(header_element, 10);
		base_util.utobj().clickElement(header_element);
		
		//validation performed for Page URL
		String expected_url="https://jupiter.cloud.planittesting.com/#/"+header_name;
		Assert.assertEquals(driver.getCurrentUrl(), expected_url, "Page URL does not match.");
	}
	
	//clicking button
	public void workflowActionToClickButton(String button) throws Exception
	{
		button_element=By.xpath("//a[text()='"+button+"']");
		base_util.eWait().WaitForElementToBeClickable(button_element, 10);
		base_util.utobj().clickElement(button_element);
		Thread.sleep(1500);
	}
	
	//populate list of mandatory elements after clicking Submit button on Contact page
	public void getListOfRequiredWebElements()
	{
		List<WebElement> mandatory_fields=driver.findElements(By.xpath("//*[@required='required']"));
		for(WebElement e:mandatory_fields)
		{
			//printing mandatory fields to Extent Report
			ExtentCucumberAdapter.addTestStepLog("Mandatory fields after clicking Submit button are <b><i><font color='red'>"+e.getAttribute("id")+"</font></i></b>");
		}
	}
	
	//validating all error messages and entering values to their fields
	public void validateErrorAndEnterValues(String error_message, String input_value) throws Exception
	{
		if(error_message.equals("Forename is required"))
		{
			error_element=By.xpath("//span[@id='forename-err']");
			//validation performed on error message
			Assert.assertEquals(driver.findElement(error_element).getText(), error_message, "Error_message does not match.");
			base_util.eWait().waitForElementPresent(error_element);
			
			//input value to input field
			input_value_element=By.xpath("(//span[@id='forename-err']/preceding::input[@name='forename'])[1]");
			base_util.eWait().WaitForElementToBeClickable(input_value_element, 10);
			base_util.utobj().sendKeys(input_value_element, input_value);
			//saving forename value to class level variable for success message validation
			forename=input_value;
		}
		if(error_message.equals("Email is required"))
		{
			error_element=By.xpath("//span[@id='email-err']");
			//validation performed on error message
			Assert.assertEquals(driver.findElement(error_element).getText(), error_message, "Error_message does not match.");
			base_util.eWait().waitForElementPresent(error_element);
			
			//input value to input field
			input_value_element=By.xpath("(//span[@id='email-err']/preceding::input[@name='email'])[1]");
			base_util.eWait().WaitForElementToBeClickable(input_value_element, 10);
			base_util.utobj().sendKeys(input_value_element, input_value);
		}
		if(error_message.equals("Message is required"))
		{
			error_element=By.xpath("//span[@id='message-err']");
			//validation performed on error message
			Assert.assertEquals(driver.findElement(error_element).getText(), error_message, "Error_message does not match.");
			base_util.eWait().waitForElementPresent(error_element);
			
			//input value to text area field
			input_value_element=By.xpath("(//span[@id='message-err']/preceding::textarea[@name='message'])[1]");
			base_util.eWait().WaitForElementToBeClickable(input_value_element, 10);
			base_util.utobj().sendKeys(input_value_element, input_value);
		}
	}
	
	//populate list of required elements without clicking Submit button on Contact page
	public void listOfMandatoryFieldsWithoutClickingSubmitBtn()
	{
		List<WebElement> required_elements=driver.findElements(By.xpath("//span[@class='req']/parent::label"));
		for(WebElement e:required_elements)
		{
			//printing mandatory fields to Extent Report
			ExtentCucumberAdapter.addTestStepLog("Required fields before clicking Submit button are <b><i><font color='blue'>"+e.getAttribute("for")+"</font></i></b>");
		}
	}
	
	//validates successful message after submitting Contact form
	public void validateSuccessfulMessageOnContactPage() throws Exception
	{
		alert_element=By.xpath("//div[contains(@class,'alert')]");
	
		base_util.eWait().waitForProgressBarToComplete(10, 10);
		String actual_success_msg=base_util.utobj().getText(alert_element);
		String expected_success_msg="Thanks "+forename+", we appreciate your feedback.";
		//validation performed below
		ExtentCucumberAdapter.addTestStepLog("Success message should be <b><font color='green'><i>"+expected_success_msg+"</i></font></b>");
		Assert.assertEquals(actual_success_msg, expected_success_msg, "Success message does not match.");
	}
}
