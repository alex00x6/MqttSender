package jar;

import actions.MQTTConnector;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Storm on 16.05.2017.
 */
public class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-publish", "-p" }, echoInput = true)
    private boolean publish = false;

    @Parameter(names = { "-subscribe", "-s" }, echoInput = true)
    private boolean subscribe = false;

    public void proceed(){
        if (publish){
            MQTTConnector mqttConnector = new MQTTConnector();
            mqttConnector.mqttPublish();
        }
        if (subscribe){
            MQTTConnector mqttConnector = new MQTTConnector();
            mqttConnector.mqttSubscribe();
        }
    }

    public void print() {
        System.out.println("Publish: "+publish);
        System.out.println("Subscribe: "+subscribe);
    }
}
