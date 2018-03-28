package letzNavTestFrameWork.letzNavTestScripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import letzNavApplicationsToTest.clarityPPM;
import letzNavTestFrameWork.letzNavComponents.letzNavEditor;
import letzNavTestFrameWork.letzNavConfiguration.letzNavBrowser;
import letzNavTestFrameWork.letzNavConfiguration.letzNavExcelReader;
import letzNavTestFrameWork.letzNavConfiguration.letzNavExtentReports;
import letzNavTestFrameWork.letzNavConfiguration.letzNavInstaller;
import letzNavTestFrameWork.letzNavConfiguration.letzNavPropertiesReader;
import letzNavTestFrameWork.letzNavConfiguration.letzNavScreenShots;

public class letzNavEditorRegressionTest {
	Logger logs;
	public WebDriver driver;

	public String baseURL = letzNavPropertiesReader.getValue("url");
	public String clarityid = letzNavPropertiesReader.getValue("clarityid");
	public String claritypwd = letzNavPropertiesReader.getValue("claritypwd");
	public String editorId = letzNavPropertiesReader.getValue("editorid");
	public String editorPwd = letzNavPropertiesReader.getValue("editorpassword");
	ExtentReports report;
	ExtentTest test;
	letzNavEditor editor;
	clarityPPM clarity;
	letzNavExcelReader data;
	String component = "editor";
	letzNavInstaller installer;
	String msg;

	// This meathod initializes browser and installs extentions to browser
	@BeforeClass
	public void openBrowserAndInstallExtensions() throws Exception {
		//installer = new letzNavInstaller();
		//installer.downloadExtension("editor");
		logs = Logger.getLogger("devpinoyLogger");
		report = letzNavExtentReports.getInstance();
		driver = letzNavBrowser.startBrowser("chrome", letzNavPropertiesReader.getValue("url"), component);
		// player = new letzNavPlayer(driver);
		editor = new letzNavEditor(driver);
		clarity = new clarityPPM(driver);
		String testDataFile = System.getProperty("user.dir") + "/TestData/Book1.xlsx";
		letzNavExcelReader.setExcelFile(testDataFile, "EmployeeData");
	}

	// Data driven validateWorkFlows
	@DataProvider(name = "data")
	public Object[][] getData(Method method) {
		Object[][] testData = null;
		if (method.getName().equals("validateLaunchers")) {
			testData = letzNavExcelReader.getTestData("validateLaunchers");
			return testData;
		} else if (method.getName().equals("validateEditorFormPage")) {
			testData = letzNavExcelReader.getTestData("validateEditorFormPage");
			return testData;
		} else if (method.getName().equals("validateValidation")) {
			testData = letzNavExcelReader.getTestData("validateValidation");
			return testData;
		} else if (method.getName().equals("validateNavTips")) {
			testData = letzNavExcelReader.getTestData("validateNavTips");
			return testData;
		} else if (method.getName().equals("validateWorkFlows")) {
			testData = letzNavExcelReader.getTestData("validateWorkFlows");
			return testData;
		}

		else {
			return testData;
		}

	}

