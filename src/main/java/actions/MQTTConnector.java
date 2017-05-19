package actions;

import com.amazonaws.services.iot.client.*;
import pubSub.TestTopicListener;

import java.util.concurrent.ThreadLocalRandom;

public class MQTTConnector {
    private String clientEndpoint = Reader.properties.get("iotEndpoint");
    private String topic = Reader.properties.get("topic");
    private String payload = Reader.properties.get("payload");

    private String awsAccessKeyId = Reader.properties.get("awsAccessKeyId");
    private String awsSecretAccessKey = Reader.properties.get("awsSecretAccessKey");
    private String sessionToken = Reader.properties.get("sessionToken");


    private final int connectionRetries = 2;
    private final int openConnectionTimeMs = 59000;

    public void mqttSubscribe(){
        String clientId = ThreadLocalRandom.current().nextInt(900, 900000 + 1)+"xe";

        AWSIotMqttClient awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        awsIotClient.setConnectionTimeout(openConnectionTimeMs);
        awsIotClient.setKeepAliveInterval(openConnectionTimeMs);
        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            awsIotClient.connect();
        } catch (AWSIotException e) {
            e.printStackTrace();
        }

        AWSIotTopic topicIoT = new TestTopicListener(topic, AWSIotQos.QOS0);

        try {
            awsIotClient.subscribe(topicIoT);
            System.out.println("Subscriber: "+awsIotClient.getConnectionStatus().toString());
        } catch (AWSIotException e) {
            e.printStackTrace();
        }

        if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)){
            sleep(openConnectionTimeMs);
            try {
                awsIotClient.unsubscribe(topic);
                awsIotClient.disconnect();
            } catch (AWSIotException e) {
                e.printStackTrace();
            }
        }
    }

    public void mqttPublish(){
        String clientId = ThreadLocalRandom.current().nextInt(900, 900000 + 1)+"Pe";

        AWSIotMqttClient awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, sessionToken);
        awsIotClient.setMaxConnectionRetries(connectionRetries);

        try {
            if(awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.DISCONNECTED)){
                awsIotClient.connect();
            }
            awsIotClient.publish(topic, payload);
            if (awsIotClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)){
                awsIotClient.disconnect();
            }
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

    public void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
