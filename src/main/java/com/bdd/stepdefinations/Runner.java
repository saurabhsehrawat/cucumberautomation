package com.bdd.stepdefinations;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/UI", plugin = {
		"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		 "json:target/cucumber.json",
		"junit:target/testResult.xml"}, dryRun=false, monochrome=false, tags=("@Planit"))

public final class Runner {
	public static void main(String[] args) {
		JUnitCore.main(Runner.class.getName());
	}
}