	// This test validates login to application
	@Test(priority = 1)
	public void editorLogin() {
		try {
			logs.debug("Test Clarity and Editor Login");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			test = report.startTest("Login Clarity PPM");
			test.log(LogStatus.INFO, "Validate clarity & editor login");
			clarity.loginPage(clarityid, claritypwd);
			editor.letzNavLoginToTool(editorId, editorPwd);
			clarity.goToPortlets();

		} catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			e.printStackTrace();
			logs.debug("exception in editor Login");
		}
	}

	@Test(dataProvider = "data", priority = 2, enabled = false)
	public void validateValidation(String xpath, String min, String max, String regExp, String regExpMsg,
			String custAsr, String custAsrMsg) {
		try {
			logs.debug("Validate Validation Creation");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			test = report.startTest("Validation finalization");
			editor.clickOnComponent("validations");
			editor.letzNavCreateValidation(xpath, min, max, regExp, regExpMsg, custAsr, custAsrMsg);
			
			
		} catch (Exception e) {

			driver.findElement(By.xpath("//button[@class='button mat-button']")).click();
			driver.findElement(By.xpath("//span[@class='letznav-panel-btn back']/span")).click();
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in Validation Functionality Test ");
			//e.printStackTrace();
			
				
			
		}
	}

	@Test(dataProvider = "data", priority = 3, enabled = false)
	public void validateNavTips(String xpath, String navTipTxt, String navTipName) {
		try {
			logs.debug("Validate NavTip Creation");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			test = report.startTest("NavTip finalization");
			editor.clickOnComponent("navtips");
			editor.letzNavCreateNavtip(xpath, navTipTxt, navTipName);
		} catch (Exception e) {
			driver.findElement(By.xpath("//button[@class='button mat-button']")).click();
			driver.findElement(By.xpath("//span[@class='letznav-panel-btn back']/span")).click();
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in Nav Tip Functionality");
		}

	}

	@Test(priority = 4, enabled = false)
	public void preLauncherSetUp() {
		try {
			driver.findElement(By.xpath("//badge-list/div[@class='top-bar']/div/back-btn/span/span")).click();
		} catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in pre launcher setup");
		}
	}

	@Test(priority = 5, enabled = false)
	public void preLauncherTest() {
		try {
			editor.clickOnComponent("launchers");
		} catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in dashboard clicking on launcher");
		}

	}

	@Test(dataProvider = "data", priority = 6, dependsOnMethods = "preLauncherTest", enabled = false)
	public void validateLaunchers(String elementXpath, String launcherOption, String launcherTxt, String launcherTitle,
			String linkUrl) {
		try {
			logs.debug("Validate launchers Creation");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			test = report.startTest("Launcher finalization");
			editor.letzNavCreateLauncher(elementXpath, launcherOption, launcherTxt, launcherTitle, linkUrl);
		} catch (Exception e) {
			driver.findElement(By.xpath("//button[@class='button mat-button']")).click();
			driver.findElement(By.xpath("//span[@class='letznav-panel-btn back']/span")).click();
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in validating launchers");
		}

	}

	@Test(priority = 7, enabled = false)
	public void preWorkFlowSetUp() {
		try {
		Assert.assertTrue(
				driver.findElement(By.xpath("//back-btn/span[@class='letznav-panel-btn back']")).isDisplayed());

		driver.findElement(By.xpath("//badge-list/div[@class='top-bar']/div/back-btn/span/span")).click();
		driver.findElement(By.xpath("//badge-form-list/div[@class='top-bar']/div/back-btn/span/span")).click();
		}
		catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in validating pre workflow setup");
		}
	}
		

	@Test(dependsOnMethods = "editorLogin", priority = 8, enabled = false)
	public void preWorkFlowTest() {
		try {
			Assert.assertTrue(driver.findElement(By.xpath("//bottom-bar")).isDisplayed());
			editor.clickOnComponent("workflows");
			editor.createNewWorkFlow(" Test Automation ", " AutomateWorkFlow");
		} catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			logs.debug("exception in validating pre work flow test ");
		}

	}

	@Test(dataProvider = "data", priority = 9, enabled = false)
	public void validateWorkFlows(String elementXpath, String desc, String advanceOn, String balloonLoc,
			String markEntryPt) {
		try {
			logs.debug("Validate launchers Creation");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			test = report.startTest("Launcher finalization");
			editor.createNewWorkFlow(elementXpath, desc, advanceOn, balloonLoc, markEntryPt);

		} catch (Exception e) {
			msg = e.getMessage();
			test.log(LogStatus.ERROR, msg);
			e.printStackTrace();
			logs.debug("exception in validating workflow creation ");
		}

	}

	@Test(priority = 10, enabled = false)
	public void postWorkFlowCreation() {
		
		driver.findElement(By.xpath("//flow-view/step-view/div/div/span[@aria-haspopup='true']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("$('button[class=mat-menu-item] div').click();");
		driver.findElement(By.xpath("//div[@class='dialog-buttons']/button[1]/span")).click();
		driver.findElement(By.xpath("//back-btn[@backto='admin/flows']/span")).click();
		driver.findElement(By.xpath("//back-btn[@backto='admin']/span")).click();
	}
	
	@Test(priority = 11, enabled = true)
	public void testAnnouncements() {
		try {
		driver.findElement(By.cssSelector("li.home-navigation__announcements")).click();
		editor.addNewAnnouncement("TestAutomate","Automation","okbutton","elementPresence");
		driver.findElement(By.cssSelector("back-btn span[class='letznav-panel-btn back']")).click();
		driver.findElement(By.xpath("//back-btn[@backto='admin']/span")).click();
		boolean result = driver.findElement(By.cssSelector("ul.home-navigation")).isDisplayed();
		Assert.assertTrue(result);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		logs.debug("Test Execution Started");
	}

	// This method captures screenshot after testrun
	@AfterMethod
	public void tearDown(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = letzNavScreenShots.takeScreenshot(driver, testResult.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Test Failed and Captured ScreenShot", imagePath);
			logs.debug("Screen Shot Captured on Failure");
		}
		String path = letzNavScreenShots.takeScreenshot(driver, testResult.getName());
		String imagePath = test.addScreenCapture(path);
		test.log(LogStatus.PASS, "Test Case Passed",imagePath);
		logs.debug("Test Case Finished");
		report.endTest(test);
		report.flush();
	}

	@AfterClass // This meathod will close browser
	public void afterClass() throws IOException {
		//installer.clearExtensionResources();
		driver.quit();
		

	}
}
