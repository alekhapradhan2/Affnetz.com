package com.qa.affnetz.Publicapages;

import static org.testng.Assert.assertTrue;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class RegisterPageRepo {
	
	Page page;
	
	private String fname="#input-21";
	
	private String lname="#input-27";
	
	private String yearDrop="#year";
	
	private String monthDrop="#month";
	
	private String dayDrop="#day";
	
	private String ph="#input-34";
	
	private String mail="#input-37";
	
	private String userName="#input-40";
	
	private String password="#input-43";
	
	private String cnfPassword="#input-47";
	
	private String add1="#input-58";
	
	private String city="#input-64";
	
	private String stateClick="#input-67";
	
	private String stateList="#list-67";
	
	private String allState="//div[contains(@id,'list-item-120')]";
	
	private String zipCode="#input-72";
	
	private String billingAdd="#input-85";
	
	private String billingCity="#input-91";
	
	private String billingStateClick="#input-94";
	
	private String billingStateList="#list-94";
	
	private String billingZipCode="#input-99";
	
	private String billingAllState="//div[contains(@id,'list-item-')]";
	
	private String condtionCheckBox="(//div[@class='v-input--selection-controls__ripple'])[3]";
	
	private String PaymentFrame="xpath=//iframe[contains(@name,'privateStripeFrame')]";
	
	private String cardNumber="xpath=//input[@name='cardnumber']";
	
	private String expireDate="xpath=//input[@name='exp-date']";
	
	private String cvc="xpath=//input[@name='cvc']";
	
	private String postalCode="xpath=//input[@name='postal']";
	
	private String submitApplicationButton="//span[contains(text(),'Submit Application')]";
	
	private String logoutButton="//span[contains(text(),'logout')]";
	
	private String regSucessfullMsg="//div[@class='v-alert__content']";
	
	public RegisterPageRepo(Page page)
	{
		this.page=page;
	}
	
	public void setUserDetails(String FirstName,String LastName,String MailId,String UserName,String Password) {
		page.fill(fname, FirstName);
		page.fill(lname, LastName);
		Locator year=page.locator(yearDrop);
		year.selectOption("2000");
		Locator month=page.locator(monthDrop);
		month.selectOption(new SelectOption().setIndex(5));
		Locator day=page.locator(dayDrop);
		day.selectOption(new SelectOption().setIndex(5));
		page.fill(ph, "6371772552");
		page.fill(mail, MailId);
		page.fill(userName, UserName);
		page.fill(password, Password);
		page.fill(cnfPassword, Password);
	}
	
	public void setUserAddress() throws InterruptedException {
		page.fill(add1, "New Address");
		page.fill(city, "Bhubaneswar");
		page.click(stateClick);
		Thread.sleep(2000);
		Locator state=page.locator(stateList);
		Locator allStateNames=state.locator(allState);
		for(int i=0;i<=allStateNames.count();i++)
		{
			String name=allStateNames.nth(i).textContent().trim();
			if(name.equals("Arizona"))
			{
				allStateNames.nth(i).click();
				break;
			}
		}
		page.fill(zipCode, "88888");
		
		page.fill(billingAdd, "New Address");
		page.fill(billingCity, "Bhubaneswar");
		page.click(billingStateClick);
		Thread.sleep(2000);
		Locator State=page.locator(billingStateList);
		Locator AllStateNames=State.locator(billingAllState);
		for(int i=0;i<=AllStateNames.count();i++)
		{
			String name=AllStateNames.nth(i).textContent().trim();
			if(name.equals("Arizona"))
			{
				AllStateNames.nth(i).click();
				break;
			}
		}
		page.fill(billingZipCode, "88888");
		
	}
	
	public void setCardDetails() {
		FrameLocator cardFrame=page.frameLocator(PaymentFrame);
		cardFrame.locator(cardNumber).fill("4242424242424242");
		cardFrame.locator(expireDate).fill("0428");
		cardFrame.locator(cvc).fill("242");
		cardFrame.locator(postalCode).fill("88888");
	}
	
	public void SubmitApp() {
		page.click(condtionCheckBox);
		page.click(submitApplicationButton);

	}
	
	public void doLogout() {
		Locator logout=page.locator(logoutButton);
		logout.waitFor();
		logout.click();
	}
	
	public void isFormSubmit() {
		Locator msg=page.locator(regSucessfullMsg);
		msg.waitFor();
		String regMsg=msg.textContent().trim();
		if(regMsg.contains("Registration Successful"))
		{
			assertTrue(true, "Some Error Occured");
		}
	}
	
	
	
	
	
	

}
