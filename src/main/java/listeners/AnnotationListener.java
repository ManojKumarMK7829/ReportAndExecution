package listeners;


import configuration.ConfigConstants;
import configuration.ConfigFileManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import pathConfig.FilePath;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AnnotationListener implements IAnnotationTransformer {

    private List<String> classes = getTestClasses();
    private static ConfigFileManager configFileManager = ConfigFileManager.getInstance(FilePath.CONFIG_PROPS);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        //Set the invocation count for tests
        System.out.println(configFileManager.getPropertyValue(ConfigConstants.INVOCATION_COUNT)+ " !!!!");
        int invocationCount = (configFileManager.getPropertyValue(ConfigConstants.INVOCATION_COUNT).isEmpty() && configFileManager.getPropertyValue(ConfigConstants.INVOCATION_COUNT).equals("")) ? 1 : Integer.parseInt(configFileManager.getPropertyValue(ConfigConstants.INVOCATION_COUNT));
        annotation.setInvocationCount(invocationCount);

        //Sets the retry analyzer
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(RetryUtil.class);
        }
    }

    private List<String> getTestClasses() {
        String classNames = ConfigFileManager.getInstance(FilePath.CONFIG_PROPS).getPropertyValue(ConfigConstants.TEST_CLASSES);
        if (classNames.isEmpty() || classNames.equalsIgnoreCase("all")) {
            return new ArrayList<>();
        } else {
            List<String> testClasses = Arrays.asList(classNames.split(",")).stream().map(String::trim).collect(Collectors.toList());
            log.info("Test classes to be executed : " + testClasses);
            return testClasses;
        }
    }
}
