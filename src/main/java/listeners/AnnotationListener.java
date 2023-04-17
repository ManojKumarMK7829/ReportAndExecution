package listeners;


import lombok.extern.slf4j.Slf4j;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AnnotationListener implements IAnnotationTransformer {

    private List<String> classes = getTestClasses();

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (!classes.isEmpty()) {
            String classPath[] = testMethod.getDeclaringClass().getName().split("\\.");
            if (testMethod != null && !classes.contains(classPath[classPath.length - 1])) {
                annotation.setEnabled(false);
            }
        }
    }

    private List<String> getTestClasses() {
        String classNames = "all";
        if (classNames.isEmpty() || classNames.equalsIgnoreCase("all")) {
            return new ArrayList<>();
        } else {
            List<String> testClasses = Arrays.asList(classNames.split(",")).stream().map(String::trim).collect(Collectors.toList());
            log.info("Test classes to be executed : " + testClasses);
            return testClasses;
        }
    }
}
