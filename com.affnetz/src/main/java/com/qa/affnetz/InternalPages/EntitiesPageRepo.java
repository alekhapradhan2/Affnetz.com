package com.qa.affnetz.InternalPages;

import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EntitiesPageRepo {
	
	Page page;
	
	private String entitiName="//a[@class='my-1 file-name']";
	
	private String searchEntity="#input-72";
	
	private String searchButton="//span[contains(text(),'Search')]";
	
	public String name;
	private String clickSearchedEntiti="//a[contains(text(),'"+name+"')]";
	
	private String entitiHeadline="//div[@class='headline']";
	
	private String manualButton="//span[contains(text(),'Manual Donation')]";
	
	private String datePicker="//span[contains(text(),'OK ')]";
	
	private String dateInput="#input-150";
	
	private String calenderTable="(//table)[2]";
	
	private String donationAmount="#input-140";
	
	private String paymentMode="#input-143";
	
	private String paymentModeList="#list-143";
	
	private String paymentModeName="//div[contains(@id,'list-item-161')]";
	
	private String addDonationButton="//span[text()='Add Donation']";
	
	private String eventsSection="(//div[text()='Events'])[2]";
	
	public EntitiesPageRepo(Page page)
	{
		this.page=page;
	}
	
	public void searchEntiti(String entitiName) throws InterruptedException {
		page.fill(searchEntity, entitiName);
		Thread.sleep(1000);
		page.click(searchButton);
		
	}
	
	public void clickOnSearchedEntiti(String entitiname)
	{
		name=entitiname;
		page.click(clickSearchedEntiti);
	}
	
	public String getEntitiName()
	{
		Locator entiti=page.locator(entitiName).first();
		entiti.waitFor();
		String name=entiti.textContent().trim();
		return name.substring(6);
	}
	
	public String getEntitiHeadline() {
		Locator entiti=page.locator(entitiHeadline).first();
		entiti.waitFor();
		String title=entiti.textContent().trim();
		return title;
	}
	
	public void clickOneEntiti() {
		page.click(entitiName);
	}
	
	public void clickOnManualDonationButton() {
		page.click(manualButton);
	}
	
	
	public int setDonationAmount()
	{
		page.locator(donationAmount).clear();
		Random x=new Random();
		int amt=x.nextInt(500);
		page.locator(donationAmount).fill(""+amt+"");
		return amt;
	}
	public void selectPaymentMode() throws InterruptedException {
		page.click(paymentMode);
		Thread.sleep(1000);
		Locator payment=page.locator(paymentModeList);
		Random r=new Random();
		int x=r.nextInt(5);
		Locator allPaymentMode=payment.locator(paymentModeName);
		allPaymentMode.nth(x).click();
	}
	
	public void setDateAndTime(String EDate) throws InterruptedException
	{
		
			page.click(dateInput);
			Thread.sleep(1000);
			Locator table=page.locator(calenderTable);
			Locator row=table.locator("//tr");
			for(int i=1;i<=row.count();i++)
			{
				Locator col=row.locator("//td");
				for(int j=0;j<=col.count();j++)
				{
					String date=col.nth(j).textContent().trim();
					if(date.equals(EDate))
					{
						col.nth(j).click();
						break;
					}
				}
			}
			Thread.sleep(2000);
		page.click(datePicker);
	}
	
	public void clickOnAddDonationButton() {
		page.click(addDonationButton);
	}
	
	public void isThatParticularEventShownInEntitiEventSection() {
		page.click(eventsSection);
	}
}
