package com.affnetz.qa.test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.playWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.pages.LoginPageRepo;



public class Logintest {
	
	
	Page page;
	LoginPageRepo lp;
	
	@BeforeTest
	public void setUp()
	{
//		pf=new playWrightFactory();
		page=playWrightFactory.intitBrowser("chromium");
		lp=new LoginPageRepo(page);
	}
	
	@Test(priority =0,testName = "isLoginPageOpen")
	public void checkLoginPage()
	{
		String title=lp.getLoginPageTitle();
		Assert.assertEquals(title, "T1");
	}
	
	@Test(priority =1,testName = "Enter Cradentilas", dependsOnMethods = "checkLoginPage")
	public void doLoginWithValidCradential()
	{
		lp=new LoginPageRepo(page);
		lp.doLogin("t1admin", "%^&$T1Affnetz#$");
		lp.isLogin();
		
	}
	
	@Test(priority = 2,testName="Do Logout",dependsOnMethods = "doLoginWithValidCradential")
	public void dologout() throws InterruptedException {
		lp=new LoginPageRepo(page);
		lp.doLogout();
		lp.isLogout();
	}
	
	@DataProvider (name = "invalidData")
	public Object[][] dpMethod() {
	    return new Object [][] {
	    	{"t1admin","sbahv"},
	    	{"fvbsdjh","%^&$T1Affnetz#$"},
	    	{"t1admin",""},
	    	{"","%^&$T1Affnetz#$"},
	    	{"",""}
	    	
	    };
	    	
	    }
	@Test(priority = 3,testName = "Invalid Login",dataProvider = "invalidData")
	public void doLoginWithInvalidCradentilas(String uname,String pwd)
	{
		lp=new LoginPageRepo(page);
		lp.doLogin(uname, pwd);
		lp.isErrMsgShowing();
		

	}
	

	
	
	

	
	

}
