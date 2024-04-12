package com.qa.affnetz.InternalPages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EventPageRepo {
	
	Page page;
	
	private String createButton="//span[contains(text(),'Create')]";
	
	private String eventName="//h4";
	
	private String eventTitle="//h2[@class='js-event-title']";
	
	private String organizername="//div[contains(@class,'organizer-name')]";
	
	private String oragnizerMailId="(//a[contains(@href,'mailto')])[1]";
	
	private String about="(//p)";
	
	private String eventEdit="//i[text()='edit']";
	
	private String eventSearchBox="#input-73";
	
	private String searchButton="//span[contains(text(),'Search')]";
	
	
	//-----------------------------------New Event  Create Repo--------------------------------------//
	
	private String title="#input-78";
	
	private String BriefDescription="#input-81";
	
	private String eventtype="#input-84";
	
	private String eventTypeList="#list-84";
	
	private String eventTypeName="//div[contains(@id,'list-item-216')]";
	
	private String Description="(//div[contains(@class,'ql-editor')])[1]";
	
	private String Organizer="#input-100";
	
	private String organizerList="#list-100";
	
	private String oraganizerNames="//div[contains(@id,'list-item-222')]";
	
	private String Email="#input-105";
	
	private String phoneNumber="#input-108";
	
	private String EventStartDate="#input-135";
	
	private String startDateCal="(//table)[1]";
	
	private String EventEndDate="#input-140";
	
	private String endDateCal="(//table)[2]";
	
	private String RegistrationClosingTime="#input-145";
	
	private String regDateCal="(//table)[3]";
	
	private String RegularPrice="#input-148";
	
	private String VIPPrice="#input-165";
	
	private String MinimumAttendees="#input-173";
	
	private String MaximumAttendees="#input-176";
	
	private String publish="#input-200";
	
	private String publishList="#list-200";
	
	private String publishName="//div[contains(@id,'list-item-')]";
	
	private String saveButton="//span[contains(text(),'Save')]";
	
	//----------------------------------Event Edit Repo------------------------------------------//
	 private String Title="#input-74";
	
	
	
	public EventPageRepo(Page page)
	{
		this.page=page;
	}
	
	public String getEventName()
	{
		Locator name=page.locator(eventName).first();
		name.waitFor();
		String eventname=name.textContent().trim();
		return eventname;
	}
	
	public void clickOnOneEvent() {
		Locator name=page.locator(eventName).first();
		name.waitFor();
		name.click();
	}
	
	public Locator getCreateButton()
	{
		Locator create=page.locator(createButton);
		return create;
	}
	
	public void clickOnCreateButton()
	{
		Locator create=page.locator(createButton);
		create.waitFor();
		page.click(createButton);
	}
	
	public String getEventTitle() {
		Locator name=page.locator(eventTitle).first();
		name.waitFor();
		String title=name.textContent().trim();
		return title;
	}
	
	public void clickEditEvent() {
		page.click(eventEdit);
	}
	
	public void searchEvent(String EventName) throws InterruptedException {
		page.fill(eventSearchBox, EventName);
		Thread.sleep(1000);
		page.click(searchButton);
	}
	 //--------------------------------------------EVent Creation Methods--------------------------------//
	public void fillEventTitle(String eventTitle) {
		page.fill(title, eventTitle);
	}
	
	public void setBriefDescription(String disc)
	{
		page.fill(BriefDescription, disc);
	}
	
	public void selectEventType() throws InterruptedException {
		page.click(eventtype);
		Thread.sleep(1000);
		Locator eventList=page.locator(eventTypeList);
		Locator TypeName=eventList.locator(eventTypeName);
		TypeName.first().click();
	}
	
	public void setDescription(String desc)
	{
		page.fill(Description, desc);
	}
	
	public String selectOraganizerDetails(String mailId) throws InterruptedException {
		page.click(Organizer);
		Thread.sleep(1000);
		Locator List=page.locator(organizerList);
		Locator names=List.locator(oraganizerNames);
		Random r=new Random();
		int x=r.nextInt(15);
		String organizerName=names.nth(x).textContent().trim();
		names.nth(x).click();
		page.locator(Email).clear();
		page.fill(Email, mailId);
		page.locator(phoneNumber).clear();
		page.fill(phoneNumber, "6371772552856584");
		return organizerName;
	}
	
	public void setDates() throws InterruptedException {
		
		
		//--------Start Date---------------//
		while (!page.locator("//span[text()='OK ']").isVisible()) {
		
			page.click(EventStartDate);
		}
		Thread.sleep(1000);
		Locator table=page.locator(startDateCal);
		Locator row=table.locator("//tr");
		row.nth(2).locator("//td").nth(2).click();
		Thread.sleep(1000);
		page.click("//span[text()='OK ']");
		
		
		//-----------End Date---------------//
		while (!page.locator("(//span[text()='OK '])[2]").isVisible()) {
			
			page.click(EventEndDate);
		}
		Thread.sleep(1000);
		Locator table1=page.locator(endDateCal);
		Locator row1=table1.locator("//tr");
		row1.nth(4).locator("//td").nth(3).click();
		Thread.sleep(1000);
		page.click("(//span[text()='OK '])[2]");
		
		// ---------------Reg. Closing Date----------//
		while (!page.locator("(//span[text()='OK '])[3]").isVisible()) {
			
			page.click(RegistrationClosingTime);
		}
		
		Thread.sleep(1000);
		Locator table2=page.locator(regDateCal);
		Locator row2=table2.locator("//tr");
		row2.nth(3).locator("//td").nth(3).click();
		Thread.sleep(1000);
		page.click("(//span[text()='OK '])[3]");
	}
	
	public void setPrice() {
		Random rm=new Random();
		int x=rm.nextInt(499);
		int vipPrice=x+50;
		page.fill(RegularPrice, ""+x+"");
		page.fill(VIPPrice, ""+vipPrice+"");
	}
	
	public void setAttendees() {
		Random rm=new Random();
		int x=rm.nextInt(200);
		int max=x+50;
		
		page.fill(MinimumAttendees, ""+x+"");
		page.fill(MaximumAttendees, ""+max+"");
		
	}
	
	public void doPubishTheEventAndSaveTheEvent() throws InterruptedException {
		page.click(publish);
		Thread.sleep(1000);
		Locator list=page.locator(publishList);
		Locator name=list.locator(publishName);
		name.nth(1).click();
		page.click(saveButton);
	}
	
	public void verifyTheEventDetails(String eventName,String orgaNizerName,String mail) {
		String eventname=page.locator(eventTitle).textContent().trim();
		String oarganizename=page.locator(organizername).textContent().trim();
		String organizerMail=page.locator(oragnizerMailId).textContent().trim()	;
		
		if(orgaNizerName.contains(oarganizename))
		{
			assertTrue(true);
		}else {
			assertTrue(false, "OrganizerName Not Matched");
		}
		assertEquals(eventname, eventName);
		assertEquals(organizerMail, mail);
	
			
	}
	
	public void clickOnSaveButton() {
		page.click(saveButton);
	}
	
	//------------Edit Event------------------------//
	
	public void changeEventName(String eventName)
	{
		page.locator(Title).clear();
		page.fill(Title, eventName);
	}
	
	

}
