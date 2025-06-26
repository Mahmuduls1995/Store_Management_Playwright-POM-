package tests;

import basedriver.BaseDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.*;
import pages.LoginPage;
import utilities.ExtentFactory;

import java.io.IOException;

public class LoginTest extends BaseDriver {
    ExtentReports report;
    ExtentTest parentTest;
    ExtentTest childTest;

    @BeforeClass
    @Parameters({"url","browserName","headless"})
    public void openUrl(@Optional("https://testing-and-learning-hub.vercel.app/Projects/Platzi%20Fake%20Store/login.html") String url, @Optional("chrome") String browserName,
                        @Optional("false") String headless) throws InterruptedException {

        report = ExtentFactory.getInstance();
        parentTest = report.createTest("<p style=\"color:#FF6000; font-size:20px\"><b>Store Management</b></p>")
                .assignAuthor("QA TEAM").assignDevice("Windows");

        start(browserName,headless);
        launchApplication(url);
    }
    @Test
    public void loginTest() throws IOException {
        childTest = parentTest.createNode("<p style=\"color:#3E96E7; font-size:20px\"><b>Login</b></p>");
        LoginPage loginPage = new LoginPage(page,childTest);
        loginPage.login();
    }

    @AfterClass
    public void tearDown(){
        stop();
        report.flush();
    }


}
