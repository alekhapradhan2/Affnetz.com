package com.affnetz.qa.test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory_T1;
import com.microsoft.playwright.Page;
import com.qa.affnetz.pages.LoginPageRepo_T1;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Logintest_T1{
	
	
	Page page;
	LoginPageRepo_T1 lp;
	
	@BeforeTest
	public void setUp()
	{
//		pf=new playWrightFactory();
		page=PlayWrightFactory_T1.intitBrowser("chromium","login");
		lp=new LoginPageRepo_T1(page);
	}
	
	@Test(priority =0,testName = "isLoginPageOpen")
	public void checkLoginPage()
	{
		assertThat(page).hasURL("https://t1.affnetz.org/login");
	}
	
	@Test(priority =1,testName = "Enter Cradentilas", dependsOnMethods = "checkLoginPage")
	public void doLoginWithValidCradential()
	{
		lp=new LoginPageRepo_T1(page);
		lp.doLogin("t1admin", "%^&$T1Affnetz#$");
		lp.isLogin();
	}
	
	@Test(priority = 2,testName="Do Logout",dependsOnMethods = "doLoginWithValidCradential")
	public void dologout() throws InterruptedException {
		lp=new LoginPageRepo_T1(page);
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
		lp=new LoginPageRepo_T1(page);
		lp.doLogin(uname, pwd);
		lp.isErrMsgShowing();
		

	}
	

	
	
	

	
	

}
