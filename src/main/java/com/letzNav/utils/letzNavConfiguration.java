package com.letzNav.utils;

import org.openqa.selenium.WebDriver;

import com.letzNav.hostApplications.clarityPPM;
import com.letzNav.modules.letzNavPlayer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class letzNavConfiguration {
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	letzNavPlayer player;
	clarityPPM clarity;
	letzNavExcelReader data;
	String component="player";
	letzNavInstaller installer;
	
	public void letzNavConfig(WebDriver driver,ExtentReports report,ExtentTest test,letzNavPlayer player,letzNavExcelReader data ) {
		
	}
	
	
}
