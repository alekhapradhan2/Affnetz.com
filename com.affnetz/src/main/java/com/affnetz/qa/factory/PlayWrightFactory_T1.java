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
import com.qa.affnetz.Publicapages.LoginPageRepo_T1;

public class PlayWrightFactory_T1 {

	static Playwright playwright;
	static Browser browser;
	static BrowserContext browserContext;
	static Page page;
	
	static LoginPageRepo_T1 lp;
	static Properties prop;
	
	public static Page intitBrowser(String whichPage) throws IOException
	{
		
		playwright=Playwright.create();
		

		switch (initProp().getProperty("browser").toLowerCase()) {
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
	
	
	public static Properties initProp() throws IOException {
		FileInputStream file=new FileInputStream("src/test/resources/config.properties");
		prop=new Properties();
		prop.load(file);
		
		return prop;
	}
	
	@Test
	public static void login() throws IOException {
		lp=new LoginPageRepo_T1(page);
		lp.doLogin(initProp().getProperty("userName"), initProp().getProperty("password"));
		
	
	}
}
