package Orange_HRM.oHRM.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ExtFileReader {
	 private static Properties properties = new Properties();

	    static {
	        loadProperties();
	    }

	    private static void loadProperties() {
	        try (InputStream globalTestdataStream = ExtFileReader.class.getClassLoader()
	                .getResourceAsStream("Global_Configuration/globalTestdata.properties");
	        		InputStream globalKeysStream = ExtFileReader.class.getClassLoader()
	    	                .getResourceAsStream("Global_Configuration/Config.properties");
	        		InputStream localKeysStream = ExtFileReader.class.getClassLoader()
	    	                .getResourceAsStream("Local_Testdata/userdata.properties")) {

	            if (globalTestdataStream != null) {
	                properties.load(globalTestdataStream);
	            } else {
	                throw new FileNotFoundException("Global_Configuration/globalTestdata.properties not found in classpath");
	            }

	            if (globalKeysStream != null) {
	                properties.load(globalKeysStream);
	            } else {
	                throw new FileNotFoundException("Global_Configuration/Config.properties not found in classpath");
	            }
	            if (localKeysStream != null) {
	                properties.load(localKeysStream);
	            } else {
	                throw new FileNotFoundException("Local_Testdata/userdata.properties not found in classpath");
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to load properties files.");
	        }
	    }

	    public static String getProperty(String key) {
	        return properties.getProperty(key);
	    }

}
