package com.timwe.simple_api.api_loop.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


@Service
public class RabbitMQProducerService {
	
	private Channel channel;
	ConnectionFactory factory;
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
	public RabbitMQProducerService() {

		factory = new ConnectionFactory();

		factory.setHost(host != null ? host : "10.244.2.54");
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
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void produce() throws IOException, TimeoutException {

		System.out.println(
				factory.getUsername() + " | " + 
						factory.getPassword() + " | " + 
								factory.getHost() + " | " + 
									factory.getPort() + " | " + 
										factory.getVirtualHost() );

		connection = factory.newConnection(); 
		channel = connection.createChannel();
		
		String message = "";

		for (int i = 0; i < 60; i++) {
			message = "UUID = " + UUID.randomUUID() + " TEST QUEUE";
			channel.basicPublish("", "TEST-QUEUE", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}

	}
	
}