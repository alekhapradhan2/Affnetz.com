package com.affnetz.qa.test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory_T1;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.qa.affnetz.Publicapages.LoginPageRepo_T1;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.IOException;


public class Logintest_T1{
	
	
	Page page;
	Browser browser;
	LoginPageRepo_T1 lp;
	
	@BeforeTest
	public void setUp() throws IOException
	{
		page=PlayWrightFactory_T1.intitBrowser("login");
		lp=new LoginPageRepo_T1(page);
	}
	
	@Test(priority =0,testName = "isLoginPageOpen")
	public void checkLoginPage()
	{
		assertThat(page).hasURL("https://t1.affnetz.org/login");
	}
	
	@Test(priority =1,testName = "Enter Cradentilas", dependsOnMethods = "checkLoginPage")
	public void doLoginWithValidCradential() throws IOException
	{
		lp=new LoginPageRepo_T1(page);
		PlayWrightFactory_T1.login();
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
