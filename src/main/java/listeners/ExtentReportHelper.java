package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Slf4j
public class ExtentReportHelper {

    /*      Initialization of Extent report     */
    private static ExtentReports extentReports;
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
    private static String reportFileLocation = reportFilepath + fileSeperator + "report.html";
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String screenshotsPath = System.getProperty("user.dir") + "/screenshots";

    public static ExtentReports initReport() {
        if(extentReports == null)
            createInstance();
        try {
//            CleanDirectory.deleteOlderFilesInDirectory(screenshotsPath);
        } catch (Exception e) {
            log.error("Not able to clean the screenshots directory " + e.getMessage());
            e.printStackTrace();
        }

        return extentReports;
    }

    private static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);
        String gitBranchName = getCurrentGitBranch();

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Test Report: " + "");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        htmlReporter.config().setReportName("Test Report: " + "");

        extentReports = new ExtentReports();

        if (gitBranchName != null)
            extentReports.setSystemInfo("Git Branch", gitBranchName);
        extentReports.attachReporter(htmlReporter);
        return extentReports;
    }

    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                return reportFileLocation;
            } else {
                return System.getProperty("user.dir");
            }
        }
        return reportFileLocation;
    }

    private static String getCurrentGitBranch() {
        String gitBranchName = null;
        try {
            Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            gitBranchName = reader.readLine();
            System.out.println(gitBranchName);
            process.destroy();
            reader.close();
        } catch (Exception e) {
            log.error("Error while executing Command: [git rev-parse --abbrev-ref HEAD] | Exception: " + e.getMessage());
        }
        return gitBranchName;
    }

    /*      Closure of Report   */
    public static void flushReport() {
        extentReports.flush();
        extentReports = null;
    }

    public static void startTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

//    public static void addScreenshotOnFailure(AppiumDriver driver, String testname, String testClassName) {
//        try {
//            extentTest.get().fail("Screenshot Of Failed Screen", MediaEntityBuilder.createScreenCaptureFromBase64String(driver.getScreenshotAs(OutputType.BASE64)).build());
//        } catch (Exception e) {
//            log.error("Error While Taking Screenshot: " + e.getMessage());
//            updateResultInReport(Status.FAIL, null);
//        }
//    }
//
//    public static void updateResultInReport(Status status, String comment) {
//        comment = comment == null ? "" : comment;
//        extentTest.get().log(status, comment);
//    }
}
