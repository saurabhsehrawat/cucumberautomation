Feature: To test Jupiter Test Toys Application
Background:
Given initialise the browser opening "Planit Jupiter Toys"

@PlanitTest1 @Planit
Scenario: To validate error messages and mandatory fields on Contact Page
	When user navigates to "contact" in header section and validates page URL
	Then user clicks on "Submit" button
	And user populates mandatory fields and show them in the report
	And user verify error message "Forename is required" and enter value as "Saurabh"
	And user verify error message "Email is required" and enter value as "saurabh@xyz.com"
	And user verify error message "Message is required" and enter value as "Hello there!"
	Then user clicks on "Submit" button
	
@PlanitTest2 @Planit
Scenario: To validate successful submission message on Contact page
	When user navigates to "contact" in header section and validates page URL
	Then user populates mandatory fields without clicking Submit button and show them in report
	Then user clicks on "Submit" button
	And user verify error message "Forename is required" and enter value as "Sehrawat"
	And user verify error message "Email is required" and enter value as "sehrawat@xyz.com"
	And user verify error message "Message is required" and enter value as "Dear Sehrawat!"
	Then user clicks on "Submit" button
	And user validates successful message after submitting form
	
@PlanitTest3 @Planit
Scenario: To validate shopping functionality in the cart and subtotals of items added
	When user navigates to "shop" in header section and validates page URL
	Then user buy 2 "Stuffed Frog" and add to shopping cart and get the product price
	Then user buy 5 "Fluffy Bunny" and add to shopping cart and get the product price
	Then user buy 3 "Valentine Bear" and add to shopping cart and get the product price
	And user go to shopping "cart" page
	Then user verify subtotal and price for each product in the cart
	And user verify sum total value of all the products in the cart
	