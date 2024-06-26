package com.affnetz.qa.test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.qa.affnetz.Publicapages.LoginPageRepo;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.IOException;


public class Logintest{
	
	
	Page page;
	Browser browser;
	LoginPageRepo lp;
	
	@BeforeTest
	public void setUp() throws IOException
	{
		page=PlayWrightFactory.intitBrowser("login");
		lp=new LoginPageRepo(page);
	}
	
	@Test(priority =0,testName = "isLoginPageOpen")
	public void checkLoginPage()
	{
		assertThat(page).hasURL("https://t1.affnetz.org/login");
	}
	
	@Test(priority =1,testName = "Enter Cradentilas", dependsOnMethods = "checkLoginPage")
	public void doLoginWithValidCradential() throws IOException
	{
		lp=new LoginPageRepo(page);
		PlayWrightFactory.login();
		lp.isLogin();
		
	}
	
	@Test(priority = 2,testName="Do Logout",dependsOnMethods = "doLoginWithValidCradential")
	public void dologout() throws InterruptedException {
		lp=new LoginPageRepo(page);
		page.reload();
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
