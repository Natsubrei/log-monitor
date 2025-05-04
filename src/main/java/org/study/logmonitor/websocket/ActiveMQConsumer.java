package org.study.logmonitor.websocket;

import jakarta.annotation.PostConstruct;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String ANALYSIS_QUEUE = "ANALYSIS_QUEUE";
    private static final String ALERT_QUEUE = "ALERT_QUEUE";

    @PostConstruct
    public void init() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination analysis = session.createQueue(ANALYSIS_QUEUE);
            Destination alert = session.createQueue(ALERT_QUEUE);

            MessageConsumer analysisConsumer = session.createConsumer(analysis);
            analysisConsumer.setMessageListener(this::handleMessage);

            MessageConsumer alertConsumer = session.createConsumer(alert);
            alertConsumer.setMessageListener(this::handleMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                LogWebSocketHandler.broadcast(text);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
