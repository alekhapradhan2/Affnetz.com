package com.qa.affnetz.InternalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForCloseOptions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class DashboardRepo {
	
	Page page;
	
	private String monthDonorList="xpath=//a[contains(@href,'month-donor-list?')]";
	
	private String memberReprtList="//a[contains(@href,'member-list')]";
	
	private String volunteerReportLink="//a[contains(@href,'volunteer-report')]";
	
	private String donorNameSearch="#input-72";
	
	private String searchButton="xpath=//span[contains(text(),'SEARCH')]";
	
	private String Report="xpath=//div[text()='Reports']";
	
	private String tributeDonationReport="xpath=//div[text()='Tribute Donation Report']";
	
	private String TributeLink="xpath=//div[text()='Tribute']";
	
	private String donorReportLink="//a[contains(@href,'donations-report')]";
	
	private String DonorReportTable=".v-data-table__wrapper tr";
	
	private String peerToPeerFundaraisingLink="xpath=//div[text()='Peer-to-Peer Fundraising']";
	
	private String dashboardLink="//div[text()='Dashboard']";
	
	private String stakeHoldersLink="//div[text()='Stakeholders']";
	
	private String settingsLink="//div[text()='Settings']";
	
	private String entitiesLink="//div[text()='Entities']";
	
	private String eventLink="//div[text()='Events']";
	
	
	
	
	public DashboardRepo(Page page)
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
	
	public void isDonorDetailsShowing(String Dname) throws InterruptedException
	{
		Thread.sleep(2000);
		Locator row=page.locator("div.v-data-table__wrapper tr").nth(1);
		row.waitFor();
		Locator col=row.locator("xpath=//td");
		String donorName=null;
		for(int i=0;i<col.count();i++)
		{
			String name=col.nth(0).textContent();
			System.out.println(name);
			if(name.equalsIgnoreCase(Dname))
			{
				donorName=name;
				break;
			}
			
		}
		
		assertEquals(donorName, Dname);

		
	}
	
	public boolean isDonorDetailsShownInDonorReport(String DonorName, String DonorMail,String DonorAmt) throws InterruptedException {
		Locator row=page.locator(DonorReportTable);
		Thread.sleep(3000);
		boolean flag=false;
		for(int i=1;i<row.count();i++)
		{
			Locator col=row.nth(i).locator("//td");
			String name=col.nth(0).textContent().trim();
			String mail=col.nth(1).textContent().trim();
			 mail=mail.substring(0, mail.length()-3);
			String amt=col.nth(7).textContent().trim();
			System.out.println(name+" "+mail+" "+amt);
			
			if(name.equals(DonorName) && DonorMail.contains(mail) && amt.contains(DonorAmt))
			{
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	public void clickOnReport() {
		Locator link=page.locator(Report);
		link.waitFor();
		link.click();
	}
	
	public void clickOnTributeDonationReport() {
		Locator link=page.locator(tributeDonationReport);
		link.waitFor();
		link.click();
	}
	
	public void clickOnTributeLink() {
		page.click(TributeLink);
	}
	
	public void clickOnDonorReportLink() {
		Locator link=page.locator(donorReportLink);
		link.waitFor();
		link.click();
	}
	
	public void clickOnPeerToPeerFundarasing() {
		page.click(peerToPeerFundaraisingLink);
	}
	
	public void goToDashBoard() {
		page.click(dashboardLink);
	}
	
	public void goToStakeHolders() {
		page.click(stakeHoldersLink);
	}
	
	public void goToSettingPage() {
		page.click(settingsLink);
	}
	
	public void goToEntityPage() {
		page.click(entitiesLink);
	}
	
	public void clickOnMemberReportLink() {
		page.click(memberReprtList);
	}
	
	public void clickOnVolunteerLink() {
		page.click(volunteerReportLink);
	}
	
	public void goToEventPage() {
		page.click(eventLink);
	}
	

}
