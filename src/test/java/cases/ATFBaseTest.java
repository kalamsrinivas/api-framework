package cases;

import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ATFConstants;
import utils.ATFProperties;
import utils.ATFReporter;
import utils.ATFTestDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;


public class ATFBaseTest {
    public static final String REGG = "regression", SMOKE = "smoke";
    protected static final Logger log = LoggerFactory.getLogger(ATFBaseTest.class);
    public String HOST_URL, EXCEL_FILE_PATH, JSON_FILE_PATH;
    protected String testScriptName, testGroup, displayName, env;
    private String[] testDetails = null;

    public static void AssertTrue(String expectedValue, String actualValue, String message) {
        String passMessage = "Expected " + message + " { " + expectedValue + " } Matches Actual " + message + " { " + actualValue + " } ";
        String failMessage = "Expected " + message + " { " + expectedValue + " } Not Matches Actual " + message + " { " + actualValue + " } ";
        if (!(expectedValue == actualValue)) {
            ReportFail(failMessage);
        } else {
            ReportPass(passMessage);
        }
        Assert.assertTrue(expectedValue.trim().equals(actualValue.trim()), "Expected Value: " + expectedValue + " Actual Value: " + actualValue);
    }

    public static void AssertSame(int expectedValue, int actualValue, String message) {
        String passMessage = "Expected " + message + " { " + expectedValue + " } Matches Actual " + message + " { " + actualValue + " } ";
        String failMessage = "Expected " + message + " { " + expectedValue + " } Not Matches Actual " + message + " { " + actualValue + " } ";
        if (!(expectedValue == actualValue)) {
            ReportFail(failMessage);
        } else {
            ReportPass(passMessage);
        }
        Assert.assertSame(expectedValue, actualValue, message + " Expected Value: " + expectedValue + " Actual Value: " + actualValue);
    }

    public static void AssertEquals(String expectedValue, String actualValue, String message) {
        String passMessage = "Expected " + message + " { " + expectedValue + " } Matches Actual " + message + " { " + actualValue + " } ";
        String failMessage = "Expected " + message + " { " + expectedValue + " } Not Matches Actual " + message + " { " + actualValue + " } ";
        if (!(expectedValue.equals(actualValue))) {
            ReportFail(failMessage);
        } else {
            ReportPass(passMessage);
        }
        Assert.assertEquals(expectedValue.trim(), actualValue.trim(), "Expected Value: " + expectedValue + " Actual Value: " + actualValue);
    }

    protected static void ReportPass(String message) {
        ATFReporter.ReportPass(message);
    }

    protected static void ReportFail(String message) {
        ATFReporter.ReportFail(message);
    }

    public static void ReportInfo(String message) {
        ATFReporter.ReportInfo(message);
    }

    protected static void ReportSkip(String message) {
        ATFReporter.ReportSkip(message);
    }

    protected void intSuite(Method method) {
        displayName = method.getAnnotation(ATFTestDetails.class).displayName().trim();
        testScriptName = method.getName().toString();
        testGroup = method.getDeclaredAnnotation(Test.class).groups()[0].toString();
        env = ATFConstants.ENVIRONMENT;
        RestAssured.useRelaxedHTTPSValidation();
    }

    public void startTest(String series)
    {
        testDetails = new String[]{testScriptName, displayName, testGroup, env, series};
        ATFReporter.StartTest(testDetails);
    }

    @BeforeSuite(alwaysRun = true)
    protected void beforeSuite() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(ATFConstants.AUTOMATION_LOG4J_PATH));
        } catch (IOException e) {
            System.out.println("Error: Cannot load configuration file");
        }
        EXCEL_FILE_PATH = ATFConstants.EXCELFILE_PATH;
        JSON_FILE_PATH = ATFConstants.JSONFILE_PATH;
        HOST_URL = getProperty(ATFConstants.HOST_URL);
    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod(ITestResult result) {
        if ((result.getStatus() == ITestResult.FAILURE) || ATFReporter.testFailCount > 0) {
            if (!(result.getThrowable() == null)) {
                ReportFail("" + result.getThrowable());
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            ReportSkip("Test Script: '" + testScriptName + "' Skipped");
        }
        ATFReporter.EndTest();
        System.out.println("");
    }

    @AfterSuite
    protected void afterSuite() {
        ATFReporter.PublishTestExecutionSummary();
    }

    public void skip(String message) {
        throw new SkipException(message);
    }

    protected String getProperty(String parameter) {
        return ATFProperties.GetProperty(parameter);
    }
}