package com.bdd.stepdefinations;

import com.bdd.pages.PlanitContact_page;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlanitContact_stepDef extends PlanitContact_page{
	
	@When("user navigates to {string} in header section and validates page URL")
	public void user_navigates_to_in_header_section_and_validates_page_url(String header_name) throws Exception {
		workflowActionToClickHeader(header_name);
	}
	@Then("user clicks on {string} button")
	public void user_clicks_on_button(String button) throws Exception {
	    workflowActionToClickButton(button);
	}
	@Then("user populates mandatory fields and show them in the report")
	public void user_populates_mandatory_fields_and_show_them_in_the_report() {
		getListOfRequiredWebElements();
	}
	@Then("user verify error message {string} and enter value as {string}")
	public void user_verify_error_message_and_enter_value_as(String error_message, String input_value) throws Exception {
	    validateErrorAndEnterValues(error_message, input_value);
	}
	@Then("user populates mandatory fields without clicking Submit button and show them in report")
	public void user_populates_mandatory_fields_without_clicking_submit_button_and_show_them_in_report() {
	    listOfMandatoryFieldsWithoutClickingSubmitBtn();
	}
	@Then("user validates successful message after submitting form")
	public void user_validates_successful_message_after_submitting_form() throws Exception {
	    validateSuccessfulMessageOnContactPage();
	}
}
