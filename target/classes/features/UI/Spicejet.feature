Feature: To test Spicejet website

@SpiceJet
Scenario: To test booking a round trip flight
	Given initialise the browser opening "Spicejet"
	Then user select "round trip" option to book flight
	And user select "From" location dropdown
	And user move mouse to "Select a region" location
	And user scroll to "GAU" location
	And user move mouse to "Select a region" location
	And user scroll to "IXB" location
	Then user click on "Departure Date" dropdown
	Then user click on "Return Date" dropdown
	Then user click on next month and select date 5
	Then user click on the "Passengers" dropdown
	And user select 2 "Adult"
	And user select 2 "Children"
	And user select 1 "Infant"
	Then user click on the "Currency" dropdown
	And user select currency as "USD"
	Then user select traveller type as "Family & Friends"
	And user click on "Search Flight" button
	And user accept "Terms & Conditions"
	
