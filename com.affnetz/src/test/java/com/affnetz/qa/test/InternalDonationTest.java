package com.affnetz.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.IOException;
import java.util.Random;

import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.HomePageRepo;
import com.qa.affnetz.InternalPages.InternalDonationRepo;
import com.qa.affnetz.InternalPages.PeerToPeerFundraisingRepo;
import com.qa.affnetz.InternalPages.SettingPageRepo;
import com.qa.affnetz.InternalPages.StakeHolderRepo;
import com.qa.affnetz.InternalPages.TributeDonationReportRepo;
import com.qa.affnetz.InternalPages.TributeRepo;
import com.qa.affnetz.Publicapages.PublicCampaignRepo;
import com.qa.affnetz.Publicapages.PublicTributeDonationRepo;

public class InternalDonationTest {
	Page page;
	HomePageRepo hp;
	InternalDonationRepo intDon;
	DashboardRepo db;
	StakeHolderRepo sp;
	SettingPageRepo settRepo;
	TributeRepo tr;
	TributeDonationReportRepo TributeDonationRepo;
	PeerToPeerFundraisingRepo pr;
	
	String fname,lname,mail,amt;
	String tributeName,TributeDonorFirstName,TributeDonorLastName,TributeDonorMail,tributeAmt;
	String campaignFirstName,campaignLastname,campaignmail,campaignAmt,CampaignName;
	String teamName,teamDonorFirstName,teamDonorLastName,teamDonorMailId,teamDonorAmt;
	Random rm=new Random();
	int x=rm.nextInt(9999);
	Random rm1=new Random();
	int y=rm1.nextInt(9999);
	Random rm2=new Random();
	int w=rm2.nextInt(9999);
	
	boolean screeing=PlayWrightFactory.screeningProcess_Donor();
	
	//-----------------------------------------------Direct Donation------------------------------------------------------------------//
	
	@Test(priority = 0 ,groups = {"DirectDonation","Donation","Smoke"})
	public void LoginAsASuperAdmin() throws IOException {
		page=PlayWrightFactory.intitBrowser("login");
		PlayWrightFactory.login();
	}
	
	@Test(priority = 1,groups = {"DirectDonation","Donation","Smoke"})
	public void clickDirectDonatteButton() {
		hp=new HomePageRepo(page);
		page.reload();
		hp.clickOnHome();
		hp.clickDonate();
	}
	
