package com.qa.affnetz.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class DashboardRepo_T1 {
	
	Page page;
	
	private String monthDonorList="xpath=//a[contains(@href,'month-donor-list?')]";
	
	private String donorNameSearch="#input-72";
	
	private String searchButton="xpath=//span[contains(text(),'SEARCH')]";
	
	private String donorTable="xpath=(//table)[1]";
	
	public DashboardRepo_T1(Page page)
	{
		this.page=page;
	}
	
	public void clickOnMonthDonorLink() {
		Locator link=page.locator(monthDonorList);
		link.waitFor();
		link.click();
	}
	
	public void searchDonorDetaills(String dName) {
		Locator searchInput=page.locator(donorNameSearch);
		searchInput.waitFor();
		searchInput.fill(dName);
		page.click(searchButton);
		
		
	}
	
	public boolean isDonorDetailsShowing(String Dname) throws InterruptedException
	{
		Thread.sleep(2000);
		Locator row=page.locator("div.v-data-table__wrapper tr").nth(1);
		row.waitFor();
		boolean flag=false;
		Locator col=row.locator("xpath=//td");
		for(int i=0;i<col.count();i++)
		{
			String name=col.nth(0).textContent();
			System.out.println(name);
			if(name.equalsIgnoreCase(Dname))
			{
				flag=true;
				break;
			}
			
		}
	
		return flag;
		
	}

}
