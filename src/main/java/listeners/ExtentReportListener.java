package listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.*;

@Slf4j
public class ExtentReportListener implements ISuiteListener, IInvokedMethodListener {

    @Override
    public void onStart(ISuite suite) {
        log.info("Report execution started");
        ExtentReportHelper.initReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("Report execution closed");
        ExtentReportHelper.flushReport();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ExtentReportHelper.startTest(method.getTestMethod().getMethodName());
            ExtentReportHelper.getTest().assignCategory(testResult.getTestClass().getRealClass().getSimpleName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String getMethodName = testResult.getMethod().getConstructorOrMethod().getName();
            System.out.println(testResult.getTestClass().getName());
            String getClassName = testResult.getTestClass().getName().split("\\.")[1];
            log.info("Test class name : " + getClassName + " Test method name : " + getMethodName + " got failed");
            ExtentReportHelper.getTest().fail("Test class name : " + getClassName + " Test method name : " + getMethodName + " got failed");
//            ExtentReportHelper.addScreenshotOnFailure(AppiumDriverManager.getAppiumDriver(), getMethodName, getClassName);
        }
    }
}