	@Test(priority = 2,groups = {"DirectDonation","Donation","Smoke"},dependsOnMethods = "clickDirectDonatteButton")
	public void fillAllDonorDetails() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		fname="Ashman"+x;
		lname="Derw"+x;
		mail="engineering+sep"+x+"@affnetz.com";
		String PhNo="7873530919";
		amt=""+x+"";
		intDon.setPesonalDetails(fname,lname, PhNo,mail);
		intDon.setDonationAmount(amt);
		intDon.setAddress();
	}
	@Test(priority = 3,groups = {"DirectDonation","Donation","Smoke"})
	public void setCardDetils() {
		intDon=new InternalDonationRepo(page);
		intDon.setCardDetails();
	}
	@Test(priority = 4,groups = {"DirectDonation","Donation","Smoke"})
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		intDon=new InternalDonationRepo(page);
		intDon.clickOnDonate();
	}
	@Test(priority = 5,groups = {"DirectDonation","Donation","Smoke"})
	public void isDonationDone() {
		intDon=new InternalDonationRepo(page);
		intDon.isFormSubmit();
	}
	@Test(priority = 6,groups = {"DirectDonation","Donation","Smoke"},dependsOnMethods = "isDonationDone")
	public void isReciptDownload() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
		page.goBack();
	}
	@Test(priority = 7,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isDonorDetailsStoreInDonorReport() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String donorName=fname+" "+lname;
		boolean flag=db.isDonorDetailsShownInDonorReport(donorName, mail, amt);
		assertTrue(flag);
		
	}
	@Test(priority =8 ,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isDonorShownInScreeningProcessForApproveTheUser() throws InterruptedException {
		if(screeing==true)
		{
			settRepo=new SettingPageRepo(page);
			db=new DashboardRepo(page);
			db.goToSettingPage();
			settRepo.gotToScreeningProcessPage();
			String name=fname+" "+lname;
			settRepo.isDonorDetailsShownInScreeningProcess(name, mail,"Donors");
		
		}else {
			assertFalse(false, "Screeing Process Is Off");
		}
	
	}
	//@Test(priority = 9,groups = {"DirectDonation","Donation"},dependsOnMethods = "isDonationDone")
	public void isNewDonorUserIsCreated() throws InterruptedException
	{
		hp=new HomePageRepo(page);
		hp.clickOnHome();
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sp=new StakeHolderRepo(page);
		Thread.sleep(2000);
		sp.searchUser(mail);
		String userName=fname+"  "+lname;
		sp.isUserCreated(userName);
		sp.clickOnSearchedUser();
		sp.verifyUserDetails("Donors",userName, mail);
	
		
	}
	
	//-----------------------------------------------Tribute Donation------------------------------------------------------------------//
	
	@Test(priority = 10,groups = {"Donation","Tribute","Smoke"})
	public void goToTributePage() throws IOException {
		try {
			hp=new HomePageRepo(page);
			hp.clickOnHome();
			db=new DashboardRepo(page);
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		}

	}
	
	@Test(priority = 11,groups = {"Donation","Tribute","Smoke"})
	public void clickOneDonate() {
		tr=new TributeRepo(page);
		tributeName=tr.getTributeName();
		tr.clickOnSearchedTribute();
		String tributeTitle=tr.getTributeName();
		assertEquals(tributeName, tributeTitle);
	}
	@Test(priority = 12,groups ={"Donation","Tribute","Smoke"})
	public void goToTributeDOnationPage() {
		tr=new TributeRepo(page);
		tr.goToDonationPage();
		String name=tr.getDonorNameOnDonationPage();
		assertEquals(tributeName, name);
	}
	@Test(priority = 13,groups = {"Donation","Tribute","Smoke"},dependsOnMethods = "goToTributeDOnationPage")
	public void setUserDetails() {
		tr=new TributeRepo(page);
		TributeDonorFirstName="Lacki"+y+"ui";
		TributeDonorLastName="Shym"+y+"ma";
		TributeDonorMail="engineering+septri"+y+"@affnetz.com";
		tributeAmt="50";
		tr.setPesonalDetails(TributeDonorFirstName, TributeDonorLastName, TributeDonorMail);
		tr.setDonationAmount(tributeAmt);
	}
	@Test(priority = 14,groups = {"Donation","Tribute","Smoke"})
	public void setUserAddress() throws InterruptedException {
		tr=new TributeRepo(page);
		tr.setAddress();
	}
	@Test(priority = 15,groups = {"Donation","Tribute","Smoke"})
	public void setCardDetails() {
		tr=new TributeRepo(page);
		tr.setCardDetails();
	}
	@Test(priority = 16,groups = {"Donation","Tribute","Smoke"})
	public void submitAllDetails_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		tr=new TributeRepo(page);
		tr.clickOnDonate();
	}
	@Test(priority = 17,groups = {"Donation","Tribute","Smoke"})
	public void isDonationDone_Tribute() {
		tr=new TributeRepo(page);
		tr.isFormSubmit();
	}
	@Test(priority = 18,groups = {"Donation","Tribute","Smoke"},dependsOnMethods = "isDonationDone_Tribute")
	public void isReciptDownload_Tribute() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
		page.goBack();
	}
	
	@Test(priority = 19,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailsStoredInTributeDonationReport() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.clickOnReport();
		Thread.sleep(1000);
		db.clickOnTributeDonationReport();
		TributeDonationRepo=new TributeDonationReportRepo(page);
		boolean flag=TributeDonationRepo.isTributeDonorDetailsShowing(TributeDonorFirstName+" "+TributeDonorLastName, tributeName);
		assertTrue(flag);
	}
	@Test(priority = 20,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailsShowInThatParticularTribute() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnTributeLink();
		tr=new TributeRepo(page);
		tr.searchTributeName(tributeName);
		
		assertEquals(tr.getTributeName(), tributeName);
		tr.clickOnSearchedTribute();
		tr.isDonorDetailsShowing(TributeDonorFirstName, TributeDonorMail, tributeAmt);
	}
	@Test(priority = 21,groups = {"Donation","Tribute"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorShownInScreeningProcessForApproveTheuser_Tribute() throws InterruptedException {
		if(screeing==true)
		{
			settRepo=new SettingPageRepo(page);
			db=new DashboardRepo(page);
			db.goToSettingPage();
			settRepo.gotToScreeningProcessPage();
			String name=TributeDonorFirstName+" "+TributeDonorLastName;
			settRepo.isDonorDetailsShownInScreeningProcess(name, TributeDonorMail,"Donors");
		}else {
			assertFalse(false, "Screeing Process is Pradhan");
		}

		
	}
	
	//-----------------------------------------------Campaign Donation------------------------------------------------------------------//
	
	@Test(priority = 22,groups = {"Campaign","Donation","Smoke"})
	public void goToCampaignPage() throws IOException {
		
		try {
			hp=new HomePageRepo(page);
			hp.clickOnHome();
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}

	}
	@Test(priority = 22,groups = {"Campaign","Donation","Smoke"},dependsOnMethods = "goToCampaignPage")
	public void clickOnCampaing() {
		pr=new PeerToPeerFundraisingRepo(page);
		CampaignName=pr.getCampaignName();
		pr.clickOnOneCampaign();
		String title=pr.getCampaignName();
		assertEquals(CampaignName, title);
		
	}
	@Test(priority = 23,groups = {"Campaign","Donation","Smoke"})
	public void goToCampaignDonationPage() {
		pr=new PeerToPeerFundraisingRepo(page);
		pr.goToDonationPage();
	}
	@Test(priority = 24,groups = {"Campaign","Donation","Smoke"})
	public void setuserDetails_Campaign() {
		intDon=new InternalDonationRepo(page);
		campaignFirstName="Julli"+w;
		campaignLastname="dwar"+w;
		campaignmail="engineering+julli"+w+"@affnetz.com";
		campaignAmt="36";
		intDon.setPesonalDetails(campaignFirstName, campaignLastname, "6371772552", campaignmail);
		intDon.setDonationAmount(campaignAmt);
	}
	@Test(priority = 25,groups = {"Campaign","Donation","Smoke"})
	public void setUserAddress_Campaign() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		intDon.setAddress();
	}
	@Test(priority = 26,groups = {"Campaign","Donation","Smoke"})
	public void setCardDetails_Campaing() {
		intDon=new InternalDonationRepo(page);
		intDon.setCardDetails();
	}
	@Test(priority = 27,groups = {"Campaign","Donation","Smoke"})
	public void isDonationDone_Campaing() {
		intDon=new InternalDonationRepo(page);
		intDon.clickOnDonate();
		intDon.isFormSubmit();
	}
	@Test(priority = 28,groups = {"Campaign","Donation","Smoke"})
	public void isReceiptDownload_Campaign() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
		page.goBack();
	}
	@Test(priority = 29,groups = {"Campaign","Donation"})
	public void isDonorDetailsisStoredInDonationreport_Campaign() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String donorName=campaignFirstName+" "+campaignLastname;
		boolean flag=db.isDonorDetailsShownInDonorReport(donorName, campaignmail, campaignAmt);
		assertTrue(flag);
	}
	@Test(priority = 30,groups = {"Campaign","Donation"})
	public void isDonorDetailsIsShowingInThatParticularCampaignDonationSection() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnPeerToPeerFundarasing();
		pr=new PeerToPeerFundraisingRepo(page);
		pr.searchCampaign(CampaignName);
		pr.clickOnSearchButton();
		String name=pr.getCampaignName();
		assertEquals(CampaignName, name);
		pr.clickOnSearchedCamp();
		pr.goToDonationsSection();
		pr.isDonorDetailsShowingInThisPartocularCampaing(campaignFirstName, campaignmail, campaignAmt);
	}
	@Test(priority = 31,groups = {"Campaign","Donation"})
	public void isDonorShownInScreeningProcessForApproveTheuser_Campaign() throws InterruptedException {
		if(screeing==true)
		{
			settRepo=new SettingPageRepo(page);
			db=new DashboardRepo(page);
			db.goToSettingPage();
			settRepo.gotToScreeningProcessPage();
			String name=campaignFirstName+" "+campaignLastname;
			settRepo.isDonorDetailsShownInScreeningProcess(name, campaignmail,"Donors");
			
		}else {
			assertFalse(false, "Screeing Process is Pradhan");
		}
	
		
	}
	
	//-----------------------------------------------Team Donation------------------------------------------------------------------//
	
	@Test(priority = 32,groups = {"Donation","Team","Smoke"})
	public void goToCampaignPage_Team() throws IOException {
		
		try {
			hp=new HomePageRepo(page);
			hp.clickOnHome();
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}

	}
	@Test(priority = 33,groups = {"Donation","Team","Smoke"})
	public void clickOnCampaing_Team() {
		pr=new PeerToPeerFundraisingRepo(page);
		CampaignName=pr.getCampaignName();
		pr.clickOnOneCampaign();
		String title=pr.getCampaignName();
		assertEquals(CampaignName, title);
		
	}
	
	@Test(priority = 34,groups = {"Donation","Team","Smoke"})
	public void goToCampaignTeam() {
		pr=new PeerToPeerFundraisingRepo(page);
		pr.goToTeamSection();
		teamName=pr.getTeamName();
		pr.clickOnTeam();
		String teamTitle=pr.getTeamTitle();
		assertEquals(teamName, teamTitle);
	}
	
	
	@Test(priority = 35,groups = {"Donation","Team","Smoke"})
	public void goToTeamDonationPage() {
		pr=new PeerToPeerFundraisingRepo(page);
		pr.teamDonate();
	}
	
	
	@Test(priority = 36,groups = {"Donation","Team","Smoke"})
	public void setuserDetails_Team() {
		intDon=new InternalDonationRepo(page);
		teamDonorFirstName="JulliTeam"+w;
		teamDonorLastName="dwarTeam"+w;
		teamDonorMailId="engineering+team"+w+"@affnetz.com";
		teamDonorAmt="36";
		intDon.setPesonalDetails(teamDonorFirstName, teamDonorLastName, "6371772552", teamDonorMailId);
		intDon.setDonationAmount(teamDonorAmt);
	}
	@Test(priority = 37,groups = {"Donation","Team","Smoke"})
	public void setUserAddress_Team() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		intDon.setAddress();
	}
	@Test(priority = 38,groups = {"Donation","Team","Smoke"})
	public void setCardDetails_Team() {
		intDon=new InternalDonationRepo(page);
		intDon.setCardDetails();
	}
	@Test(priority = 39,groups = {"Donation","Team","Smoke"})
	public void isDonationDone_Team() {
		intDon=new InternalDonationRepo(page);
		intDon.clickOnDonate();
		intDon.isFormSubmit();
	}
	@Test(priority = 40,groups = {"Donation","Team","Smoke"})
	public void isReceiptDownload_Team() throws InterruptedException {
		intDon=new InternalDonationRepo(page);
		boolean flag=intDon.downloadReceipt();
		assertTrue(flag);
		page.goBack();
	}
	@Test(priority = 41,groups = {"Donation","Team"})
	public void isDonorDetailsisStoredInDonationreport_Team() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String donorName=teamDonorFirstName+" "+teamDonorLastName;
		boolean flag=db.isDonorDetailsShownInDonorReport(donorName, teamDonorMailId, teamDonorAmt);
		assertTrue(flag);
	}
	@Test(priority = 42,groups = {"Donation","Team"})
	public void isDonorDetailsIsShowingInThatParticularCampaignTeamDonatiosSection() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnPeerToPeerFundarasing();
		pr=new PeerToPeerFundraisingRepo(page);
		pr.searchCampaign(CampaignName);
		pr.clickOnSearchButton();
		String campaignName=pr.getSearchedCampName();
		assertEquals(campaignName,CampaignName);
		pr.clickOnSearchedCamp();
		pr.goToTeamSection();
		pr.clickRightTeam(teamName);
		String title=pr.getTeamTitle();
		assertEquals(teamName, title);
		pr.goToDonationsSection();
		pr.isDonorDetailsShowingInThisPartocularCampaing(teamDonorFirstName, teamDonorMailId, teamDonorAmt);
		
	}
	@Test(priority = 43,groups = {"Donation","Team"})
	public void isDonorShownInScreeningProcessForApproveTheuser_Team() throws InterruptedException {
		if(screeing==true)
		{
			settRepo=new SettingPageRepo(page);
			db=new DashboardRepo(page);
			db.goToSettingPage();
			settRepo.gotToScreeningProcessPage();
			String name=teamDonorFirstName+" "+teamDonorLastName;
			settRepo.isDonorDetailsShownInScreeningProcess(name, teamDonorMailId,"Donors");	
		}else {
			assertFalse(false, "Screeing Process is Pradhan");
		}
	
	}
	
	
	
	
	

	
	
	

}
