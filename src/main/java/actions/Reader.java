package actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Alex Storm on 19.05.2017.
 */
public class Reader {
    private Properties prop = new Properties();
    private InputStream input = null;
    static HashMap<String, String> properties = null;
    private static final String propertiesFile = "mqtt.properties";


    public HashMap<String, String> readProperties() {
        properties = new HashMap<>();

        try {
            input = new FileInputStream(propertiesFile);

            // load a propertiesFile file
            prop.load(input);

            // get the propertiesFile value and save them in Map
            properties.put("iotEndpoint", prop.getProperty("iotEndpoint"));
            properties.put("topic", prop.getProperty("topic"));
            properties.put("payload", prop.getProperty("payload"));
            properties.put("awsAccessKeyId", prop.getProperty("awsAccessKeyId"));
            properties.put("awsSecretAccessKey", prop.getProperty("awsSecretAccessKey"));
            if(prop.contains("sessionToken")) {
                properties.put("sessionToken", prop.getProperty("sessionToken"));
            }





        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

}
