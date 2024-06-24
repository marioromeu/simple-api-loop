package com.timwe.simple_api.api_loop.service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class ApiService {

	Logger log = Logger.getLogger(ApiService.class.getName());
	
	public void produce() throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();

		System.out.println(
				factory.getUsername() + " | " + 
						factory.getPassword() + " | " + 
								factory.getHost() + " | " + 
									factory.getPort() + " | " + 
										factory.getVirtualHost() );
		
		try (
				Connection connection = factory.newConnection(); 
				Channel channel = connection.createChannel()
		) {

			channel.queueDeclare("TEST-QUEUE", false, false, false, null);
			String message = "";

			for (int i = 0; i < 60; i++) {
				message = "UUID = " + UUID.randomUUID() + " TEST QUEUE";
				channel.basicPublish("", "TEST-QUEUE", null, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
			}

		}

	}

	public void executeLoop(int repetitions) {
		for (int i = 0; i < repetitions; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void fatorial(int number) {
		int i, fact = 1;
		for (i = 1; i <= number; i++) {
			fact = fact * i;
			log.info(Thread.currentThread().getName() + " exec -> " + i);
		}
		log.info("Factorial of " + number + " is: " + fact);
	}

}