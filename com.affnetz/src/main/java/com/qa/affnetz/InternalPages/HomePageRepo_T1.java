package com.qa.affnetz.InternalPages;

import com.microsoft.playwright.Page;

public class HomePageRepo_T1 {
	
	Page page;
	private String Home="//div[text()='Home']";
//	private String logoutButton="xpath=//a[@title='logout']";
	private String superAdminText="xpath=//div[text()='Super Admin']";
	
	public HomePageRepo_T1(Page page)
	{
		this.page=page;
	}
	
	public void clickOnHome()
	{
		page.click(Home);
	}
	

}