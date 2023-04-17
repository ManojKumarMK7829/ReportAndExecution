package listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.*;

@Slf4j
public class Listeners implements ISuiteListener, ITestListener, IInvokedMethodListener {


    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            log.info(result.getName() + " test case is Passed.");
        }
    }
    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.info("Test case failed " + result.getName());
            log.info("Test case failed because of " + result.getThrowable().getStackTrace());
//            String screenshotFilePath = null;
//            try {
//                screenshotFilePath = ScreenshotUtil.takeScreenshotAtEndOfTest(result.getName());
//                //BaseTest.extentTest.log(Status.FAIL,"Path of the screen shot -"+screenshotFilePath);
//                //BaseTest.extentTest.log(Status.FAIL, "Test case is failed at " + BaseTest.extentTest.addScreenCaptureFromPath(screenshotFilePath));
//                ExtentReportHelper.getTest().addScreenCaptureFromPath(screenshotFilePath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getStatus() == ITestResult.SKIP) {
            log.info(result.getName()+" is skipped.");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    @Override
    public void onStart(ISuite iSuite) {

    }

    @Override
    public void onFinish(ISuite iSuite) {

    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }
}
