package com.timwe.simple_api.api_loop.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Service
public class RabbitMQConsumer {

	private Connection connection;
	private Channel channel;
	
	public RabbitMQConsumer() {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare("TEST-QUEUE", false, false, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	}

	@Scheduled(fixedDelay = 1000)
	public void consume() throws IOException {
	
		System.out.println("Iniciando consumo");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		    String message = new String(delivery.getBody(), "UTF-8");
		    System.out.println(" [x] Received '" + message + "'");
		};

		channel.basicConsume("TEST-QUEUE", true, deliverCallback, consumerTag -> { });

		System.out.println("Fim do consumo");
		
	}
	
}
