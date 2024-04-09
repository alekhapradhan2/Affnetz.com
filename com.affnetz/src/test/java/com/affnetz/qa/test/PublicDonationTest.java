package com.affnetz.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.io.IOException;
import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.PeerToPeerFundraisingRepo;
import com.qa.affnetz.InternalPages.SettingPageRepo;
import com.qa.affnetz.InternalPages.TributeDonationReportRepo;
import com.qa.affnetz.InternalPages.TributeRepo;
import com.qa.affnetz.Publicapages.LoginPageRepo;
import com.qa.affnetz.Publicapages.PublicCampaignRepo;
import com.qa.affnetz.Publicapages.PublicDonationRepo;
import com.qa.affnetz.Publicapages.PublicTributeDonationRepo;
import com.qa.affnetz.Publicapages.PublicTributeRepo;


/**Direct Donation Test No - 0 to 7
 * Tribute Donation Test No -8 to 17
 * Campaign Donation Test no -18 to 27 
 * Team Donation Test No- 28 to 38*/
public class PublicDonationTest {
	
	static Page page;
	PublicDonationRepo pd;
	LoginPageRepo lp;
	DashboardRepo dr;
	PublicTributeRepo pr;
	PublicTributeDonationRepo pdr;
	TributeDonationReportRepo TributeDonationRepo;
	ITestResult result;
	TributeRepo tribute;
	PublicCampaignRepo pc;
	PeerToPeerFundraisingRepo fund;
	SettingPageRepo settRepo;

	
	//-----------------------------------------DIRECT DONATION---------------------------------------------------------//
	
