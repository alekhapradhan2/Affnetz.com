package com.affnetz.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.BaseT1;
import com.affnetz.qa.factory.Listener_T1;
import com.affnetz.qa.factory.PlayWrightFactory_T1;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.affnetz.pages.DashboardRepo_T1;
import com.qa.affnetz.pages.LoginPageRepo_T1;
import com.qa.affnetz.pages.PublicDonationRepo_T1;
import com.qa.affnetz.pages.PublicTributeDonationRepo_T1;
import com.qa.affnetz.pages.PublicTributeRepo_T1;
import com.qa.affnetz.pages.TributeDonationReportRepo_T1;

public class PublicDonationTest_T1 {
	
	Page page;
	PublicDonationRepo_T1 pd;
	LoginPageRepo_T1 lp;
	DashboardRepo_T1 dr;
	PublicTributeRepo_T1 pr;
	PublicTributeDonationRepo_T1 pdr;
	TributeDonationReportRepo_T1 TributeDonationRepo;
	ITestResult result;
	
	
	String fname,lname,ph,mailid,tributeName;
	Random rm=new Random();
	int x=rm.nextInt(999);
	
		
	/**Public donation with direct link */
	
	@Test(priority =0,groups = {"DirectDonation","Donation"})
	public void launchBrowser() {
		page=PlayWrightFactory_T1.intitBrowser("chromium", "donate");
	}
	
	@Test(priority = 1,groups = {"DirectDonation","Donation"})
	public void isDonationPage()
	{
		assertThat(page).hasURL("https://t1.affnetz.org/donate");
	}
	
	/**All the donor details fill */
	
	@Test(priority = 2,dependsOnMethods = "isDonationPage",groups = {"DirectDonation","Donation"})
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
	
	/** card details fill */
	
	@Test(priority = 3,dependsOnMethods = "fillAllDonorDetails",groups = {"DirectDonation","Donation"})
	public void setCardDetils() {
		pd=new PublicDonationRepo_T1(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	/**Click on donate button */
	
	@Test(priority = 4,dependsOnMethods = "setCardDetils",groups = {"DirectDonation","Donation"})
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_T1(page);
		pd.clickOnDonate();
	}
	
	/**It will check is donation is done or no */
	@Test(priority = 5,dependsOnMethods = "submitAllDetails",groups = {"DirectDonation","Donation"})
	public void isDonationDone() {
		pd=new PublicDonationRepo_T1(page);
		pd.isFormSubmit();
	}
	
	
	/**It will check user able to download the receipt after donation or not */
	
	@Test(priority = 6,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isReciptDownload() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo_T1(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	
	/**It will check all the donor details is showing to super admin in montly donor list or not */
	
	@Test(priority = 7,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isSuperAdminSeeTheDonorDetails() throws InterruptedException {
		
		page=PlayWrightFactory_T1.intitBrowser("chromium", "login");
		lp=new LoginPageRepo_T1(page);
		lp.doLogin("t1admin", "%^&$T1Affnetz#$");
		dr=new DashboardRepo_T1(page);
		dr.clickOnMonthDonorLink();
		dr.searchDonorDetaills(fname+" "+lname);
		boolean flag=dr.isDonorDetailsShowing(fname+" "+lname);
		
		assertTrue(flag);
		
	}
	
	
	@Test(priority = 8,groups = {"Tribute","Donation"})
	public void goToPublicTributePage() {
		page=PlayWrightFactory_T1.intitBrowser("chromium","login");
//		page.navigate("https://t1.affnetz.org/login");
		lp=new LoginPageRepo_T1(page);
		lp.clickTribute();
		assertThat(page).hasURL("https://t1.affnetz.org/tribute");
	}
	
	
	@Test(priority = 9,groups = {"Tribute","Donation"})
	public void clickOneTribute() {
		pr=new PublicTributeRepo_T1(page);
		tributeName=pr.getTributeName();
		pr.clickOnTribute();
		
		String title=pr.getTributeTitile();
		assertEquals(tributeName, title);
		
	}
	
	
	@Test(priority = 10,groups = {"Tribute","Donation"})
	public void clickOnDonateButton() {
		pr= new PublicTributeRepo_T1(page);
		pr.clickOnTributeDonateButton();
		String name=pr.getTributeNameInDonationForm();
		assertEquals(tributeName, name);
	}
	
	
	@Test(priority = 11,groups = {"Tribute","Donation"})
	public void fillAllDonorDetails_Tribute()
	{
		pdr=new PublicTributeDonationRepo_T1(page);
		fname="Meth"+x+"ew";
		lname="Jac"+x+"k";
		ph="6371772552";
		mailid="engineering+ap"+x+"@affnetz.com";
		pdr.setPesonalDetails(fname, lname, ph, mailid);
		pdr.setDonationAmount("45");
		pdr.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	
	@Test(priority = 12,groups = {"Tribute","Donation"})
	public void setCardDetils_Tribute() {
		pdr=new PublicTributeDonationRepo_T1(page);
		pdr.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	@Test(priority = 13,groups = {"Tribute","Donation"})
	public void submitAllDetails_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pdr= new PublicTributeDonationRepo_T1(page);
		pdr.clickOnDonate();
	}
	
	
	@Test(priority = 14,groups = {"Tribute","Donation"},dependsOnMethods = "submitAllDetails_Tribute")
	public void isDonationDone_Tribute() {
		pdr=new PublicTributeDonationRepo_T1(page);
		pdr.isFormSubmit();
	}
	
	
	@Test(priority = 15,groups = {"Tribute","Donation"},dependsOnMethods ="isDonationDone_Tribute" )
	public void isReciptDownload_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pdr=new PublicTributeDonationRepo_T1(page);
		boolean flag=pdr.downloadReceipt();
		assertTrue(flag);
	}
	
	@Test(priority = 16,groups = {"Tribute","Donation"})
	
	public void isPublicTributeDonorDetaiShowToSuperAdmin() throws InterruptedException {
		page=PlayWrightFactory_T1.intitBrowser("chromium", "login");
		lp=new LoginPageRepo_T1(page);
		lp.doLogin("t1admin", "%^&$T1Affnetz#$");
		
		dr=new DashboardRepo_T1(page);
		dr.clickOnReport();
		dr.clickOnTributeDonationReport();
		TributeDonationRepo=new TributeDonationReportRepo_T1(page);
		boolean flag=TributeDonationRepo.isTributeDonorDetailsShowing(fname+" "+lname, tributeName);
		assertTrue(flag);
		
		
		
	}
	
	
	
	
	
	

}
