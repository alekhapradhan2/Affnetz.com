package com.qa.affnetz.InternalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EventPageRepo {
	
	Page page;
	
	private String createButton="//span[contains(text(),'Create')]";
	
	private String eventName="//h4";
	
	private String eventTitle="//h2";
	
	private String eventEdit="//i[text()='edit']";
	
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

}
