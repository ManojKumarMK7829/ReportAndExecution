package configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import pathConfig.FilePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ConfigFileManager extends AbstractModule {

    private static Properties properties = new Properties();
    public static Map<String, String> configFileMap = new HashMap();

    ConfigFileManager (String configFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(configFilePath);
        properties.load(fileInputStream);
    }

    @Override
    protected void configure() {
        try {
            properties.load(new FileInputStream(new File(FilePath.CONFIG_PROPS)));
            /*  Binding each and every key with its value   */
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to find the file.");
        }
    }

    public static ConfigFileManager getInstance(String configFile) {
        ConfigFileManager instance = null;
        if (instance == null) {
            try {
                instance = new ConfigFileManager(configFile);
                Enumeration keys = properties.propertyNames();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String value = (System.getenv(key) == null) ? properties.getProperty(key) : System.getenv(key);
                    configFileMap.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String getPropertyValue(String key) {
        return configFileMap.get(key);
    }
}
