package jar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Created by Alex Storm on 12.05.2017.
 */
public class Unpack {

    public void unpackOnStart(){
        unpackFile("mqtt.properties", "/mqtt.properties");
    }

    private void unpackFile(String newFile, String resourceFile){
        File file = new File(new File(newFile).getAbsolutePath());
        if (!file.getAbsoluteFile().getParentFile().exists()){
            file.getAbsoluteFile().getParentFile().mkdirs();
        }
        if (!file.exists()) {
            InputStream link = (getClass().getResourceAsStream(resourceFile));
            try {
                Files.copy(link, file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void unpackFile(String newFile, String resourceFile, boolean setExecutable){
        File file = new File(new File(newFile).getAbsolutePath());
        if (!file.getAbsoluteFile().getParentFile().exists()){
            file.getAbsoluteFile().getParentFile().mkdirs();
        }
        if (!file.exists()) {
            InputStream link = (getClass().getResourceAsStream(resourceFile));
            try {
                Files.copy(link, file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.setExecutable(true);
        }

    }
}
