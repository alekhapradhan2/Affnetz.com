package com.qa.affnetz.InternalPages;

import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TributeRepo {
	
	Page page;
	
	private String searchTribute="#input-76";
	
	private String tributeSearchButton="xpath=//span[contains(text(),'Search')]";
	
	private String tributeName="//h2";
	
	private String donorDetailsTable=".v-data-table__wrapper tr";
	
	public TributeRepo(Page page) {
		this.page=page;
	}
	
	public void searchTributeName(String tName) {
		Locator searchBox=page.locator(searchTribute).first();
		Locator searchButton=page.locator(tributeSearchButton).first();
		searchBox.waitFor();
		searchButton.waitFor();
		searchBox.fill(tName);
		searchButton.click();	
	}
	
	public String getTributeName() {
		String name=page.locator(tributeName).textContent().trim();
		return name;
	}
	
	public void clickOnSearchedTribute() {
		Locator click=page.locator(tributeName).first();
		click.waitFor();
		click.click();
	}
	
	public void isDonorDetailsShowing(String fname,String email,String amount) throws InterruptedException {
		Thread.sleep(2000);
		Locator tableRow=page.locator(donorDetailsTable);
		boolean flag=false;
		String Donorname = null;
		String Donormail = null;
		for(int i=1;i<tableRow.count();i++)
		{
			Locator col=tableRow.nth(i).locator("//td");
			String name=col.nth(0).textContent().trim();
			String  mail=col.nth(2).textContent().trim();
			String  amt=col.nth(6).textContent().trim();
			
			if(name.equalsIgnoreCase(fname)&& mail.equalsIgnoreCase(email)&& amt.contains(amount))
			{
				Donorname=name;
				Donormail=mail;
			}

			
		}
		assertEquals(Donorname, fname);
		assertEquals(Donormail, email);
		
	}
	
	public void show()
	{
		Locator tableRow=page.locator(donorDetailsTable);
		boolean flag=false;
		for(int i=1;i<tableRow.count();i++)
		{
			Locator col=tableRow.nth(i).locator("//td");
			String name=col.nth(0).textContent().trim();
			String mail=col.nth(2).textContent().trim();
			String amt=col.nth(6).textContent().trim();
			System.out.println(name+" "+mail+" "+amt);
			
		}
	}
	
	

}
