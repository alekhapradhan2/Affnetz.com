package com.affnetz.qa.factory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

public class PlayWrightFactory_T1 {

	static Playwright playwright;
	static Browser browser;
	static BrowserContext browserContext;
	static Page page;
	static String Loginurl="https://t1.affnetz.org/login";
	static String donateUrl="https://t1.affnetz.org/donate";
	
	public static Page intitBrowser(String browserName,String whichPage)
	{
		playwright=Playwright.create();
		switch (browserName.toLowerCase()) {
		case "chromium":
			 browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		case "chrome":
			 browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			break;
		case "firefox":
			browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		case "sofari":
			browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		default:
			System.out.println("Please pass the valid browser name............");
			break;
		}
		
		browserContext=browser.newContext();
		page=browserContext.newPage();
		switch (whichPage.toLowerCase()) {
		case "login":
			page.navigate(Loginurl,new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
			
			break;
		case "donate":
			page.navigate(donateUrl,new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
			
			break;
		default:
			System.out.println(whichPage+"Not Exist");
			break;
		}
		return page;	
	}
	
	public static void closeBrowser() {
		page.close();
		browser.close();
		playwright.close();
	}
	
	public static String takeScreenshot()
	{
		String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}
}
