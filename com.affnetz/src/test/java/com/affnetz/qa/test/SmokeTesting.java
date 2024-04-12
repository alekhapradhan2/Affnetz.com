package com.affnetz.qa.test;

import java.io.IOException;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.EventPageRepo;
import com.qa.affnetz.InternalPages.HomePageRepo;
import com.qa.affnetz.InternalPages.PeerToPeerFundraisingRepo;
import com.qa.affnetz.InternalPages.StakeHolderRepo;
import com.qa.affnetz.InternalPages.TributeRepo;
import com.qa.affnetz.Publicapages.LoginPageRepo;

public class SmokeTesting {
	
	static Page page;
	LoginPageRepo rp;
	DashboardRepo db;
	HomePageRepo hp;
	StakeHolderRepo sp;
	EventPageRepo ev;
	boolean flag=false;
	String firstName,lastName,stakeHolderMailId,userType;
	Random uc=new Random();
	int x=uc.nextInt(999);
	
	//-------------------------Home Page----------------------------------------//
	@Test(priority = 0,groups = {"Smoke"})
	public void HomePage() throws IOException
	{
		page=PlayWrightFactory.intitBrowser("login");
		PlayWrightFactory.login();
		page.reload();
		hp=new HomePageRepo(page);
		hp.clickOnHome();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("homeUrl"));
	}
	
	
	//-----------------------------------------------Dashboard-------------------------------------//
	@Test(priority = 1,groups = {"Smoke"})
	public void DashBoard() throws IOException {
		
		try {
			db=new DashboardRepo(page);
			db.goToDashBoard();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
		}
		db=new DashboardRepo(page);
		
		db.clickOnMonthDonorLink();
		if( page.locator("//h1").textContent().trim().contains("New Donor Report")) {
			assertTrue(true, "New Donor Report");
		}else {
			assertTrue(false, "New Donor Report");
		}
		page.goBack();
		db.clickOnMemberReportLink();
		if( page.locator("//h1").textContent().trim().contains("New Member Report"))
		{
			assertTrue(true, "New Member Report");
		}else {
			assertTrue(false, "New Member Report");
		}
		page.goBack();
		db.clickOnVolunteerLink();
		if( page.locator("//h1").textContent().trim().contains("Volunteer Report"))
		{
			assertTrue(true, "Volunteer Report");
		}else {
			assertTrue(false, "Volunteer Report");
		}
		
		page.goBack();
		db.clickOnDonorReportLink();
		if( page.locator("//h1").textContent().trim().contains("Donation Report"))
		{
			assertTrue(true, "Donation Reportt");
		}else {
			assertTrue(false, "Donation Report");
		}	
	}
	
	//---------------------------------------------Stakeholder------------------------------------------------------//
	
	//--------------------------------------StakeHolder New User Creation With Valid Inputs-----------------------------------------//
	
		@Test(priority = 2,groups = {"StakeHolder","New User Creation", "Valid","Smoke"})
		public void goToStakeHolderPage() throws IOException {
			try {
				db=new DashboardRepo(page);
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
			} catch (Exception e) {
				page=PlayWrightFactory.intitBrowser("login");
				PlayWrightFactory.login();
				page.reload();
				db=new DashboardRepo(page);
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
			}
			
			
			
		}
		
		@Test(priority = 3,groups = {"StakeHolder","New User Creation", "Valid","Smoke"},dependsOnMethods = "goToStakeHolderPage")
		public void clickOnCreateButton_Stakeholder() {
			sp=new StakeHolderRepo(page);
			if(sp.getCreateButton().isEnabled())
			{
				assertTrue(true);
				sp.clickOnCreateButton();
			}else {
				assertTrue(false, " CreateBUtton Disable");
			}
		}
		
		
		@Test(priority = 4,groups = {"StakeHolder","New User Creation", "Valid","Smoke"},dependsOnMethods ="clickOnCreateButton_Stakeholder" )
		public void fillAllTheValidInputAndSave() throws InterruptedException {
			sp=new StakeHolderRepo(page);
			firstName="Jani"+x;
			lastName="Kari"+x;
			stakeHolderMailId="engineering+jani"+x+"kari@affnetz.com";
			userType=sp.fillAllUserDetails(stakeHolderMailId, firstName, lastName);	
			sp.clickOnSaveButton();
		}
		
		@Test(priority = 5,groups = {"StakeHolder","New User Creation", "Valid","Smoke"})
		public void isUserCreated() throws InterruptedException {
			sp=new StakeHolderRepo(page);
			boolean flag=sp.isUserCreated();
			assertTrue(flag, "Some Error Created");
		}
		
		@Test(priority = 6,groups = {"StakeHolder","New User Creation", "Valid","Smoke"})
		public void verifyTheCreatedUserDetails() {
			sp=new StakeHolderRepo(page);
			String userName=firstName+" "+lastName;
			sp.verifyCreateduserDetails(userType, userName, stakeHolderMailId);
		}
		
		//--------------------------------------StakeHolder New User Creation With Existing Email-----------------------------------------//
		
		
		@Test(priority = 7,groups = {"StakeHolder","New User Creation", "Invalid","Smoke"})
		public void goToStakeHolderPage_Invalid() throws IOException {
			try {
				db=new DashboardRepo(page);
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
				flag=true;
				
			} catch (Exception e) {
				page=PlayWrightFactory.intitBrowser("login");
				PlayWrightFactory.login();
				page.reload();
				db=new DashboardRepo(page);
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
			}
		}
		
		@Test(priority = 8,groups = {"StakeHolder","New User Creation", "Invalid","Smoke"},dependsOnMethods ="goToStakeHolderPage_Invalid")
		public void clickOnCreateButton_Invalid() {
			sp=new StakeHolderRepo(page);
			if(sp.getCreateButton().isEnabled())
			{
				assertTrue(true);
				sp.clickOnCreateButton();
			}else {
				assertTrue(false, " CreateBUtton Disable");
			}
		}
		
		@Test(priority = 9,groups = {"StakeHolder","New User Creation", "Invalid","Smoke"},dependsOnMethods = "clickOnCreateButton_Invalid")
		public void fillExstingEstakeHolderMailIdAndFillAllOtherDetailsAndClickSaveButton() throws InterruptedException {
			sp=new StakeHolderRepo(page);
			if(flag==true)
			{
				userType=sp.fillAllUserDetails(stakeHolderMailId, firstName, lastName);	
				sp.clickOnSaveButton();
			}else {
				Random m=new Random();
				int y=m.nextInt(999);
				firstName="Jani"+y;
				lastName="Kari"+y;
				String stakeHolderMailId=sp.existingMailIds();
				userType=sp.fillAllUserDetails(stakeHolderMailId, firstName, lastName);
				sp.clickOnSaveButton();
			}
			
		}
		
		@Test(priority = 10,groups = {"StakeHolder","New User Creation", "Invalid","Smoke"},dependsOnMethods = "fillExstingEstakeHolderMailIdAndFillAllOtherDetailsAndClickSaveButton")
		public void isSystemShowingErrorMsgWhenExistingstakeHolderMailIdEntered() {
			sp=new StakeHolderRepo(page);
			String name=sp.getCofirmMsg();
			assertEquals(name, "Email id already registered. Please use a different email id or contact Admin");
		}
		
		//	-------------------------------------------StakeHolder Edit The user Profile--------------------------------------------------------------  //
		@Test(priority = 11,groups = {"StakeHolder", "Edit","Smoke"})
		public void goToStakeHolderPage_Edit() throws IOException {
			try {
				db=new DashboardRepo(page);
				page.reload();
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
				flag=true;
				
			} catch (Exception e) {
				page=PlayWrightFactory.intitBrowser("login");
				PlayWrightFactory.login();
				page.reload();
				db=new DashboardRepo(page);
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
				db.goToStakeHolders();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
			}
		}
		
		@Test(priority = 12,groups = {"StakeHolder", "Edit","Smoke"},dependsOnMethods = "goToStakeHolderPage_Edit")
		public void searchExistingUserAndClikcOnThat() throws InterruptedException {
			sp=new StakeHolderRepo(page);
			if(flag==true)
			{
				sp.searchUser(stakeHolderMailId);
				sp.clickOnSearchedUser();
				
			}else {
				String mail=sp.existingMailIds();
				sp.searchUser(mail);
				sp.clickOnSearchedUser();
			}
			
			boolean flag=sp.isUserProfileOpen();
			assertTrue(flag, "Not Opend");
			userType=sp.getUserType();
			stakeHolderMailId=sp.getUserMail();
		}
		
		@Test(priority = 13,groups = {"StakeHolder", "Edit","Smoke"})
		public void clickOnEditaButtonAndUpdateTheUserDetailsAndSave() {
			sp=new StakeHolderRepo(page);
			sp.clickOnEditButton();
			firstName="Kruit"+x;
			lastName="Tiyt"+x;
			sp.editUserDetails(firstName, lastName);
			sp.clickOnEditSaveButton();
		}
		
		@Test(priority = 14,groups = {"StakeHolder", "Edit","Smoke"})
		public void isUserDetailsUpdatedAndSaved() {
			sp=new StakeHolderRepo(page);
			String userName=firstName+" "+lastName;
			sp.verifyCreateduserDetails(userType, userName, stakeHolderMailId);
		}
		
		
		//------------------------------------------------Event------------------------------------------------------------------------------//
		//--------------------------------------------Event New Event Creation---------------------------------------------------------------//
		
		String eventName,BriefDescription,Description,MailId,OrgaNizer;
		Random r=new Random();
		int y=r.nextInt(999999);
		
		@Test(priority = 15,groups = {"Smoke"})
		public void goToEventpage() throws IOException {
			try {
				db=new DashboardRepo(page);
				db.goToEventPage();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));	
			} catch (Exception e) {
				page=PlayWrightFactory.intitBrowser("login");
				PlayWrightFactory.login();
				page.reload();
				db=new DashboardRepo(page);
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
				db.goToEventPage();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));	
			}
		
		}
		
		@Test(priority = 16,groups = {"Smoke"})
		public void clickOnCreateButton_Event() {
			ev=new EventPageRepo(page);
			if(ev.getCreateButton().isEnabled())
			{
				assertTrue(true, "Clickabale");
				ev.clickOnCreateButton();
			}else {
				assertTrue(false, "Disbale");
			}
		}
		
		@Test(priority = 17,groups = {"Smoke"})
		public void fillAllEventDetails() throws IOException, InterruptedException {
			ev=new EventPageRepo(page);
			eventName=PlayWrightFactory.initProp().getProperty("EventName")+x;
			BriefDescription=PlayWrightFactory.initProp().getProperty("BriefDescription");
			Description=PlayWrightFactory.initProp().getProperty("Description");
			MailId="engineering+event"+x+"new@affnetz.com";
			ev.fillEventTitle(eventName);
			ev.setBriefDescription(BriefDescription);
			ev.setDescription(Description);
			ev.selectEventType();
			OrgaNizer=ev.selectOraganizerDetails(MailId);
			ev.setDates();
			System.out.println(OrgaNizer);
			ev.setPrice();
			ev.setAttendees();
			ev.doPubishTheEventAndSaveTheEvent();
			
		}
		
		@Test(priority = 18,groups = {"Smoke"})
		public void isEventCreated() throws InterruptedException {
			ev=new EventPageRepo(page);
			ev.verifyTheEventDetails(eventName, OrgaNizer, MailId);
		}
		
		//-----------------------------Edit The Event ----------------------------------------------//
		
		@Test(priority = 19,groups = {"Smoke"})
		public void goToEventPage_Edit() throws Exception {
			
				db=new DashboardRepo(page);
				db.goToEventPage();
				assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));	
				
			
		}
		@Test(priority = 20,groups = {"Smoke"})
		public void SelectOneEventAndClickEdit() throws InterruptedException, IOException {
			
			ev=new EventPageRepo(page);
			
			ev.searchEvent(eventName);
			ev.clickOnOneEvent();
			ev.clickEditEvent();
			eventName=PlayWrightFactory.initProp().getProperty("EditEventName")+x;
			ev.changeEventName(eventName);
			ev.clickOnSaveButton();
		}
		@Test(priority = 21,groups = {"Smoke"})
		public void isEventDetailsIsUpdate() {
			ev=new EventPageRepo(page);
			ev.verifyTheEventDetails(eventName, OrgaNizer, MailId);
		}
		
		
		
	
	
	

}
