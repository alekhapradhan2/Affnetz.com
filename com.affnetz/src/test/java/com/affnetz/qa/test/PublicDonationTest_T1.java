package com.affnetz.qa.test;

import static org.testng.Assert.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Random;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory_T1;
import com.microsoft.playwright.Page;
import com.qa.affnetz.pages.DashboardRepo_T1;
import com.qa.affnetz.pages.LoginPageRepo_T1;
import com.qa.affnetz.pages.PublicDonationRepo_T1;

public class PublicDonationTest_T1 {
	
	Page page;
	PublicDonationRepo_T1 pd;
	LoginPageRepo_T1 lp;
	DashboardRepo_T1 dr;
	
	String fname,lname,ph,mailid;
	Random rm=new Random();
	int x=rm.nextInt(999);
	
	@BeforeTest
	public void setUp()
	{
		page=PlayWrightFactory_T1.intitBrowser("chromium","donate");
	}
	
	@Test(priority = 0)
	public void isDonationPage()
	{
		assertThat(page).hasURL("https://t1.affnetz.org/donate");
	}
	@Test(priority = 1,dependsOnMethods = "isDonationPage")
	public void fillAllDonorDetails() {
		pd=new PublicDonationRepo_T1(page);
		 fname="Meth"+x+"ew";
		 lname="Jac"+x+"k";
		 ph="6371772552";
		 mailid="engineering+ap"+x+"@affnetz.com";
		pd.setPesonalDetails(fname, lname, ph, mailid);
		pd.setDonationAmount("45");
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	@Test(priority = 2,dependsOnMethods = "fillAllDonorDetails")
	public void setCardDetils() {
		pd=new PublicDonationRepo_T1(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	@Test(priority = 3,dependsOnMethods = "setCardDetils")
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_T1(page);
		pd.clickOnDonate();
	}
	
	@Test(priority = 4,dependsOnMethods = "submitAllDetails")
	public void isDonationDone() {
		pd=new PublicDonationRepo_T1(page);
		pd.isFormSubmit();
	}
	
	@Test(priority = 5,dependsOnMethods = "isDonationDone")
	public void isReciptDownload() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_T1(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	@Test(priority = 6,dependsOnMethods = "isDonationDone")
	public void isSuperAdminSeeTheDonorDetails() throws InterruptedException {
		
		page.navigate("https://t1.affnetz.org/login");
		lp=new LoginPageRepo_T1(page);
		lp.doLogin("t1admin", "%^&$T1Affnetz#$");
		dr=new DashboardRepo_T1(page);
		dr.clickOnMonthDonorLink();
		dr.searchDonorDetaills(fname+" "+lname);
		boolean flag=dr.isDonorDetailsShowing(fname+" "+lname);
		assertTrue(flag);	
	}
	
	

}