	/**Public donation with direct link 
	 * @throws IOException */
	
	
	@Test(priority =0,groups = {"DirectDonation","Donation"})
	public void launchBrowser() throws IOException {
		page=PlayWrightFactory.intitBrowser("donate");
	}
	
	
	@Test(priority = 1,groups = {"DirectDonation","Donation"})
	public void isDonationPage() throws IOException
	{
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("donateUrl"));
	}
	
	
	/**All the donor details fill 
	 * @throws InterruptedException */
	
	
	String fname,lname,ph,amount,mailid;
	Random rm=new Random();
	int x=rm.nextInt(999);
	@Test(priority = 2,dependsOnMethods = "isDonationPage",groups = {"DirectDonation","Donation"})
	public void fillAllDonorDetails() throws InterruptedException {
		pd=new PublicDonationRepo(page);
		fname="Meth"+x+"ew";
		lname="Jac"+x+"k";
		ph="6371772552";
		amount=""+x+"";
		mailid="engineering+ap"+x+"@affnetz.com";
		pd.setPesonalDetails(fname, lname, ph, mailid);
		pd.setDonationAmount(amount);
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	
	
	/** card details fill */
	
	@Test(priority = 3,dependsOnMethods = "fillAllDonorDetails",groups = {"DirectDonation","Donation"})
	public void setCardDetils() {
		pd=new PublicDonationRepo(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	/**Click on donate button */
	
	@Test(priority = 4,dependsOnMethods = "setCardDetils",groups = {"DirectDonation","Donation"})
	public void submitAllDetails() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		pd.clickOnDonate();
	}
	
	
	/**It will check is donation is done or not */
	@Test(priority = 5,dependsOnMethods = "submitAllDetails",groups = {"DirectDonation","Donation"})
	public void isDonationDone() {
		pd=new PublicDonationRepo(page);
		pd.isFormSubmit();
	}
	
	
	/**It will check user able to download the receipt after donation or not
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped */
	
	@Test(priority = 6,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isReciptDownload() throws InterruptedException {
		pd=new PublicDonationRepo(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
				
	/**It will login as a super admin and check all the donor details is showing to super admin in montly donor list or not
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped 
	 * @throws IOException */
	
	@Test(priority = 7,dependsOnMethods = "isDonationDone",groups = {"DirectDonation","Donation"})
	public void isSuperAdminSeeTheDonorDetails() throws InterruptedException, IOException {
		page.goBack();
		lp=new LoginPageRepo(page);
		lp.goToLoginPage();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("loginUrl"));
		PlayWrightFactory.login();
		dr=new DashboardRepo(page);
		dr.clickOnMonthDonorLink();
		dr.searchDonorDetaills(fname+" "+lname);
		dr.isDonorDetailsShowing(fname+" "+lname);
		
	}
	
	@Test(priority = 8,groups = {"DirectDonation","Donation"})
	public void isDonorShownInScreeningProcessForApproveTheuser() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		dr=new DashboardRepo(page);
		dr.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=fname+" "+lname;
		settRepo.isDonorDetailsShownInScreeningProcess(name, mailid,"Donors");

	}
	
	//--------------------------------------------TRIBUTE DONATION----------------------------------------------------------------
	
	
	
	/** This method will open the public tribute page 
	 * and also validate the tribute page opened or not
	 * @throws IOException */
	
	@Test(priority = 9,groups = {"Tribute","Donation"})
	public void goToPublicTributePage() throws IOException {
		try {
			lp=new LoginPageRepo(page);
			lp.doLogout();
			lp.clickTribute();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
//			page.navigate("https://t1.affnetz.org/login");
			lp=new LoginPageRepo(page);
			lp.clickTribute();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("tributeUrl"));
		}
		

	}
	
	/** This method will click on one public tribute
	 *  and validate the name is marching or not*/
	
	String tributeName,TributeDonorFisrtName,TributeDonorLastName,TributeDonorMail,tributeAmt;
	Random rm1=new Random();
	int y=rm1.nextInt(999);
	@Test(priority = 10,groups = {"Tribute","Donation"})
	public void clickOneTribute() {
		pr=new PublicTributeRepo(page);
		tributeName=pr.getTributeName();
		pr.clickOnTribute();
		
		String title=pr.getTributeTitile();
		assertEquals(tributeName, title);
		
	}
	
	
	
	/** This method will click on donate button in selected tribute and 
	 * check the tribute name is matching with the 
	 * name showing in donation page or not */
	
	@Test(priority = 11,groups = {"Tribute","Donation"})
	public void clickOnDonateButton() {
		pr= new PublicTributeRepo(page);
		pr.clickOnTributeDonateButton();
		String name=pr.getTributeNameInDonationForm();
		assertEquals(tributeName, name);
	}
	
	
	
	/** This method will fill the all donor details 
	 * @throws InterruptedException */
	
	@Test(priority = 12,groups = {"Tribute","Donation"})
	public void fillAllDonorDetails_Tribute() throws InterruptedException
	{
		pdr=new PublicTributeDonationRepo(page);
		TributeDonorFisrtName="Nami"+y+"ew";
		TributeDonorLastName="Hol"+y+"k";
		ph="6371772552";
		tributeAmt=""+y+"";
		TributeDonorMail="engineering+ap"+y+"@affnetz.com";
		pdr.setPesonalDetails(TributeDonorFisrtName,TributeDonorLastName, ph, TributeDonorMail);
		pdr.setDonationAmount(tributeAmt);
		pdr.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	
	
	/** This method will fill the card details of donor */
	@Test(priority = 13,groups = {"Tribute","Donation"})
	public void setCardDetils_Tribute() {
		pdr=new PublicTributeDonationRepo(page);
		pdr.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	/** This method will click on donateButton on donation form and submit the details */
	@Test(priority = 14,groups = {"Tribute","Donation"})
	public void submitAllDetails_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pdr= new PublicTributeDonationRepo(page);
		pdr.clickOnDonate();
	}
	
	
	/** This method will check is donation form is submitted or not */
	@Test(priority = 15,groups = {"Tribute","Donation"},dependsOnMethods = "submitAllDetails_Tribute")
	public void isDonationDone_Tribute() {
		pdr=new PublicTributeDonationRepo(page);
		pdr.isFormSubmit();
	}
	
	
	/** This method will check is donation receipt is downloaded or not.
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped*/
	
	@Test(priority = 16,groups = {"Tribute","Donation"},dependsOnMethods ="isDonationDone_Tribute" )
	public void isReciptDownload_Tribute() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	
	/** This method will check after donation all the donor details should shown to super admin in tribute donation report.
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped 
	 * @throws IOException */
	
	@Test(priority = 17,groups = {"Tribute","Donation"},dependsOnMethods = "isDonationDone_Tribute")
	public void isPublicTributeDonorDetaiShowToSuperAdmin() throws InterruptedException, IOException {
		page.goBack();
		lp=new LoginPageRepo(page);
		lp.goToLoginPage();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("loginUrl"));
		PlayWrightFactory.login();
		dr=new DashboardRepo(page);
		dr.clickOnReport();
		Thread.sleep(1000);
		dr.clickOnTributeDonationReport();
		TributeDonationRepo=new TributeDonationReportRepo(page);
		boolean flag=TributeDonationRepo.isTributeDonorDetailsShowing(TributeDonorFisrtName+" "+TributeDonorLastName, tributeName);
		assertTrue(flag);
	}
	
	
	
	/** This method will login as a super admin and check after donation all the donor details
	 *  should store in that particular tribute in donation section.
	 * This method will only execute if the donation is done if donation got failed then this method will be skipped */
	@Test(priority = 18,groups = {"Tribute","Donation"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorDetailShowInThatParticularTributeDonationSection() throws InterruptedException {
		dr=new DashboardRepo(page);
		dr.clickOnTributeLink();
		assertThat(page).hasURL("https://t1.affnetz.org/tribute");
		tribute=new TributeRepo(page);
		tribute.searchTributeName(tributeName);
		assertEquals(tribute.getTributeName(), tributeName);
		tribute.clickOnSearchedTribute();
		tribute.isDonorDetailsShowing(TributeDonorFisrtName, TributeDonorMail, tributeAmt);
	}
	@Test(priority = 19,groups = {"Tribute","Donation"},dependsOnMethods = "isDonationDone_Tribute")
	public void isDonorShownInScreeningProcessForApproveTheuser_Tribute() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		dr=new DashboardRepo(page);
		dr.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=TributeDonorFisrtName+" "+TributeDonorLastName;
		settRepo.isDonorDetailsShownInScreeningProcess(name, TributeDonorMail,"Donors");
;
	}

	//-------------------------------------------CAMPAIGN DONATION---------------------------------------------------------//
	
	/** This method will open the public campaign page 
	 * and also validate the campaign page opened or not
	 * @throws IOException */
	
	@Test(priority = 20,groups = {"Campaign","Donation"})
	public void goToPublicCampaignPage() throws IOException {
		
		try {
			lp=new LoginPageRepo(page);
			lp.doLogout();
			lp.clickfundRaising();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			lp=new LoginPageRepo(page);
			lp.clickfundRaising();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}
		}
		
	
	
	String campName,campFirstName,campLastName,CampAmt,CampMail;
	Random rm2=new Random();
	int w=rm2.nextInt(999);
	/** This method will click on one public campaign
	 *  and validate the name is marching or not
	 * @throws InterruptedException */
	
	@Test(priority = 21,groups = {"Campaign","Donation"})
	public void clickOnCampaign() throws InterruptedException {
		pc=new PublicCampaignRepo(page);
		campName=pc.getCampaignName();
		pc.searchCampaign(campName);
		Thread.sleep(2000);
		pc.clickOnCampaign();
		String title=pc.getCampaignTitle();
		assertEquals(campName, title);
	}
	
	
	
	/** This method will click on donate button in selected campaign*/
	
	@Test(priority = 22,groups = {"Campaign","Donation"})
	public void clickOnCampDonateButton() {
		pc=new PublicCampaignRepo(page);
		pc.clickOnCampDonate();
	}

	
	
	/** This method will fill the card details of donor */
	
	@Test(priority = 23,groups = {"Campaign","Donation"},dependsOnMethods = "clickOnCampDonateButton")
	public void fillAllDonorDetails_Campaign() throws InterruptedException {
		pd=new PublicDonationRepo(page);
		campFirstName="Andr"+w+"ew";
		campLastName="Cheri"+w+"k";
		ph="6371772552";
		CampAmt=""+w+"";
		CampMail="engineering+cp"+w+"@affnetz.com";
		pd.setPesonalDetails(campFirstName, campLastName, ph, CampMail);
		pd.setDonationAmount(CampAmt);
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	
	
	/** This method will fill the card details of donor */
	
	@Test(priority = 24,groups = {"Campaign","Donation"})
	public void setCardDetils_Campaign() {
		pd=new PublicDonationRepo(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	
	/** This method will click on donateButton on 
	 * donation form and submit the details */
	
	@Test(priority = 25,groups = {"Campaign","Donation"})
	public void submitAllDetails_Campaign() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		pd.clickOnDonate();
	}
	
	
	
	/** This method will check is donation form is submitted not */
	
	@Test(priority = 26,groups = {"Campaign","Donation"})
	public void isDonationDone_Campaign() {
		pd=new PublicDonationRepo(page);
		pd.isFormSubmit();
	}
	
	
	/** This method will check is receipt is download or not after donation */
	
	@Test(priority = 27,groups = {"Campaign","Donation"},dependsOnMethods = "isDonationDone_Campaign")
	public void isReciptDownload_Campaign() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	
	
	/** This method will login as a super admin and check is donor details is stored in donorReport or not
	 * and super admin will able to show the donor details or not  */
	
	@Test(priority = 28,groups = {"Campaign","Donation"},dependsOnMethods = "isDonationDone_Campaign")
	public void isPublicCampaignDonorDetailsIsStoredInDonorReport() throws InterruptedException, IOException {
		
		page.goBack();
		lp=new LoginPageRepo(page);
		lp.goToLoginPage();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("loginUrl"));
		PlayWrightFactory.login();
		dr=new DashboardRepo(page);
		dr.clickOnDonorReportLink();
		String DonorName=campFirstName+" "+campLastName;
		boolean flag=dr.isDonorDetailsShownInDonorReport(DonorName, CampMail, CampAmt);
		assertTrue(flag);
		
	}
	
	
	
	/** This method will login as a super admin and check is donor details is stored in that particular campaign donation section or not
	 * and donor details will show in that campaign donation section */
	
	
	@Test(priority = 29,groups = {"Campaign","Donation"},dependsOnMethods = "isDonationDone_Campaign")
	public void isDonorDetailsIsShowingInThatParticularCampaignDonatiosSection() throws InterruptedException {
		dr=new DashboardRepo(page);
		dr.clickOnPeerToPeerFundarasing();
		fund=new PeerToPeerFundraisingRepo(page);
		fund.searchCampaign(campName);
		fund.clickOnSearchButton();
		String campaignName=fund.getSearchedCampName();
		assertEquals(campaignName,campName);
		fund.clickOnSearchedCamp();
		fund.goToDonationsSection();
		fund.isDonorDetailsShowingInThisPartocularCampaing(campFirstName, CampMail, CampAmt);
		
	}
	
	@Test(priority = 30,groups = {"Campaign","Donation"},dependsOnMethods = "isDonationDone_Campaign")
	public void isDonorShownInScreeningProcessForApproveTheuser_Campaign() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		dr=new DashboardRepo(page);
		dr.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=campFirstName+" "+campLastName;
		settRepo.isDonorDetailsShownInScreeningProcess(name, CampMail,"Donors");
	}

	
//------------------------------------------------TEAM DONATION------------------------------------------------------------------//	
	String teamName,teamFirstName,teamLastName,teamAmt,teamMail;
	Random rm3=new Random();
	int z=rm3.nextInt(999); 
	/** This method will open the public campaign page 
	 * and also validate the campaign page opened or not
	 * @throws IOException */
	
	@Test(priority = 31,groups = {"Campaign","Donation","Team"})
	public void testPublicTeamCampaignDonation() throws IOException {
		try {
			
			lp=new LoginPageRepo(page);
			lp.doLogout();
			lp.clickfundRaising();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			lp=new LoginPageRepo(page);
			lp.clickfundRaising();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("campaignUrl"));
		}
		
		
	}
	
	
	
	/** This method will click on one public campaign
	 *  and validate the name is matching or not*/
	
	@Test(priority = 32,groups = {"Campaign","Donation","Team"})
	public void goToPublicCampaign() {
		pc=new PublicCampaignRepo(page);
		campName=pc.getCampaignName();
		pc.clickOnCampaign();
		String title=pc.getCampaignTitle();
		assertEquals(campName, title);
		
	}
	
	
	
	/** This method will click on one team
	 *  and validate the name is marching or not*/
	
	@Test(priority = 33,groups = {"Campaign","Donation","Team"})
	public void goToCampaignTeam() {
		pc=new PublicCampaignRepo(page);
		pc.goToTeamSection();
		teamName=pc.getTeamName();
		pc.clickOnTeam();
		String teamTitle=pc.getTeamTitle();
		assertEquals(teamName, teamTitle);
	}
	
	
	
	/** This Method will click on team donation button*/
	
	@Test(priority = 34,groups = {"Campaign","Donation","Team"})
	public void goToTeamDonationPage() {
		pc=new PublicCampaignRepo(page);
		pc.teamDonate();
	}
	
	
	
	/** This method will fill the all donor details*/
	
	@Test(priority = 35,groups = {"Campaign","Donation","Team"})
	public void fillAllDonorDetails_Team() throws InterruptedException {
		pd=new PublicDonationRepo(page);
		teamFirstName="asteam"+w+"ew";
		teamLastName="Chteam"+w+"k";
		ph="6371772552";
		teamAmt=""+w+"";
		teamMail="engineering+team"+w+"@affnetz.com";
		pd.setPesonalDetails(teamFirstName, teamLastName, ph, teamMail);
		pd.setDonationAmount(teamAmt);
		pd.setAddress("newadd1", "newadd2", "newCity", "Alaska", "88888");
	}
	
	
	
	/** This method will fill the card details*/
	
	@Test(priority = 36,groups = {"Campaign","Donation","Team"})
	public void setCardDetils_Team() {
		pd=new PublicDonationRepo(page);
		pd.setCardDetails("4242424242424242", "4242","424","88888");
	}
	
	
	
	/** This method will click on donateButton on 
	 * donation form and submit the details */
	
	@Test(priority = 37,groups = {"Campaign","Donation","Team"})
	public void submitAllDetails_Team() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		pd.clickOnDonate();
	}
	
	
	/** This method will check is donation form is submitted not */
	
	@Test(priority = 38,groups = {"Campaign","Donation","Team"})
	public void isDonationDone_Team() {
		pd=new PublicDonationRepo(page);
		pd.isFormSubmit();
	}
	
	/** This method will check is receipt is download or not after donation */
	
	@Test(priority = 39,groups = {"Campaign","Donation","Team"})
	public void isReciptDownload_Team() throws InterruptedException {
		Thread.sleep(2000);
		pd=new PublicDonationRepo(page);
		boolean flag=pd.downloadReceipt();
		assertTrue(flag);
	}
	
	
	/** This method will login as a super admin and check is donor details is stored in donorReport or not
	 * and super admin will able to show the donor details or not  */
	@Test(priority = 40,groups = {"Campaign","Donation","Team"},dependsOnMethods = "isDonationDone_Team")
	public void isPublicCampaignTeamDonorDetailsIsStoredInDonorReport() throws InterruptedException, IOException {
		
		page.goBack();
		lp=new LoginPageRepo(page);
		lp.goToLoginPage();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("loginUrl"));
		PlayWrightFactory.login();
		dr=new DashboardRepo(page);
		dr.clickOnDonorReportLink();
		String DonorName=teamFirstName+" "+teamLastName;
		boolean flag=dr.isDonorDetailsShownInDonorReport(DonorName, teamMail, teamAmt);
		assertTrue(flag);
		
	}
	
	
	/** This method will login as a super admin and check is donor details is stored in that particular campaign team donation section 
	 * and super admin will able to show the donor details or not  */
	
	@Test(priority = 41,groups = {"Campaign","Donation","Team"},dependsOnMethods = "isDonationDone_Team")
	public void isDonorDetailsIsShowingInThatParticularCampaignTeamDonatiosSection() throws InterruptedException {
		dr=new DashboardRepo(page);
		dr.clickOnPeerToPeerFundarasing();
		fund=new PeerToPeerFundraisingRepo(page);
		fund.searchCampaign(campName);
		fund.clickOnSearchButton();
		String campaignName=fund.getSearchedCampName();
		assertEquals(campaignName,campName);
		fund.clickOnSearchedCamp();
		fund.goToTeamSection();
		fund.clickRightTeam(teamName);
		String title=fund.getTeamTitle();
		assertEquals(teamName, title);
		fund.goToDonationsSection();
		fund.isDonorDetailsShowingInThisPartocularCampaing(teamFirstName, teamMail, teamAmt);
		
	}
	@Test(priority = 42,groups = {"Campaign","Donation","Team"},dependsOnMethods = "isDonationDone_Team")
	public void isDonorShownInScreeningProcessForApproveTheuser_Team() throws InterruptedException {
		settRepo=new SettingPageRepo(page);
		dr=new DashboardRepo(page);
		dr.goToSettingPage();
		settRepo.gotToScreeningProcessPage();
		String name=teamFirstName+" "+teamLastName;
		settRepo.isDonorDetailsShownInScreeningProcess(name, teamMail,"Donors");

	}
	
	
	
	
	
	
	
	
	
	
	

}
