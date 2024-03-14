package com.qa.affnetz.pages;

import com.microsoft.playwright.Page;

public class HomePageRepo {
	
	Page page;
	private String Home="//div[text()='Home']";
//	private String logoutButton="xpath=//a[@title='logout']";
	private String superAdminText="xpath=//div[text()='Super Admin']";
	
	public HomePageRepo(Page page)
	{
		this.page=page;
	}
	
	public void clickOnHome()
	{
		page.click(Home);
	}
	

}
