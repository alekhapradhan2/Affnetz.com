package com.affnetz.qa.factory;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class playWrightFactory {

	static Playwright playwright;
	static Browser browser;
	static BrowserContext browserContext;
	static Page page;
	
	public static Page intitBrowser(String browserName)
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
		page.navigate("https://t1.affnetz.org/login");
		return page;	
	}
	
	public static String takeScreenshot()
	{
		String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}
}
