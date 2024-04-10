package com.affnetz.qa.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.StakeHolderRepo;

public class StakeHolderTest {
	
	static Page page;
	DashboardRepo db;
	StakeHolderRepo sp;
	boolean flag=false;
	String firstName,lastName,mailId,userType;
	Random uc=new Random();
	int x=uc.nextInt(999);
	
//--------------------------------------New User Creation With Valid Inputs-----------------------------------------//
	
	@Test(priority = 0,groups = {"StakeHolder","New User Creation", "Valid"})
	public void goToStakeHolderPage() throws IOException {
		page=PlayWrightFactory.intitBrowser("login");
		PlayWrightFactory.login();
		page.reload();
		db=new DashboardRepo(page);
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
		db.goToStakeHolders();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
		
		
	}
	
	@Test(priority = 1,groups = {"StakeHolder","New User Creation", "Valid"})
	public void clickOnCreateButton() {
		sp=new StakeHolderRepo(page);
		if(sp.getCreateButton().isEnabled())
		{
			assertTrue(true);
			sp.clickOnCreateButton();
		}else {
			assertTrue(false, " CreateBUtton Disable");
		}
	}
	
	
	@Test(priority = 2,groups = {"StakeHolder","New User Creation", "Valid"})
	public void fillAllTheValidInputAndSave() throws InterruptedException {
		sp=new StakeHolderRepo(page);
		firstName="Jani"+x;
		lastName="Kari"+x;
		mailId="engineering+jani"+x+"kari@affnetz.com";
		userType=sp.fillAllUserDetails(mailId, firstName, lastName);	
		sp.clickOnSaveButton();
	}
	
	@Test(priority = 3,groups = {"StakeHolder","New User Creation", "Valid"})
	public void isUserCreated() throws InterruptedException {
		sp=new StakeHolderRepo(page);
		boolean flag=sp.isUserCreated();
		assertTrue(flag, "Some Error Created");
	}
	
	@Test(priority = 4,groups = {"StakeHolder","New User Creation", "Valid"})
	public void verifyTheCreatedUserDetails() {
		sp=new StakeHolderRepo(page);
		String userName=firstName+" "+lastName;
		sp.verifyCreateduserDetails(userType, userName, mailId);
	}
	
	//--------------------------------------New User Creation With Existing Email-----------------------------------------//
	
	
	@Test(priority = 5,groups = {"StakeHolder","New User Creation", "Invalid"})
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
	
	@Test(priority = 6,groups = {"StakeHolder","New User Creation", "Invalid"})
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
	
	@Test(priority = 7,groups = {"StakeHolder","New User Creation", "Invalid"},dependsOnMethods = "clickOnCreateButton_Invalid")
	public void fillExstingEmailIdAndFillAllOtherDetailsAndClickSaveButton() throws InterruptedException {
		sp=new StakeHolderRepo(page);
		if(flag==true)
		{
			userType=sp.fillAllUserDetails(mailId, firstName, lastName);	
			sp.clickOnSaveButton();
		}else {
			Random m=new Random();
			int y=m.nextInt(999);
			firstName="Jani"+y;
			lastName="Kari"+y;
			String mailid=sp.existingMailIds();
			userType=sp.fillAllUserDetails(mailid, firstName, lastName);
			sp.clickOnSaveButton();
		}
		
	}
	
	@Test(priority = 8,groups = {"StakeHolder","New User Creation", "Invalid"},dependsOnMethods = "fillExstingEmailIdAndFillAllOtherDetailsAndClickSaveButton")
	public void isSystemShowingErrorMsgWhenExistingMailIdEntered() {
		sp=new StakeHolderRepo(page);
		String name=sp.getCofirmMsg();
		assertEquals(name, "Email id already registered. Please use a different email id or contact Admin");
	}
	
	//	-------------------------------------------Edit The user Profile--------------------------------------------------------------  //
	@Test(priority = 9,groups = {"StakeHolder", "Edit"})
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
	
	@Test(priority = 10,groups = {"StakeHolder", "Edit"})
	public void searchExistingUserAndClikcOnThat() throws InterruptedException {
		sp=new StakeHolderRepo(page);
		if(flag==true)
		{
			sp.searchUser(mailId);
			sp.clickOnSearchedUser();
			
		}else {
			String mail=sp.existingMailIds();
			sp.searchUser(mail);
			sp.clickOnSearchedUser();
		}
		
		boolean flag=sp.isUserProfileOpen();
		assertTrue(flag, "Not Opend");
		userType=sp.getUserType();
		mailId=sp.getUserMail();
	}
	
	@Test(priority = 11,groups = {"StakeHolder", "Edit"})
	public void clickOnEditaButtonAndUpdateTheUserDetailsAndSave() {
		sp=new StakeHolderRepo(page);
		sp.clickOnEditButton();
		firstName="Kruit"+x;
		lastName="Tiyt"+x;
		sp.editUserDetails(firstName, lastName);
		sp.clickOnEditSaveButton();
	}
	
	@Test(priority = 12,groups = {"StakeHolder", "Edit"})
	public void isUserDetailsUpdatedAndSaved() {
		sp=new StakeHolderRepo(page);
		String userName=firstName+" "+lastName;
		sp.verifyCreateduserDetails(userType, userName, mailId);
	}
	

}

