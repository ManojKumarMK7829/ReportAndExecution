package tests.Executor;

import listeners.ExtentReportListener;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.xml.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TestRunner {

    public static void main(String[] args) throws Exception {

        String includedGroups = "basics"; // replace with your included groups

        List<XmlClass> classes = getXmlClassesFromIncludedGroups();

        XmlSuite suite = new XmlSuite();
        suite.setName("Dynamic TestNG Suite");
        getListners().forEach(suite::addListener);


        boolean execution = false;

        if (execution) {    // true if parallel is required from file utility
            if (true) { // set true for classes
                log.info("Parallel Classes");
                suite.setParallel(XmlSuite.ParallelMode.CLASSES);
            } else if (true) {// set true for methods
                log.info("Parallel methods");
                suite.setParallel(XmlSuite.ParallelMode.METHODS);
            } else {
                throw new RuntimeException("Not Supported PARALLEL_EXECUTION_TYPE: "+ "");
            }
            //xmlTest.setThreadCount(Integer.parseInt(configFileManager.getProperty(Config.THREAD_COUNT) == null ? "1" : configFileManager.getProperty(Config.THREAD_COUNT)));
            suite.setThreadCount(20); //deviceCount is the number to set thread count
        }

        XmlTest test = new XmlTest(suite);
        test.setName("Dynamic TestNG Test");
        test.setIncludedGroups(getTestGroups(includedGroups));
        test.setPreserveOrder(true);
        test.setXmlClasses(classes);

        String xmlString = suite.toXml();
        System.out.println(xmlString);

        List<XmlSuite> suites = new LinkedList<>();
        suites.add(suite);

        TestNG testng = new TestNG();
        testng.setUseDefaultListeners(false);
        testng.setXmlSuites(suites);
        testng.run();

        boolean isTestExecutionFailed = testng.hasFailure();
        if (isTestExecutionFailed)
            throw new RuntimeException("Test Execution Failed");
    }

    public static List<XmlClass> getXmlClassesFromIncludedGroups() throws Exception {

        // You can modify this method to retrieve XmlClasses based on your logic
        // This example code simply returns a hard-coded list of XmlClasses
        List<XmlClass> classes = new LinkedList<>();

        getAllTestcaseClass("tests")
                .stream()
                .map(XmlClass::new)
                .forEach(classes::add);

        return classes;
    }

    private static Set<Class<?>> getAllTestcaseClass(String testcasePackagePath) {

        final ConfigurationBuilder config = new ConfigurationBuilder()
                .setScanners(new ResourcesScanner(), new SubTypesScanner(false))
                .setUrls(ClasspathHelper.forPackage(testcasePackagePath))
                .filterInputsBy(new FilterBuilder().includePackage(testcasePackagePath));

        final Reflections reflect = new Reflections(config);
        Set<Class<?>> classes = reflect.getSubTypesOf(Object.class).stream()
                .collect(Collectors.toSet());
//        for (Class<?> cls : classes) {
//            System.out.println(cls.getName());
//        }
        return classes;
    }

    private static List<String> getTestGroups(String grp) {
        String testGroups = grp;     // Get all group like regression, smoke
        if (!testGroups.isEmpty()) {
            log.info("Test groups to be executed : " + testGroups);
            return Arrays.stream(testGroups.split(",")).map(String::trim).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }

    private static ArrayList<String> getListners() {
        ArrayList<String> listnerList = new ArrayList<>();
        listnerList.add(ExtentReportListener.class.getName());
        return listnerList;
    }
}

