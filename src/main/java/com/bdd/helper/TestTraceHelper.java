package com.bdd.helper;

import com.bdd.pages.CommonPage;

public class TestTraceHelper {

	private BaseHelper BaseUtilObj = null;

	public BaseHelper utobj() {
		if (BaseUtilObj == null) {
			BaseUtilObj = new BaseHelper();
		}
		return BaseUtilObj;
	}

	private WaitHelper WaitObj = null;

	public WaitHelper eWait() {
		if (WaitObj == null) {
			WaitObj = new WaitHelper();
		}
		return WaitObj;
	}

	private BrowserHelper lbObj = null;

	public BrowserHelper browser() {
		if (lbObj == null) {
			lbObj = new BrowserHelper();
		}
		return lbObj;
	}

	private CommonPage lpObj = null;

	public CommonPage login() {
		if (lpObj == null) {
			lpObj = new CommonPage();
		}
		return lpObj;
	}

}
