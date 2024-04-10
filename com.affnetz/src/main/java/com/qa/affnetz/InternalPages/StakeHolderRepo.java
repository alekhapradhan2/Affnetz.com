package com.qa.affnetz.InternalPages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import freemarker.core.StringArraySequence;

public class StakeHolderRepo {
	
	Page page;
	
	private String searchUser="#input-76";
	
	private String userBox="//h2";
	
	private String userType=".v-chip__content";

	private String userProfileName="//h1";
	
	private String createButton="//span[contains(text(),'Create')]";
	
	private String importBulkButton="//span[contains(text(),'Import Bulk')]";
	
	//--------------UserProfile Section Repo---------------------//
	
	private String userProfileMail="(//a[contains(@href,'mailto:')])[1]";
	
	private String manualDonationButton="//span[contains(text(),'Manual Donation')]";
	
	private String module=".v-select__selections";
	
	private String list="(//div[@role='listbox'])[5]";
	
	private String moduleTitleList="(//div[@role='listbox'])[6]";
	
	private String names="//div[contains(@id,'list-item-')]";
	
	private String moduleTitle="//input[@name='link_id']";
	
	private String proceedButton="//span[contains(text(),'Proceed')]";
	
	private String donorDetailsTable=".v-data-table__wrapper tr";
	
	private String actionList1="(//div[contains(@class,'v-list v-sheet')])[7]";
	
	private String actionList2="(//div[contains(@class,'v-list v-sheet')])[8]";
	
	private String allAction="//*[contains(@id,'list-item') ]";
	
	private String allUserTypeList="//div[contains(@class,'v-tabs-bar__conten')]";
	
	private String userTypeName="//div[contains(@class,'af-ml-3 v-tab')]";
	
	private String amountInDonorDetailsPage="(//p[@class='data-display'])[1]";
	
	private String isManualDonation="(//p[@class='data-display'])[4]";
	
	private String paymentModeInDonorDetailsPage="(//p[@class='data-display'])[6]";
	
	private String donorNameInDonorDetailsPage="(//p[@class='data-display'])[7]";
	
	private String donorMailIdInDonorDetailsPage="(//p[@class='data-display'])[8]";
	
	private String campaignNameInDonorDetailsPage="(//p[@class='data-display'])[11]";
	
	private String tributeRadioButton="//div[@class='v-input--selection-controls__ripple']";
	
	private String editButton="//a[contains(@href,'https://t1.affnetz.org/stakeholder/edit')]";
	
	private String deletButton="//span[text()='delete']";
	

	//-------------------------------------------User Creation Repo------------------------------------------------------------//
	
	private String maildi="#input-75";
	
	private String firstName="#input-79";
	
	private String lastName="#input-87";
	
	private String userTypeInput="#input-91";
	
	private String userTypeList="#list-91";
	
	private String allUserType="//div[contains(@id,'list-item-111')]";
	
	private String saveButton="//span[contains(text(),'Save')]";
	
	private String userCreationConfirmMessage="//div[@id='swal2-html-container']";
	
	//--------------------------------------------------Edit Page Repo--------------------------------------------------------//
	
	private String fname="#first_name";
	
	private String lname="#last_name";
	
	private String EditsaveButton="//span[contains(text(),'save')]";
	
	
	public StakeHolderRepo(Page page)
	{
		this.page=page;
		
	}
	
	public void chooseUserType() {
		Locator list=page.locator(allUserTypeList);
		Locator type=list.locator(userTypeName);
		Random rm=new Random();
		int x=rm.nextInt(15);
		type.nth(x).click();
	}
	public void searchUser(String mail) throws InterruptedException {
		Thread.sleep(3000);
		Locator input=page.locator(searchUser).first();
		input.waitFor();
		input.fill(mail);
	}
	
	public void isUserCreated(String userName) throws InterruptedException {
		
		Locator name=page.locator(userBox).first();
		name.waitFor();
		Thread.sleep(2000);
		String username=name.textContent().trim();
		assertEquals(userName, username);
	}
	
	public void clickOnSearchedUser() throws InterruptedException {
		Thread.sleep(2000);
		Locator link=page.locator(userBox).first();
		link.waitFor();
		link.click();
		
	}
	
