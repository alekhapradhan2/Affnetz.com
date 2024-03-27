package com.qa.affnetz.InternalPages;

import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PeerToPeerFundraisingRepo {
	
	Page page;
	
	private String campSearchBox="#input-76";
	
	private String campSearchButton="//span[contains(text(),'Search')]";
	
	private String searchedCampName=".af-pt-2  h2";
	
	private String donationsSection="//div[text()='Donations']";
	
	private String donorDetailsTable=".v-data-table__wrapper tr";
	
	private String teamSection="//div[text()='Teams']";
	
	private String teamBlocks="//div[@class='af-p-6 af-bg-white']";
	
	private String teamName="//h1";
	
	private String teamTitle="//h2";
	
	private String clickTeam="//a[contains(text(),'View Team')]";
	
	
	

	
	
	
	public PeerToPeerFundraisingRepo(Page page)
	{
		this.page=page;
	}
	
	public void searchCampaign(String campName)
	{
		Locator search=page.locator(campSearchBox);
		search.waitFor();
		search.fill(campName);
	}
	
	public void clickOnSearchButton() {
		page.click(campSearchButton);
	}
	
	public void goToDonationsSection() {
		page.click(donationsSection);
	}
	
	public String getSearchedCampName() {
		Locator name=page.locator(searchedCampName).first();
		name.waitFor();
		String campName=name.textContent().trim();
		return campName;
	}
	
	public void clickOnSearchedCamp() {
		Locator name=page.locator(searchedCampName).first();
		name.waitFor();
		name.click();
	}
	
	public void isDonorDetailsShowingInThisPartocularCampaing(String fName,String mailid,String amount) throws InterruptedException {
		Thread.sleep(2000);
		boolean flag=false;
		String donorName=null;
		String donorMail=null;
		Locator rows=page.locator(donorDetailsTable);
		for(int i=1;i<rows.count();i++)
		{
			Locator cols=rows.nth(i).locator("//td");
			String fname=cols.nth(0).textContent().trim();
			String mail=cols.nth(2).textContent().trim();
			String amt=cols.nth(6).textContent().trim();
			System.out.println(fname+" "+mail+" "+amt);
			if(fname.equals(fName)&& mail.equals(mailid)&& amt.contains(amount))
			{
				donorName=fname;
				donorMail=mail;
			}
		}
		
		assertEquals(donorName, fName);
		assertEquals(donorMail, mailid);
	}
	
	public void goToTeamSection() {
		page.click(teamSection);
	}
	
	public String getTeamName() {
		Locator name=page.locator(teamName);
		name.waitFor();
		String teamName=name.textContent().trim();
		return teamName;
	}
	
	public String getTeamTitle() {
		Locator name=page.locator(teamTitle);
		name.waitFor();
		String teamTitle=name.textContent().trim();
		return teamTitle;
	}
	
	public String getAllTeamBlocks() {
		return teamBlocks;
	}
	
	public void clickRightTeam(String teamname) {
		Locator s=page.locator(teamBlocks);
		for(int i=0;i<s.count();i++)
		{
			String name=s.nth(i).locator(teamName).textContent().trim();
			
			if(name.equals(teamname))
			{
				
				s.nth(i).locator(clickTeam).click();
				break;
			}
		}
	
		
	}
	
	

}
