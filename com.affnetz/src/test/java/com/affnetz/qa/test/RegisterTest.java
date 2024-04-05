package com.affnetz.qa.test;

import java.io.IOException;
import java.util.Random;

import org.testng.annotations.Test;

import com.affnetz.qa.factory.PlayWrightFactory;
import com.microsoft.playwright.Page;
import com.qa.affnetz.InternalPages.DashboardRepo;
import com.qa.affnetz.InternalPages.SettingPageRepo;
import com.qa.affnetz.InternalPages.StakeHolderRepo;
import com.qa.affnetz.Publicapages.LoginPageRepo;
import com.qa.affnetz.Publicapages.RegisterPageRepo;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterTest {
	
	Page page;
	RegisterPageRepo rp;
	LoginPageRepo lp;
	String FirstName,LastName,MailId,UserName,Password;
	DashboardRepo db;
	StakeHolderRepo sk;
	SettingPageRepo sp;
	Random rm=new Random();
	int x=rm.nextInt(999);
	
	@Test(priority = 0)
	public void goaToRegisterpage() throws IOException {
		page=PlayWrightFactory.intitBrowser("login");
		lp=new LoginPageRepo(page);
		lp.clickRegister();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("RegisterUrl"));
	}
	
	@Test(priority = 1)
	public void giveAllUserDetails() throws InterruptedException
	{
		rp=new RegisterPageRepo(page);
		FirstName="Alekh"+x;
		LastName="Pradhan"+x;
		MailId="engineering+reg"+x+"@affnetz.com";
		UserName="alek"+x+"ha";
		Password="Alekha@"+x;
		System.out.println(FirstName+" "+LastName+MailId+" "+UserName+" "+Password);
		rp.setUserDetails(FirstName,LastName,MailId,UserName,Password);
		rp.setUserAddress();
		rp.setCardDetails();
	}
	@Test(priority = 2)
	public void submitTheApplication() {
		rp=new RegisterPageRepo(page);
		rp.SubmitApp();
	}
	
	@Test(priority = 3)
	public void loginWithSuperAdminAndCheckThisUserIsCreatedOrNot() throws IOException, InterruptedException {
		PlayWrightFactory.login();
		db=new DashboardRepo(page);
		page.reload();
		db.goToStakeHolders();
		sk=new StakeHolderRepo(page);
		sk.searchUser(MailId);
		String name=FirstName+"  "+LastName;
		sk.isUserCreated(name);
		sk.clickOnSearchedUser();
		sk.verifyUserDetails("Members",name,MailId);
		
	}
	
	@Test(priority = 4)
	public void checkThisUserGoForScreeningProcessOrNot() throws InterruptedException {
		db=new DashboardRepo(page);
		db.goToSettingPage();
		sp.gotToScreeningProcessPage();
		sp.isDonorDetailsShownInScreeningProcess(FirstName+" "+LastName, MailId, "Members");
	}
	
	
	
	

}
