package com.bdd.stepdefinations;

import com.bdd.pages.CommonPage;

import io.cucumber.java.en.Given;

public class CommonPage_stepDef {
	CommonPage common = new CommonPage();

	@Given("initialise the browser opening {string}")
	public void initialise_the_browser_opening(String app_name) throws Exception {
		common.goTo(app_name);
	}
}
