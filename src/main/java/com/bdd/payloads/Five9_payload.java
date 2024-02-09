package com.bdd.payloads;

public class Five9_payload {
	public static String five9payload()
	{
		return "{\r\n"
				+ "    \"USR_FLD_168\":\"USR_FLD_168\",\r\n"
				+ "    \"Acct_Number\":\"9900000003\",\r\n"
				+ "    \"CallAuthenticated\":\"CallAuthenticateds\",\r\n"
				+ "    \"ClientNameShort\":\"Client Names\",\r\n"
				+ "    \"SpecialLoanType\":\"SpecialLoanTypse\",\r\n"
				+ "    \"InvestorCode\":\"5201\",\r\n"
				+ "    \"MyLoanWebAcct\":\"\",\r\n"
				+ "    \"EPresentment\":\"\",\r\n"
				+ "    \"ReoccuringDraftStatus\":\"ReoccuringDraftStatuss\",\r\n"
				+ "    \"FGMCRefi\":\"Y\",\r\n"
				+ "    \"ServicingType\":\"Agency\",\r\n"
				+ "    \"CallReason\": \"Taxess\"\r\n"
				+ "}";
	}
	public static String five9JsonResponse()
	{
		return "{\r\n"
				+ "  \"html\": {\r\n"
				+ "    \"body\": {\r\n"
				+ "      \"p\": [\r\n"
				+ "        \"Loan Number:9900000003\",\r\n"
				+ "        \"Client Name:Client Names\",\r\n"
				+ "        \"Servicing Type:Agency\",\r\n"
				+ "        \"Authentication Status:CallAuthenticateds\",\r\n"
				+ "        \"Reason for Call:Taxess\",\r\n"
				+ "        \"Refinance Candidate:LT Candidate\",\r\n"
				+ "        \"Hippo Offer:Hippo Offer Eligible\",\r\n"
				+ "        \"MyLoanWeb:Not Registered\",\r\n"
				+ "        \"E-Presentment:Not Enrolled\",\r\n"
				+ "        \"Recurring ACH:Not Enrolled\"\r\n"
				+ "      ]\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}";
	}
}
