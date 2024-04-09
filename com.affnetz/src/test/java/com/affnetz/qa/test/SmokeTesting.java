package com.affnetz.qa.test;

import java.io.IOException;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

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
	@Test(priority = 0)
	public void HomePage() throws IOException
	{
		page=PlayWrightFactory.intitBrowser("login");
		PlayWrightFactory.login();
		page.reload();
		hp=new HomePageRepo(page);
		hp.clickOnHome();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("homeUrl"));
	}
	
	@Test(priority = 1)
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
	
	StakeHolderRepo sk;
	@Test(priority = 2)
	public void stakeHolder() throws IOException, InterruptedException {
		try {
			db=new DashboardRepo(page);
			db.goToStakeHolders();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.goToStakeHolders();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("stakeholderUrl"));
		}
		sk=new StakeHolderRepo(page);
		
		
		
		if(sk.getCreateButton().isEnabled() && sk.getImportButton().isEnabled())
		{
			assertTrue(true);
		}else {
			assertTrue(false, "StakeHolder");
		}
		
		sk.clickOnCreateButton();
		if( page.locator("//h1").textContent().trim().equals("New Stakeholders"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "New User Creation");
		}
		
		page.goBack();
		sk.clickOnImportButton();
		if(page.locator("//h1").first().textContent().trim().equals("Import Users"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Import Users");
		}
		
		page.goBack();
		
		sk.clickOnSearchedUser();
		if(sk.getEditBUtton().isEnabled() && sk.getEditBUtton().isVisible())
		{
			assertTrue(true);
		}else {
			assertTrue(false, "User Profile");
		}
		
		sk.clickOnEditButton();
		if( page.locator("//h1").textContent().trim().contains("Edit Profile"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Edit Profiles");
		}	
	}
	EventPageRepo ev;
	@Test(priority = 3)
	public void EventPage() throws IOException {
		try {
			db=new DashboardRepo(page);
			db.goToEventPage();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));
		} catch (Exception e) {
			page=PlayWrightFactory.intitBrowser("login");
			PlayWrightFactory.login();
			db=new DashboardRepo(page);
			page.reload();
			db.goToEventPage();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));
		}
		ev=new EventPageRepo(page);
		
		if(ev.getCreateButton().isEnabled())
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Event page");
		}
		
		ev.clickOnCreateButton();
		if( page.locator("//h1").textContent().trim().contains("Create Event"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Create Event");
		}	
		
		page.goBack();
		String eventName=ev.getEventName();
		ev.clickOnOneEvent();
		String eventTitle=ev.getEventTitle();
		assertEquals(eventTitle,eventName);
		page.goBack();
		ev.clickEditEvent();
		if( page.locator("//h1").textContent().trim().equals("Edit Event"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Edit Event");
		}
		
		
	}
	
	PeerToPeerFundraisingRepo pr;
	@Test(priority = 4)
	public void peerToPeerFundRaisIng() throws IOException {
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
		
		pr=new PeerToPeerFundraisingRepo(page);
		if(pr.getCreateButton().isEnabled() && pr.getCreateButton().isVisible())
		{
			assertTrue(true);
		}
		else {
			assertTrue(false, "Campaign Page");
		}
		
		pr.clickOnCreateButton();
		if( page.locator("//h1").textContent().trim().equals("Create Campaign"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Create Campaign");
		}
		page.goBack();
		
		pr.clickOnTeamButton();
		if( page.locator("//h1").textContent().trim().equals("Teams"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Team Creation");
		}
		page.goBack();
		String name=pr.getCampaignName();
		pr.clickOnOneCampaign();
		String title=pr.getCampaignName();
		assertEquals(title, name);
		page.goBack();
		
		pr.clickEditCampaign();
		
		if( page.locator("//h1").textContent().trim().equals("Edit Campaign"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Edit Campaign");
		}	
	}
	TributeRepo tr;
	@Test
	public void tributePage() throws IOException {
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
		tr=new TributeRepo(page);
		if(tr.getCreateButton().isEnabled())
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Tribute Create Button");
		}
		
		tr.clickOnCreateButton();
		if( page.locator("//h1").textContent().trim().equals("Create Tribute"))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "Create Tribute");
		}	
		
		page.goBack();
		
		String name=tr.getTributeName();
		tr.clickOnSearchedTribute();
		String title=tr.getTributeName();
		assertEquals(title, name);
		
		
		
	}
	
	
	
	

}
