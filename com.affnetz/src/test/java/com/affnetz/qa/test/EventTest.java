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
import com.qa.affnetz.InternalPages.EventPageRepo;

public class EventTest {
	
	static Page page;
	DashboardRepo db;
	EventPageRepo ev;
	
	//--------------------------------------------New Event Creation---------------------------------------------------------------//
	
	String eventName,BriefDescription,Description,mailId,OrgaNizer;
	Random r=new Random();
	int x=r.nextInt(999999);
	
	@Test(priority = 0)
	public void goToEventpage() throws IOException {
		page=PlayWrightFactory.intitBrowser("login");
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("loginUrl"));
		PlayWrightFactory.login();
		page.reload();
		db=new DashboardRepo(page);
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("dashboardUrl"));
		db.goToEventPage();
		assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));	
	}
	
	@Test(priority = 1)
	public void clickOnCreateButton() {
		ev=new EventPageRepo(page);
		if(ev.getCreateButton().isEnabled())
		{
			assertTrue(true, "Clickabale");
			ev.clickOnCreateButton();
		}else {
			assertTrue(false, "Disbale");
		}
	}
	
	@Test(priority = 2)
	public void fillAllEventDetails() throws IOException, InterruptedException {
		ev=new EventPageRepo(page);
		eventName=PlayWrightFactory.initProp().getProperty("EventName")+x;
		BriefDescription=PlayWrightFactory.initProp().getProperty("BriefDescription");
		Description=PlayWrightFactory.initProp().getProperty("Description");
		mailId="engineering+event"+x+"new@affnetz.com";
		ev.fillEventTitle(eventName);
		ev.setBriefDescription(BriefDescription);
		ev.setDescription(Description);
		ev.selectEventType();
		OrgaNizer=ev.selectOraganizerDetails(mailId);
		ev.setDates();
		System.out.println(OrgaNizer);
		ev.setPrice();
		ev.setAttendees();
		ev.doPubishTheEventAndSaveTheEvent();
		
	}
	
	@Test(priority = 3)
	public void isEventCreated() throws InterruptedException {
		ev=new EventPageRepo(page);
		ev.verifyTheEventDetails(eventName, OrgaNizer, mailId);
	}
	
	
	//-----------------------------Edit The Event ----------------------------------------------//
	
	@Test(priority = 4)
	public void goToEventPage_Edit() throws Exception {
		
			db=new DashboardRepo(page);
			db.goToEventPage();
			assertThat(page).hasURL(PlayWrightFactory.initProp().getProperty("eventUrl"));	
			
		
	}
	@Test(priority = 5)
	public void SelectOneEventAndClickEdit() throws InterruptedException, IOException {
		
		ev=new EventPageRepo(page);
		
		ev.searchEvent(eventName);
		ev.clickOnOneEvent();
		ev.clickEditEvent();
		eventName=PlayWrightFactory.initProp().getProperty("EditEventName")+x;
		ev.changeEventName(eventName);
		ev.clickOnSaveButton();
	}
	@Test(priority = 6)
	public void isEventDetailsIsUpdate() {
		ev=new EventPageRepo(page);
		ev.verifyTheEventDetails(eventName, OrgaNizer, mailId);
	}
	

}
