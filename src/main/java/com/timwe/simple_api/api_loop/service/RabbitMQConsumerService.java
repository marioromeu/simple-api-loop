package com.timwe.simple_api.api_loop.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


@Service
public class RabbitMQConsumerService {
	
	private Channel channel;	
	private Connection connection;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.username}")
	private String username;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
	
	@Value("${spring.rabbitmq.port}")
	private String port;

	/**
	 * 
	 */
	public RabbitMQConsumerService() {

		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost(host != null ? host : "10.128.10.215");
		factory.setPort(port != null ? Integer.parseInt(port) : 5672);
		factory.setUsername(username != null ? username : "guest");
		factory.setPassword(password != null ? password : "guest");
		factory.setVirtualHost(virtualHost != null ? virtualHost : "storage-collector-dev");
		
		try {
			
			System.out.println("BEFORE HOST="+factory.getHost());
			System.out.println("PORT="+factory.getPort());
			System.out.println("USER="+factory.getUsername());
			System.out.println("PASS="+factory.getPassword());
			System.out.println("VS="+factory.getVirtualHost());
			
	        System.out.println("MEU-ip="+InetAddress.getLocalHost().getHostAddress());
	        System.out.println("MEU-hostname="+InetAddress.getLocalHost().getHostName());

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