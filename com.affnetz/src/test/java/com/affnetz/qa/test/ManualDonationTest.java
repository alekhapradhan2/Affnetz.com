package com.affnetz.qa.test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;

import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.EntitiesPageRepo;
import com.qa.affnetz.InternalPages.ManualDonationRepo;
import com.qa.affnetz.InternalPages.ManualTributeDonationRepo;
import com.qa.affnetz.InternalPages.PeerToPeerFundraisingRepo;
import com.qa.affnetz.InternalPages.StakeHolderRepo;
import com.qa.affnetz.InternalPages.TributeDonationReportRepo;
import com.qa.affnetz.InternalPages.TributeRepo;

public class ManualDonationTest {
	
	static Page page;
	DashboardRepo db;
	StakeHolderRepo sk;
	ManualDonationRepo md;
	ManualTributeDonationRepo mtdr;
	TributeRepo tr;
	TributeDonationReportRepo tdr;
	
	String userType,UserName,UserMail,campaignName,paymentMode,tributeName,TrpaymentMode;
	
	
	//------------------------------------Stake Holder Campaign---------------------------------------------------------------------//
	

	@Test(priority = 0,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void GoToStakeHolder() throws IOException
	{
		page=PlayWrightFactory.intitBrowser("login");
		PlayWrightFactory.login();
		db=new DashboardRepo(page);
		page.reload();
		db.goToStakeHolders();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
	}
	@Test(priority = 1,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"},dependsOnMethods ="GoToStakeHolder" )
	public void goToOneUserProfile() throws InterruptedException {
		sk=new StakeHolderRepo(page);
//		sk.chooseUserType();
		sk.clickOnSearchedUser();
		
		userType=sk.getUserType();
		UserName=sk.getUserName();
		UserMail=sk.getUserMail();	
		System.out.println(userType+" "+UserName+" "+UserMail);
	}
	
	@Test(priority = 2,groups ={"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void clickManualDonationButtonAndSelectOnrCampaign() throws InterruptedException
	{
		sk=new StakeHolderRepo(page);
		sk.clickOnManualDonationButton();
		sk.chooseCampaignAsModule();
		campaignName=sk.getCampaignNameselectOneCampaign();
		System.out.println(campaignName);
	}
	@Test(priority = 3,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void clickOnProceedButtonAndGotToManualDonationPage() {
		sk=new StakeHolderRepo(page);
		sk.clickOnProceedButton();
		md=new ManualDonationRepo(page);
		String campName=md.getCampaignName();
		System.out.println(campName);
		assertEquals(campName, campaignName);
		
	}
	
	@Test(priority = 4,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void setAllDonorDetailsAndAdress() throws InterruptedException {
		md=new ManualDonationRepo(page);

		String fName="Alekha";
		String lName="Pradhan";
		md.setPesonalDetails(fName,lName,UserMail);
		md.setDonationAmount("40");
		paymentMode=md.selectPaymentMode();
		md.transactionId();
		md.setAddress();
	}
	
	@Test(priority = 5,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void submitTheFromAndCheckDonoationisDoneOrNot() {
		md=new ManualDonationRepo(page);
		md.clickOnDonate();
		md.isFormSubmit();
	}
	@Test(priority = 6,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation","Smoke"})
	public void isReceiptDownload() throws InterruptedException {
		md=new ManualDonationRepo(page);
		boolean flag=md.downloadReceipt();
		page.goBack();
		assertTrue(flag);
		
	}
	
	@Test(priority = 7,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot")
	public void checkThatInThatUserProfileThisDonationIsShowingOrNot() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(UserMail);
		sk.clickOnSearchedUser();
		sk.goToThatDonation("stakeholder campaign");
	}
	@Test(priority = 8,groups = {"Stakeholder","Stakeholder Campaign","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot")
	public void isDonorDetaisIsCorrectltStored() {
		sk=new StakeHolderRepo(page);
		sk.verifyDonorDetails(UserName, paymentMode, UserMail, campaignName);
	}
	
	//------------------------------------Stake Holder Tribute---------------------------------------------------------------------//
	
	@Test(priority = 9,groups = {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void goToStakeHolderAndChooseOneUser_TRCammpaign() throws IOException, InterruptedException {
		try {
			db=new DashboardRepo(page);
			db.goToStakeHolders();
			sk=new StakeHolderRepo(page);
//			sk.chooseUserType();
			sk.clickOnSearchedUser();
			
			userType=sk.getUserType();
			UserName=sk.getUserName();
			UserMail=sk.getUserMail();	
			System.out.println(userType+" "+UserName+" "+UserMail);
			
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.goToStakeHolders();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
			sk=new StakeHolderRepo(page);
//			sk.chooseUserType();
			sk.clickOnSearchedUser();
			
			userType=sk.getUserType();
			UserName=sk.getUserName();
			UserMail=sk.getUserMail();	
			System.out.println(userType+" "+UserName+" "+UserMail);
		}
	}
	@Test(priority = 10,groups =  {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void clickManualDonationButtonAndSelectOneTribute() throws InterruptedException
	{
		sk=new StakeHolderRepo(page);
		sk.clickOnManualDonationButton();
		sk.chooseTributeAsModule();
		tributeName=sk.getTributeNameAndSelectOneTribute();
		System.out.println(tributeName);
	}
	@Test(priority = 11,groups =  {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void clickOnProceedButtonAndGotToManualDonationPage_SHtribute() {
		sk=new StakeHolderRepo(page);
		sk.clickOnProceedButton();
		md=new ManualDonationRepo(page);
		String triName=md.getCampaignName();
		System.out.println(triName);
		assertEquals(triName, tributeName);
		
	}
	@Test(priority = 12,groups = {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void setAllDonorDetailsAndAdress_SHtribute() throws InterruptedException {
		mtdr=new ManualTributeDonationRepo(page);
		String fName="Alekha";
		String lName="Pradhan";
		mtdr.setPesonalDetails(fName,lName,UserMail);
		mtdr.setDonationAmount("40");
		TrpaymentMode=mtdr.selectPaymentMode();
		mtdr.transactionId();
		mtdr.setAddress();
	}
	@Test(priority = 13,groups =  {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void submitTheFromAndCheckDonoationisDoneOrNot_SHtribut() {
		md=new ManualDonationRepo(page);
		md.clickOnDonate();
		md.isFormSubmit();
	}
	@Test(priority = 14,groups =  {"Stakeholder","Stakeholder Tribute","Manual Donation","Smoke"})
	public void isReceiptDownload_SHtribut() throws InterruptedException {
		md=new ManualDonationRepo(page);
		boolean flag=md.downloadReceipt();
		page.goBack();
		assertTrue(flag);
	}
	
	@Test(priority = 15,groups = {"Stakeholder","Stakeholder Tribute","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_SHtribut")
	public void checkThatInThatUserProfileThisDonationIsShowingOrNot_SHtribute() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(UserMail);
		sk.clickOnSearchedUser();
		Thread.sleep(2000);
		sk.clickOnTributeRadioButtion();
		sk.goToThatDonation("stakeholder tribute");
		sk.verifyDonorDetails(UserName, TrpaymentMode, UserMail,tributeName);
	}
	
	//------------------------------------Tribute Donation---------------------------------------------------------------------//
	@Test(priority = 16,groups = {"Tribute","Manual Donation","Smoke"})
	public void goToTributePage() throws IOException {
		try {
			db=new DashboardRepo(page);
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.clickOnTributeLink();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		}
	}
	@Test(priority = 17,groups =  {"Tribute","Manual Donation","Smoke"},dependsOnMethods = "goToTributePage")
	public void goToOneTribute()
	{
		tr=new TributeRepo(page);
		tributeName=tr.getTributeName();
		System.out.println(tributeName);
		tr.clickOnSearchedTribute();
		String name=tr.getTributeName();
		assertEquals(name, tributeName);	
	}
	@Test(priority = 18,groups =  {"Tribute","Manual Donation","Smoke"},dependsOnMethods = "goToOneTribute")
	public void clickOnManualDonationPage() {
		mtdr=new ManualTributeDonationRepo(page);
		tr=new TributeRepo(page);
		tr.clickOnManualDonationButton();
		String name=mtdr.getCampaignName();
		assertEquals(name, tributeName);
	}
	
	String tributeDonorFirstName,tributeDonorLastName,tributeDonorMail,tributeAmt;
	Random rm=new Random();
	int x=rm.nextInt(999);
	
	@Test(priority = 19,groups =  {"Tribute","Manual Donation","Smoke"})
	public void setTributeDonorAddress() throws InterruptedException {
		mtdr=new ManualTributeDonationRepo(page);
		tributeDonorFirstName="CheriManual"+x;
		tributeDonorLastName="Jackcy"+x;
		tributeDonorMail="engineering+chherim"+x+"@affnetz.com";
		mtdr.setPesonalDetails(tributeDonorFirstName,tributeDonorLastName,tributeDonorMail);
		Random am=new Random();
		int amt=am.nextInt(100);
		tributeAmt=""+amt+"";
		mtdr.setDonationAmount(tributeAmt);
		TrpaymentMode=mtdr.selectPaymentMode();
		mtdr.transactionId();
		mtdr.setAddress();
	}
	
	@Test(priority = 20,groups =  {"Tribute","Manual Donation","Smoke"})
	public void submitTheFromAndCheckDonoationisDoneOrNot_tribute() {
		md=new ManualDonationRepo(page);
		md.clickOnDonate();
		md.isFormSubmit();
	}
	
	@Test(priority = 22,groups =  {"Tribute","Manual Donation","Smoke"})
	public void isReceiptDownload_tribute() throws InterruptedException {
		md=new ManualDonationRepo(page);
		boolean flag=md.downloadReceipt();
		page.goBack();
		assertTrue(flag);
	}
	
	@Test(priority = 23,groups = {"Tribute","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_tribute")
	public void checkThatDonorDetailsIsStoreInTributeReportOrNor() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.clickOnReport();
		db.clickOnTributeDonationReport();
		tdr=new TributeDonationReportRepo(page);
		String name=tributeDonorFirstName+" "+tributeDonorLastName;
		
		tdr.isTributeDonorDetailsShowing(name, tributeName);	
	}
	@Test(priority = 24,groups = {"Tribute","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_tribute")
	public void checkThatDonorDeatilShowInThatParticularTributePage() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnTributeLink();
		tr=new TributeRepo(page);
		tr.searchTributeName(tributeName);
		tr.clickOnSearchedTribute();
		String name=tr.getTributeName();
		assertEquals(name, tributeName);
		tr.isDonorDetailsShowing(tributeDonorFirstName, tributeDonorMail, tributeAmt);
	}
	
	@Test(priority = 25,groups = {"Tribute","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_tribute")
	public void isThisTributeManualDonationIsShownInThatParticularDonorProfilePage() throws InterruptedException {
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(tributeDonorMail);
		sk.clickOnSearchedUser();
		sk.clickOnTributeRadioButtion();
		String name=tributeDonorFirstName+" "+tributeDonorLastName;
		sk.goToThatDonation("tribute");
		sk.verifyDonorDetails(name, TrpaymentMode, tributeDonorMail, tributeName);
	}
	
	//------------------------------------Campaign Donation---------------------------------------------------------------------//
	
	@Test(priority = 26,groups = {"Campaign","Manual Donation","Smoke"})
	public void goToCampaignPage() throws IOException {
		try {
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}
	}
	
	
	PeerToPeerFundraisingRepo pr;
	String campaignFirstName,campaignLastName,campaignMail,campaignAmt;
	Random camp=new Random();
	int y=camp.nextInt(999);
	
	@Test(priority = 27,groups = {"Campaign","Manual Donation","Smoke"},dependsOnMethods = "goToCampaignPage")
	public void goToOneCampaign() {
		pr=new PeerToPeerFundraisingRepo(page);
		campaignName=pr.getCampaignName();
		pr.clickOnSearchedCamp();
		String name=pr.getCampaignName();
		assertEquals(name, campaignName);
	}
	
	@Test(priority = 28,groups = {"Campaign","Manual Donation","Smoke"})
	public void clickOnManualDonationButton() {
		md=new ManualDonationRepo(page);
		pr=new PeerToPeerFundraisingRepo(page);
		pr.clickOnManualDonationButton();
		String name=md.getCampaignName();
		assertEquals(name, campaignName);
	}
	
	@Test(priority = 29,groups = {"Campaign","Manual Donation","Smoke"})
	public void setAllDonorDetails() throws InterruptedException {
		md=new ManualDonationRepo(page);
	
		campaignFirstName="Stephn"+y;
		campaignLastName="Dery"+y;
		campaignMail="engineering+stepman"+y+"@affnetz.com";
		campaignAmt=""+y+"";
		md.setPesonalDetails(campaignFirstName, campaignLastName, campaignMail);
		md.setDonationAmount(campaignAmt);
		paymentMode=md.selectPaymentMode();
		md.setAddress();
	}
	
	@Test(priority = 30,groups = {"Campaign","Manual Donation","Smoke"})
	public void submitTheFromAndCheckDonoationisDoneOrNot_campaign() {
		md=new ManualDonationRepo(page);
		md.clickOnDonate();
		md.isFormSubmit();
	}
	
	@Test(priority = 31,groups = {"Campaign","Manual Donation","Smoke"})
	public void isReceiptDownload_campaign() throws InterruptedException {
		md=new ManualDonationRepo(page);
		boolean flag=md.downloadReceipt();
		page.goBack();
		assertTrue(flag);
	}
	
	@Test(priority = 32,groups = {"Campaign","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_campaign")
	public void checkThisCampaignManualDonoraDetailsIsStoreInDonationReportOrNor() throws InterruptedException {
	
		
		db=new DashboardRepo(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String name=campaignFirstName+" "+campaignLastName;
		db.isDonorDetailsShownInDonorReport(name, campaignMail, campaignAmt);
	}
	
	@Test(priority = 33,groups = {"Campaign","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_campaign")
	public void checkThisCampaignManualDonorDetailsIsShowInThatParticularCampaignOrNot() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnPeerToPeerFundarasing();
		pr=new PeerToPeerFundraisingRepo(page);
		pr.searchCampaign(campaignName);
		pr.clickOnSearchedCamp();
		String name=pr.getCampaignName();
		assertEquals(name, campaignName);
		pr.goToDonationsSection();
		pr.isDonorDetailsShowingInThisPartocularCampaing(campaignFirstName, campaignMail, campaignAmt);
	}
	
	@Test(priority = 34,groups = {"Campaign","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_campaign")
	public void isThisCampaignManualDonationIsShownInThatParticularDonorProfilePage() throws InterruptedException {
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(campaignMail);
		sk.clickOnSearchedUser();
		String name=campaignFirstName+" "+campaignLastName;
		sk.goToThatDonation("campaign");
		sk.verifyDonorDetails(name, paymentMode, campaignMail, campaignName);
	}
	
	//---------------------------------------------Team Donation---------------------------------------------------------------------//
	
	@Test(priority = 35,groups = {"Team","Manual Donation","Smoke"})
	public void goToCampaignPage_Team() throws IOException {
		try {
			db=new DashboardRepo(page);
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.clickOnPeerToPeerFundarasing();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}
	}
	
	
	String teamName,teamDonorFirstName,teamDonorLastName,teamDonorMail,teamDonationAmt;
	Random team=new Random();
	int t=team.nextInt(999);
	
	
	@Test(priority = 36,groups = {"Team","Manual Donation","Smoke"})
	public void goToOneCampaignTeamSection() {
		pr=new PeerToPeerFundraisingRepo(page);
		campaignName=pr.getCampaignName();
		pr.clickOnOneCampaign();
		String name=pr.getCampaignName();
		assertEquals(campaignName, name);
		pr.goToTeamSection();
		teamName=pr.getTeamName();
		pr.clickOnTeam();
		String title=pr.getTeamTitle();
		assertEquals(title, teamName);
		
	}
	@Test(priority = 37,groups = {"Team","Manual Donation","Smoke"})
	public void clickOnTeamManulDonationButton() {
		pr=new PeerToPeerFundraisingRepo(page);
		md=new ManualDonationRepo(page);
		pr.clickOnManualDonationButton();
		String name=md.getCampaignName();
		assertEquals(name, teamName);
	}
	
	
	@Test(priority = 38,groups = {"Team","Manual Donation","Smoke"})
	public void setAllDonorDetails_Team() throws InterruptedException {
		md=new ManualDonationRepo(page);
	
		teamDonorFirstName="Stephn"+t+"team";
		teamDonorLastName="Dery"+t+"team";
		teamDonorMail="engineeringt+stepteam"+t+"@affnetz.com";
		teamDonationAmt=""+t+"";
		md.setPesonalDetails(teamDonorFirstName, teamDonorLastName, teamDonorMail);
		md.setDonationAmount(teamDonationAmt);
		paymentMode=md.selectPaymentMode();
		md.setAddress();
	}
	
	@Test(priority = 39,groups = {"Team","Manual Donation","Smoke"})
	public void submitTheFromAndCheckDonoationisDoneOrNot_Team() {
		md=new ManualDonationRepo(page);
		md.clickOnDonate();
		md.isFormSubmit();
	}
	
	@Test(priority = 40,groups = {"Team","Manual Donation","Smoke"})
	public void isReceiptDownload_Team() throws InterruptedException {
		md=new ManualDonationRepo(page);
		boolean flag=md.downloadReceipt();
		page.goBack();
		assertTrue(flag);
	}
	
	@Test(priority = 41,groups = {"Team","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_Team")
	public void checkThisTeamManualDonoraDetailsIsStoreInDonationReportOrNor() throws InterruptedException {
		
		
		db=new DashboardRepo(page);
		db.goToDashBoard();
		db.clickOnDonorReportLink();
		String name=teamDonorFirstName+" "+teamDonorLastName;
		db.isDonorDetailsShownInDonorReport(name, teamDonorMail,teamDonationAmt);
	}
	@Test(priority = 42,groups = {"Team","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_Team")
	public void checkThisTeamManualDonorDetailsIsShowInThatParticularCampaignAndTeamOrNot() throws InterruptedException {
		db=new DashboardRepo(page);
		db.clickOnPeerToPeerFundarasing();
		pr=new PeerToPeerFundraisingRepo(page);
		pr.searchCampaign(campaignName);
		pr.clickOnSearchedCamp();
		String name=pr.getCampaignName();
		assertEquals(name, campaignName);
		pr.goToTeamSection();
		pr.clickRightTeam(teamName);
		String title=pr.getTeamTitle();
		assertEquals(title, teamName);
		pr.goToDonationsSection();
		pr.isDonorDetailsShowingInThisPartocularCampaing(teamDonorFirstName, teamDonorMail, teamDonationAmt);
	}
	@Test(priority = 43,groups = {"Team","Manual Donation"},dependsOnMethods = "submitTheFromAndCheckDonoationisDoneOrNot_Team")
	public void isThisTeamManualDonationIsShownInThatParticularDonorProfilePage() throws InterruptedException {
		
		db=new DashboardRepo(page);
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(teamDonorMail);
		sk.clickOnSearchedUser();
		String name=teamDonorFirstName+" "+teamDonorLastName;
		sk.goToThatDonation("campaign");
		sk.verifyDonorDetails(name, paymentMode, teamDonorMail, campaignName);
	}
	
	
	//---------------------------------------------Entiti Donation---------------------------------------------------------------------//
	//@Test(priority = 44,groups = {"Entiti","Manual Donation"})
	public void goToEntityPage() throws IOException
	{
		try {
			db=new DashboardRepo(page);
			db.goToEntityPage();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("entitiUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.goToEntityPage();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("entitiUrl"));
		}
	}
	
	String entitiName;
	int entitiDonationAmount;
	EntitiesPageRepo ep;
	
	//@Test(priority = 45,groups = {"Entiti","Manual Donation"})
	public void ClickOnOneEntiti()
	{
		ep=new EntitiesPageRepo(page);
		entitiName=ep.getEntitiName();
		System.out.println(entitiName);
		ep.clickOneEntiti();
		String headline=ep.getEntitiHeadline();
		System.out.println(headline);
		assertEquals(headline, entitiName);
	}
//	@Test(priority = 45,groups = {"Entiti","Manual Donation"})
	public void doOneEntitiManualDonation() throws InterruptedException {
		ep=new EntitiesPageRepo(page);
		ep.clickOnManualDonationButton();
		
		entitiDonationAmount=ep.setDonationAmount(); 
		ep.selectPaymentMode();
		ep.setDateAndTime("20");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