	public void verifyUserDetails(String UserType,String Name,String userMail) {
		Locator type=page.locator(userType).first();
		type.waitFor();
		String usertype=type.textContent().trim();
		
		Locator name=page.locator(userProfileName).first();
		name.waitFor();
		String userName=name.textContent().trim();
		
		Locator mail=page.locator(userProfileMail).first();
		mail.waitFor();
		String mailId=mail.textContent().trim();
		
		assertEquals(usertype, UserType);
		assertEquals(userName, Name);
		assertEquals(mailId, userMail);
		
		
	}
	
	public String getUserType() {
		Locator type=page.locator(userType).first();
		type.waitFor();
		String usertype=type.textContent().trim();
		return usertype;
	}
	
	public String getUserName() {
		Locator name=page.locator(userProfileName).first();
		name.waitFor();
		String userName=name.textContent().trim();
		return userName;
	}
	
	public String getUserMail() {
		Locator mail=page.locator(userProfileMail).first();
		mail.waitFor();
		String mailId=mail.textContent().trim();
		return mailId;
	}
	
	public void clickOnManualDonationButton() {
		page.click(manualDonationButton);
	}
	
	public void chooseCampaignAsModule() throws InterruptedException {
		page.click(module);
		Thread.sleep(1000);
		Locator mod=page.locator(list);
		Locator allModule=mod.locator(names);
		allModule.first().click();
	}
	public void chooseTributeAsModule() throws InterruptedException
	{
		page.click(module);
		Thread.sleep(1000);
		Locator mod=page.locator(list);
		Locator allModule=mod.locator(names);
		allModule.nth(1).click();
	}
	
	public String getCampaignNameselectOneCampaign() throws InterruptedException
	{
//		Thread.sleep(3000);
		page.click(moduleTitle);
		Thread.sleep(2000);
		Locator mod=page.locator(moduleTitleList);
		Locator allModule=mod.locator(names);
		Random rm=new Random();
		int x=rm.nextInt(9);
		String campaignName=allModule.nth(x).textContent().trim();
		allModule.nth(x).click();
		return campaignName;	
	}
	
	public String getTributeNameAndSelectOneTribute() throws InterruptedException {
		page.click(moduleTitle);
		Thread.sleep(2000);
		Locator mod=page.locator(moduleTitleList);
		Locator allModule=mod.locator(names);
		Random rm=new Random();
		int x=rm.nextInt(9);
		String tributeName=allModule.nth(x).textContent().trim();
		allModule.nth(x).click();
		return tributeName;	
	}
	
	public void clickOnProceedButton() {
		page.click(proceedButton);
	}
	
//	public void goToThatDonation() {
//		Locator row=page.locator(donorDetailsTable);
//		Locator col=row.nth(1).locator("//td");
//		col.nth(6).click();
//		Locator action=page.locator(actionList1);
//		Locator names=action.locator(allAction);
//		names.first().click();
//		
//	}
	
	/**  Pass these values as you required 
	 * stakeholder campaign
	 * 	stakeholder tribute
	 * 	tribute 
	 * 	campaign**/
	
	
	public void goToThatDonation(String Module) throws InterruptedException {
		Locator row=page.locator(donorDetailsTable);
		Locator col=row.nth(1).locator("//td");
		Thread.sleep(1000);
		switch (Module.toLowerCase()) {
		case "stakeholder campaign":
			col.nth(6).click();
			Locator action=page.locator(actionList1);
			Locator names=action.locator(allAction);
			names.first().click();
			break;
		case "stakeholder tribute":
			col.nth(7).click();
			Locator action1=page.locator(actionList1);
			Locator names1=action1.locator(allAction);
			names1.first().click();
			break;
		case "tribute":
			col.nth(7).click();
			Locator action2=page.locator(actionList2);
			Locator names2=action2.locator(allAction);
			names2.first().click();
			break;
		case "campaign":
			col.nth(6).click();
			Locator action3=page.locator(actionList2);
			Locator names3=action3.locator(allAction);
			names3.first().click();
			break;

		default:
			break;
		}
	
		
	}
	
