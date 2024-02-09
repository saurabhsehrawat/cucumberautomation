package com.bdd.stepdefinations;

import com.bdd.pages.Spicejet_page;

import io.cucumber.java.en.Then;

public class Spicejet_stepDef extends Spicejet_page{
	@Then("user select {string} option to book flight")
	public void user_select_option_to_book_flight(String trip_type) throws Exception {
		workflowActionToSelectTrip(trip_type);
	}
	@Then("user select {string} location dropdown")
	public void user_select_location_dropdown(String from_to) throws Exception {
		workflowActionToSelectFromTo(from_to);
	}
	@Then("user move mouse to {string} location")
	public void user_move_mouse_to_location(String location) throws Exception {
		moveMouseToLocation(location);
	}
	@Then("user scroll to {string} location")
	public void user_scroll_to_location(String state) throws Exception {
	    scrollMouseToLocation(state);
	}
	@Then("user click on {string} dropdown")
	public void user_click_on_dropdown(String calendar) throws Exception {
	    workflowActionToSelectDate(calendar);
	}
	@Then("user click on next month and select date {int}")
	public void user_click_on_next_month_and_select_date(Integer date) throws Exception {
	    selectReturnDate(date);
	}
	@Then("user click on the {string} dropdown")
	public void user_click_on_the_dropdown(String dropdown_type) throws Exception {
	    selectPassengersDropdown(dropdown_type);
	}
	@Then("user select {int} {string}")
	public void user_select(Integer no_of_passengers, String passenger_type) throws Exception {
	    workflowActionToSelectPassengers(no_of_passengers, passenger_type);
	}
	@Then("user select currency as {string}")
	public void user_select_currency_as(String currency) throws Exception {
	   selectCurrencyType(currency);
	}
	@Then("user select traveller type as {string}")
	public void user_select_traveller_type_as(String traveller_type) throws Exception {
	    workflowActionToSelectTraveller(traveller_type);
	}
	@Then("user click on {string} button")
	public void user_click_on_button(String button) throws Exception {
	   clickOnSearchFlights(button);
	}
	@Then("user accept {string}")
	public void user_accept(String tnc) throws Exception {
	    workflowActionToAcceptTnC(tnc);
	}
}
