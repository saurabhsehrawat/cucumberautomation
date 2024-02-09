Feature: Testing execution of Five9 API
@Five9
Scenario Outline: To validate if Five9 API can be executed successfully
	Given I set Base URL as "http://172.20.21.216:94"
	When I set header content type as "<ContentType>"
	When I hit the Five9 API with request body where resource name is "<ResourceName>"
	Then I validate if status code is "<StatusCode>" and API is "Five9"
	Then I validate the Response value message is from rownumber <RowNumber>
	
	
	
	 Examples: 
      |ContentType      | ResourceName 			| StatusCode | RowNumber	|
      |application/json	| /api/Five9Request |        200 | 0					|
     