	public void verifyDonorDetails(String UserName,String PaymentMode,String UserMail,String Campname) {
		
		
		Locator name=page.locator(isManualDonation);
		name.waitFor();
		String isDonation=name.textContent().trim();
		Locator mode=page.locator(paymentModeInDonorDetailsPage);
		mode.waitFor();
		String pMode=mode.textContent().trim();
		Locator donorName=page.locator(donorNameInDonorDetailsPage);
		donorName.waitFor();
		String Dname=donorName.textContent().trim();
		Locator DMail=page.locator(donorMailIdInDonorDetailsPage);
		DMail.waitFor();
		String donorMail=DMail.textContent().trim();
		Locator CampName=page.locator(campaignNameInDonorDetailsPage);
		CampName.waitFor();
		String campaignName=CampName.textContent().trim();
		
		assertEquals(isDonation, "Yes");
		assertEquals(pMode, PaymentMode);
		assertEquals(Dname, UserName);
		assertEquals(donorMail, UserMail);
		assertEquals(campaignName, Campname);
	
	}

	public  void clickOnTributeRadioButtion() {
//		String id=page.locator(tributeRadioButton).getAttribute("id");
//		System.out.println(id);
		page.click(tributeRadioButton);
		
		
	}
	
	public Locator getEditBUtton() {
		Locator edit=page.locator(editButton);
		return edit;
	}
	
	public void clickOnEditButton() {
		page.click(editButton);
	}
	
	public void clickOnCreateButton() {
		page.click(createButton);
	}
	
	public void clickOnImportButton() {
		page.click(importBulkButton);
	}
	
	public Locator getCreateButton() {
		Locator create=page.locator(createButton);
		return create;
	}
	
	public Locator getImportButton() {
		Locator importButton=page.locator(importBulkButton);
		return importButton;
	}
	
	public boolean isUserProfileOpen() {
		Locator edit=page.locator(editButton).first();
		edit.waitFor();
		Locator delete=page.locator(deletButton).first();
		delete.waitFor();
		boolean flag=false;
		if(edit.isVisible() && delete.isVisible())
		{
			flag=true;
		}
		return flag;
		
	}
	
	//------------------------------------------------UserCraetion Methods--------------------------------------------//
	
	public String fillAllUserDetails(String userMail,String userFirstName,String userLastName) throws InterruptedException {
		page.fill(maildi, userMail);
		page.fill(firstName, userFirstName);
		page.fill(lastName, userLastName);
		page.click(userTypeInput);
		Thread.sleep(1000);
		Locator list=page.locator(userTypeList);
		Locator names=list.locator(allUserType);
		Random r=new Random();
		int x=r.nextInt(10);
		String userType=names.nth(x).textContent().trim();
		names.nth(x).click();
		return userType;
	}
	public void clickOnSaveButton()
	{
		page.click(saveButton);
	}
	
	public String getCofirmMsg() {
		Locator l=page.locator(userCreationConfirmMessage);
		l.waitFor();
		String msg=l.textContent().trim();
		return msg;
	}
	
	public boolean isUserCreated() {
		Locator edit=page.locator(editButton).first();
		edit.waitFor();
		Locator delete=page.locator(deletButton).first();
		delete.waitFor();
		boolean flag=false;
		if(edit.isVisible() && delete.isVisible())
		{
			flag=true;
		}
		return flag;
		
	}
	
	public String existingMailIds() {
		String []mail= {"engineering+kingasda@affnetz.com",
				"engineering+few@affnetz.com",
				"engineering+march1805@affnetz.com",
				"engineering+schl1@affnetz.com"};
		Random m=new Random();
		int y=m.nextInt(3);
		return mail[y];
	}
	
	public void verifyCreateduserDetails(String usertype,String userName,String mailId) {
		String type=page.locator(userType).textContent().trim();
		String name=page.locator(userProfileName).textContent().trim();
		String mail=page.locator(userProfileMail).textContent().trim();
	
		assertEquals(name, userName);
		assertEquals(mail, mailId);
		if(type.contains(usertype))
		{
			assertTrue(true, "Valid");
		}else {
			assertTrue(false, "UserTyep Not Matched");
		}
	}
	
	//------------------------------------------------------Edit Page Methods--------------------------------------------------//
	
	public void editUserDetails(String firstName,String lastName) {
		page.locator(fname).clear();
		page.fill(fname, firstName);
		page.locator(lname).clear();
		page.fill(lname, lastName);
	}
	
	public void clickOnEditSaveButton() {
		page.click(EditsaveButton);
	}
	

	
	
	
	

}
