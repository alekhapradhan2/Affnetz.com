package com.affnetz.qa.factory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class listenres implements ITestListener {
	private static final String outpur_folder="./build/";
	private static final String file_name="testExecution.png";
	private static ExtentReports extent=init();
	public static ThreadLocal<ExtentTest> test= new ThreadLocal<ExtentTest>();
	private static ExtentReports exetentReport;
	
	private static ExtentReports init() {
		Path path=Paths.get(outpur_folder);
		if(!Files.exists(path))
		{
			try {
				Files.createDirectories(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		exetentReport=new ExtentReports();
		ExtentSparkReporter reporter=new ExtentSparkReporter(outpur_folder+file_name);
		reporter.config().setReportName("Affnetz Test Results");
		exetentReport.attachReporter(reporter);
		exetentReport.setSystemInfo("System", "Window");
		exetentReport.setSystemInfo("Author", "Alekha Pradhan");
		
		return exetentReport;
		
		
	}
	
	@Override
	public synchronized void onStart(ITestContext contrxt)
	{
		System.out.println("Test Suite Started");
	}
	
	@Override
	public synchronized void onFinish(ITestContext context)
	{
		System.out.println("Test Suite Started");
		extent.flush();
		test.remove();
	}
	
	@Override
	public synchronized void onTestStart(ITestResult result)
	{
		String methodName=result.getMethod().getMethodName();
		String qualifieddName=result.getMethod().getQualifiedName();
		int last=qualifieddName.lastIndexOf(".");
		int mid=qualifieddName.substring(0,last).lastIndexOf(".");
		String className=qualifieddName.substring(mid+1,last);
		System.out.println(methodName+ " started");
		ExtentTest extentTest=extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		extentTest.assignAuthor(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getEndMillis()));
	}
	
	public synchronized void onTestSuccess(ITestResult result)
	{
		System.out.println(result.getMethod().getMethodName()+" passed");
		test.get().pass("Test passed");
//		test.get().pass(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(playWrightFactory.takeScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
		
		
	}
	
	public synchronized void onTestFailure(ITestResult result)
	{
		System.out.println(result.getMethod().getMethodName()+" failed");
		test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(playWrightFactory.takeScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));

	}
	
	public synchronized void onTestSkipped(ITestResult result)
	{
		System.out.println(result.getMethod().getMethodName()+" skipped");
		test.get().skip(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(playWrightFactory.takeScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	
	private Date getTime(long mills)
	{
		Calendar calender=Calendar.getInstance();
		calender.setTimeInMillis(mills);
		return calender.getTime();
	}

}
