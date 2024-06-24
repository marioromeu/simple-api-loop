package com.timwe.simple_api.api_loop.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Service
public class RabbitMQConsumer {

	@Value("${hostname}")
	private String hostName;
	private String queueName = "TEST-QUEUE";
	private Connection connection;
	private Channel channel;
	
	public RabbitMQConsumer() {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(queueName, false, false, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	}

	@Scheduled(fixedDelay = 1000)
	public void consume() throws IOException {
	
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		    String message = new String(delivery.getBody(), "UTF-8");
		    System.out.println(" [x] Received '" + message + "'");
		};

		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

	}
	
}
