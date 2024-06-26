package com.affnetz.qa.factory;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;
import com.qa.affnetz.Publicapages.LoginPageRepo;

public class PlayWrightFactory {

	static Playwright playwright;
	static Browser browser;
	static BrowserContext browserContext;
	static Page page;
	
	static LoginPageRepo lp;
	static Properties prop;
	
	static boolean flag=false;	
	static boolean screeningProcessForDonor=false;
	static boolean screeingProcessForMembers=false;
	public static Page intitBrowser(String whichPage) throws IOException
	{
		
		playwright=Playwright.create();
		

		switch (initProp().getProperty("browser").toLowerCase()) {
		case "chromium":
			 browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(flag));
			break;
		case "chrome":
			 browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(flag));
			break;
		case "firefox":
			browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(flag));
			break;
		case "sofari":
			browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(flag));
			break;

		default:
			System.out.println("Please pass the valid browser name............");
			break;
		}
		
		browserContext=browser.newContext();
		page=browserContext.newPage();
		switch (whichPage.toLowerCase()) {
		case "login":
			page.navigate(initProp().getProperty("loginUrl"),new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
			
			break;
		case "donate":
			page.navigate(initProp().getProperty("donateUrl"),new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
			
			break;
		default:
			System.out.println(whichPage+"Not Exist");
			break;
		}
		return page;	
	}
	
	
	public static String takeScreenshot()
	{
		String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}
	
	
	public static Properties initProp() throws IOException {
		FileInputStream file=new FileInputStream("src/test/resources/config.properties");
		prop=new Properties();
		prop.load(file);
		
		return prop;
	}
	
	
	public static void login() throws IOException {
		lp=new LoginPageRepo(page);
		lp.doLogin(initProp().getProperty("userName"), initProp().getProperty("password"));
		
	
	}
	
	public static boolean screeningProcess_Donor() {
		return screeningProcessForDonor;
	}
	
	public static boolean screeningProcess_Member()
	{
		return screeingProcessForMembers;
	}
}
