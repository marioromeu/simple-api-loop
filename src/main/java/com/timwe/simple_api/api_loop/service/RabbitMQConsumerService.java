package com.timwe.simple_api.api_loop.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


@Service
public class RabbitMQConsumerService {
	
	private Channel channel;	
	private Connection connection;
	
	public RabbitMQConsumerService() {
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
	}
	
	public void startConsumer() throws IOException {	
		System.out.println("Iniciando consumidor");
		channel.basicConsume("TEST-QUEUE", true, buildDeliverCallback(), consumerTag -> {
		});
		System.out.println("consumidor iniciado");		
	}

	public DeliverCallback buildDeliverCallback() {
		 return (consumerTag, delivery) -> {
		    String message = new String(delivery.getBody(), "UTF-8");
		    
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    
		    System.out.println(" [x] Received '" + message + "'");
		};
	}
	
}